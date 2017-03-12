package algorithms;

import model.Bag;
import model.BagItem;
import utils.GreedyType;
import utils.InfoReader;
import utils.SearchStrategy;

import java.util.Collections;
import java.util.List;

/**
 * Created by Alex Ichim on 04.03.2017.
 */
public class GreedySearch implements SearchStrategy {

    private String fileName;
    private InfoReader infoReader;
    private Bag bestBag;
    private GreedyType greedyType;

    public GreedySearch(String fileName, GreedyType greedyType) {
        this.fileName = fileName;
        this.infoReader = InfoReader.readInfo(fileName);
        this.bestBag = new Bag();
        bestBag.setMaxWeight(this.infoReader.getMaxWeight());
        this.greedyType = greedyType;
    }

    public static void main(String[] args) {
        GreedySearch greedySearch = new GreedySearch("rucsac-200.txt", GreedyType.MaxValue);

        System.out.println("Greedy max. value: ");
        greedySearch.maxValueItems();
        System.out.println("\n");
        
        System.out.println("Greedy min. quantity: ");
        greedySearch.minQuantityItems();
        System.out.println("\n");
        
        System.out.println("Greedy max. value/quantity: ");
        greedySearch.maxValueQuantityRatio();
        System.out.println("\n");
    }

    public Bag maxValueItems() {

        List<BagItem> itemList = infoReader.getBagItemList();
        Collections.sort(itemList, BagItem.compareByValue());
        bestBag = calcBestBag(itemList);
        return bestBag;
    }

    public Bag minQuantityItems() {
        List<BagItem> itemList = infoReader.getBagItemList();
        Collections.sort(itemList, BagItem.compareByQuantity());
        bestBag = calcBestBag(itemList);
        return bestBag;
    }

    public Bag maxValueQuantityRatio() {
        List<BagItem> itemList = infoReader.getBagItemList();
        Collections.sort(itemList, BagItem.compareByRatio());
        bestBag = calcBestBag(itemList);
        return bestBag;
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


    @Override
    public Bag findBestBag() {
        switch (greedyType) {
            case MaxRationValueQuantity:
                return maxValueQuantityRatio();
            case MaxValue:
                return maxValueItems();
            default:
                return minQuantityItems();
        }
    }
}
