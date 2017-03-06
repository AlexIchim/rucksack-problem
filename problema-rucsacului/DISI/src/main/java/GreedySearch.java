import javax.sound.midi.MidiDevice;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Alex Ichim on 04.03.2017.
 */
public class GreedySearch {

    private String fileName;
    private InfoReader infoReader;
    private Bag bestBag;

    public GreedySearch(String fileName) {
        this.infoReader = InfoReader.readInfo(fileName);
        this.bestBag = new Bag();
        bestBag.setMaxWeight(this.infoReader.getMaxWeight());
    }

    public static void main(String[] args) {
        GreedySearch greedySearch = new GreedySearch("rucsac-20.txt");

        greedySearch.maxValueItems();
        greedySearch.minQuantityItems();
        greedySearch.minQuantityValueItems();


    }

    public void maxValueItems() {

        List<BagItem> itemList = infoReader.getBagItemList();
        Collections.sort(itemList, new Comparator<BagItem>() {
            @Override
            public int compare(BagItem o1, BagItem o2) {
                if (o1.getValue() > o2.getValue())
                    return 1;
                else if (o1.getValue() < o2.getValue())
                    return -1;
                else return 0;
            }
        });
        bestBag = calcBestBag(itemList);
      /*  for (BagItem item: bestBag.getItems()
             ) {
            System.out.println(item.toString());
        }*/

        System.out.println("Total value: " + bestBag.getValue());
        System.out.println("Total quantity: " + bestBag.getQuantity());
    }

    public void minQuantityItems() {
        List<BagItem> itemList = infoReader.getBagItemList();
        Collections.sort(itemList, new Comparator<BagItem>() {
            @Override
            public int compare(BagItem o1, BagItem o2) {
                if (o1.getQuantity() < o2.getQuantity())
                    return 1;
                else if (o1.getQuantity() > o2.getQuantity()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        bestBag = calcBestBag(itemList);

      /*  for (BagItem item: bestBag.getItems()
                ) {
            System.out.println(item.toString());
        }*/

        System.out.println("Total value: " + bestBag.getValue());
        System.out.println("Total quantity: " + bestBag.getQuantity());
    }

    public void minQuantityValueItems() {
        List<BagItem> itemList = infoReader.getBagItemList();
        Collections.sort(itemList, new Comparator<BagItem>() {
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

        bestBag = calcBestBag(itemList);
       /* for (BagItem item: bestBag.getItems()
                ) {
            System.out.println(item.toString());
        }*/

        System.out.println("Total value: " + bestBag.getValue());
        System.out.println("Total quantity: " + bestBag.getQuantity());
    }

    private Bag calcBestBag(List<BagItem> bagItems) {
        Bag bag = new Bag();
        bag.setMaxWeight(infoReader.getMaxWeight());
        int index = bagItems.size()-1;
        while (true) {
            if (index < 0) {
                break;
            } else {
                bag.getItems().add(bagItems.get(index));
                if (bag.checkOverFull()) {
                    bag.getItems().remove(bagItems.get(index));
                    index--;
                } else {
                    index--;
                }
            }
        }
        return bag;
    }
}
