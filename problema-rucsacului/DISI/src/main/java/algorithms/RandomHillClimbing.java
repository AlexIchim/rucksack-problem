package algorithms;

import model.Bag;
import model.BagItem;
import utils.ClimbingType;
import utils.InfoReader;
import utils.SearchStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Alex Ichim on 10.03.2017.
 */
public class RandomHillClimbing implements SearchStrategy {

    private int maxValue;
    private String fileName;
    private InfoReader infoReader;
    private Bag bestBag;
    private ClimbingType climbingType; /* Type 1 = Random Hill utils.ClimbingType, Type 2 = Steepest Ascent Hill utils.ClimbingType, Type 3 = Next Ascent Hill utils.ClimbingType */
    private List<List<Integer>> solutions;

    public List<List<Integer>> getSolutions() {
        return solutions;
    }

    public void setSolutions(List<List<Integer>> solutions) {
        this.solutions = solutions;
    }

    public InfoReader getInfoReader() {
        return infoReader;
    }

    public void setInfoReader(InfoReader infoReader) {
        this.infoReader = infoReader;
    }

    public Bag getBestBag() {
        return bestBag;
    }

    public void setBestBag(Bag bestBag) {
        this.bestBag = bestBag;
    }

    public List<BagItem> getBagItems() {
        return bagItems;
    }

    public void setBagItems(List<BagItem> bagItems) {
        this.bagItems = bagItems;
    }

    private List<BagItem> bagItems;

    public RandomHillClimbing(String fileName, ClimbingType climbingType) {
        this.climbingType = climbingType;
        this.fileName = fileName;
        this.infoReader = InfoReader.readInfo(fileName);
        this.bestBag = new Bag();
        this.bagItems = infoReader.getBagItemList();
        this.solutions = new ArrayList<List<Integer>>();
    }

    public static void main(String[] args) {
        RandomHillClimbing randomHillClimbing = new RandomHillClimbing("rucsac-200.txt", ClimbingType.RandomHillClimbing);

        for (int i = 0; i <= 10000; i++) {

            Bag bag = randomHillClimbing.steepestAscentHillClimbing();
            if (bag.getValue() > randomHillClimbing.getBestBag().getValue()) {
                randomHillClimbing.setBestBag(bag);
            }
        }

        System.out.println("Max value:");
        System.out.println(randomHillClimbing.getBestBag().getValue());
    }

    public Bag randomHillClimbing() {
        Bag bag = generateRandomBag();
        List<Integer> bits = new ArrayList<>();
        solutions.clear();
        for (int i = 0; i < infoReader.getNrObjects(); i++) {
            if (bag.getItems().contains(infoReader.getBagItemList().get(i))) {
                bits.add(1);
            } else {
                bits.add(0);
            }
        }

       /* for (Integer e:
             bits) {
            System.out.print(e);
        }
        System.out.println();*/
        solutions.add(bits);
        boolean check = true;
        int k = 0;
        while (check) {
            check = false;
            for (int i = 0; i < bagItems.size(); i++) {
                if (bits.get(i) != 1 && (bag.getQuantity() + bagItems.get(i).getQuantity()) <= bag.getMaxWeight()) {
                    bits.set(i, 1);
                   /* for (Integer e:
                            bits) {
                        System.out.print(e);
                    }
                    System.out.println();*/
                    bag.getItems().add(bagItems.get(i));
                    solutions.add(bits);
                    check = true;
                    break;
                }
            }
        }
        return bag;
    }

