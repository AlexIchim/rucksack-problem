package algorithms;

import model.Bag;
import model.BagItem;
import utils.InfoReader;
import utils.SearchStrategy;

import java.util.*;

/**
 * Created by Alex Ichim on 10.03.2017.
 */
public class BranchAndBoundSearch implements SearchStrategy {

    private String fileName;
    private InfoReader infoReader;
    private Bag bestBag;
    private List<BagItem> bagItems;
    public static int count;

    public static void main(String[] args) {
        BranchAndBoundSearch branchAndBoundSolver = new BranchAndBoundSearch("rucsac-200.txt");

        long now = System.nanoTime();
        Bag bag = branchAndBoundSolver.findBestBag();
        System.out.println(System.nanoTime()-now);

        System.out.println(bag.getValue());
    }

    public BranchAndBoundSearch(String fileName) {
        this.fileName = fileName;
        this.infoReader = InfoReader.readInfo(fileName);
        this.bestBag = new Bag();
        this.bagItems = infoReader.getBagItemList();
        Collections.sort(this.bagItems, new Comparator<BagItem>() {
            @Override
            public int compare(BagItem o1, BagItem o2) {
                if ( o1.getValue() / o1.getQuantity() > o2.getValue()/o2.getQuantity()) {
                    return 1;
                } else if  (o1.getValue() / o1.getQuantity() < o2.getValue()/o2.getQuantity()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
    }

    private class Node implements Comparable<Node> {
        public int height;
        List<BagItem> taken;
        public double bound;
        public double value;
        public double weight;

        public Node() {
            taken = new ArrayList<BagItem>();
        }

        public Node(Node parent) {
            height = parent.height + 1;
            taken = new ArrayList<BagItem>(parent.taken);
            bound = parent.bound;
            value = parent.value;
            weight = parent.weight;
        }

        @Override
        public int compareTo(Node o) {
            return (int) (o.bound - bound);
        }

        public void computeBound() {
            int i = height;
            double w = weight;
            bound = value;
            BagItem bagItem;

            do {
                bagItem = bagItems.get(i);
                if (w + bagItem.getQuantity() > infoReader.getMaxWeight()) break;
                w += bagItem.getQuantity();
                bound += bagItem.getValue();
                i++;
            } while (i < bagItems.size());
            bound += (infoReader.getMaxWeight() - w) * (bagItem.getRatio());
        }
    }

    @Override
    public Bag findBestBag () {
        Collections.sort(this.bagItems, BagItem.compareByRatioDescending());

        Node best = new Node();
        Node root = new Node();
        root.computeBound();

        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        priorityQueue.offer(root);

        while (!priorityQueue.isEmpty()) {
            Node node = priorityQueue.poll();
            if (node.bound > best.value && node.height < this.bagItems.size() - 1) {

                Node with = new Node(node);
                BagItem item = bagItems.get(node.height);
                with.weight += item.getQuantity();

                if (with.weight <= infoReader.getMaxWeight()) {
                    with.taken.add(bagItems.get(node.height));
                    with.value += item.getValue();
                    with.computeBound();

                    if (with.value > best.value) {
                        best = with;
                    }

                    if (with.bound > best.value) {
                        priorityQueue.offer(with);
                    }
                }

                Node without = new Node(node);
                without.computeBound();
                if (without.bound > best.value) {
                    //System.out.println(without.bound);
                    priorityQueue.offer(without);
                }
            }
        }

        Bag bag = new Bag();
        bag.setMaxWeight(infoReader.getMaxWeight());
        bag.setItems(best.taken);
        bag.setItemsBits(infoReader.getNrObjects());
        return bag;
    }

    @Override
    public InfoReader getInfoReader() {
        return this.infoReader;
    }
}