    public Bag steepestAscentHillClimbing() {
        Bag bag = new Bag();
        bag.setMaxWeight(infoReader.getMaxWeight());
        solutions.clear();


        List<Integer> itemsIndex = new ArrayList<>();
        for (int i = 0; i < infoReader.getNrObjects(); i++) {
            itemsIndex.add(i);
        }

        Random random = new Random();
        while (true) {
            int index = itemsIndex.get(random.nextInt(itemsIndex.size()));
            bag.getItems().add(infoReader.getBagItemList().get(index));
            if (bag.checkOverFull()) {
                bag.getItems().remove(infoReader.getBagItemList().get(index));
                break;
            }
            itemsIndex.remove((Integer) index);
        }

        List<Integer> bits = new ArrayList<>();
        for (int i = 0; i < infoReader.getNrObjects(); i++) {
            if (bag.getItems().contains(infoReader.getBagItemList().get(i))) {
                bits.add(1);
            } else {
                bits.add(0);
            }
        }

        solutions.add(bits);
        boolean check = true;
        int k = 0, bestItem = -1, bestValue = -1;
        while (check) {
            check = false;
            bestItem = -1;
            bestValue = -1;
            for (int i = 0; i < bagItems.size(); i++) {
                if (bits.get(i) != 1 && (bag.getQuantity() + bagItems.get(i).getQuantity()) <= bag.getMaxWeight()) {
                    if (bag.getValue() + bagItems.get(i).getValue() > bestValue) {
                        bestValue = bag.getValue() + bagItems.get(i).getValue();
                        bestItem = i;
                    }
                    check = true;
                }
            }
            if (bestItem != -1) {
                bits.set(bestItem, 1);
                bag.getItems().add(bagItems.get(bestItem));
                solutions.add(bits);
            }
        }
        return bag;
    }

    public Bag nextAscentHilLClimbing() {
        Bag bag = new Bag();
        bag.setMaxWeight(infoReader.getMaxWeight());


        List<Integer> itemsIndex = new ArrayList<>();
        for (int i = 0; i < infoReader.getNrObjects(); i++) {
            itemsIndex.add(i);
        }

        Random random = new Random();
        while (true) {
            int index = itemsIndex.get(random.nextInt(itemsIndex.size()));
            bag.getItems().add(infoReader.getBagItemList().get(index));
            if (bag.checkOverFull()) {
                bag.getItems().remove(infoReader.getBagItemList().get(index));
                break;
            }
            itemsIndex.remove((Integer) index);
        }

        List<Integer> bits = new ArrayList<>();
        for (int i = 0; i < infoReader.getNrObjects(); i++) {
            if (bag.getItems().contains(infoReader.getBagItemList().get(i))) {
                bits.add(1);
            } else {
                bits.add(0);
            }
        }


        boolean check = true;
        int k = 0;
        while (check) {
            check = false;
            for (int i = k; i < bagItems.size(); i++) {
                if (bits.get(i) != 1 && (bag.getQuantity() + bagItems.get(i).getQuantity()) <= bag.getMaxWeight()) {
                    bits.set(i, 1);
                    bag.getItems().add(bagItems.get(i));
                    check = true;
                    k=i;
                    break;
                }
            }
        }

        return bag;
    }


    public Bag generateRandomBag() {
        Bag bag = new Bag();
        bag.setMaxWeight(infoReader.getMaxWeight());
        List<Integer> itemsIndex = new ArrayList<>();
        for (int i = 0; i < infoReader.getNrObjects(); i++) {
            itemsIndex.add(i);
        }

        Random random = new Random();
        while (true) {
            //System.out.println(itemsIndex.size());
            int index = itemsIndex.get(random.nextInt(itemsIndex.size()));
            bag.getItems().add(infoReader.getBagItemList().get(index));
            //System.out.println("Adding item: " + index + " with value: " + infoReader.getBagItemList().get(index).getValue() + " for a total value: " + bag.getValue() + " and a total quantity of " + bag.getQuantity());
            if (bag.checkOverFull()) {
                bag.getItems().remove(infoReader.getBagItemList().get(index));
                //System.out.println("Removing item: " + index + " with value: " + infoReader.getBagItemList().get(index).getValue() + " for a total value: " + bag.getValue() + " and a total quantity of " + bag.getQuantity());
                break;
            }
            itemsIndex.remove((Integer) index);
        }
        return bag;
    }

    @Override
    public Bag findBestBag() {
        if (climbingType == ClimbingType.RandomHillClimbing) {
            return randomHillClimbing();
        } else if (climbingType == ClimbingType.SteepestAscent) {
            return steepestAscentHillClimbing();
        } else {
            return nextAscentHilLClimbing();
        }
    }
}
