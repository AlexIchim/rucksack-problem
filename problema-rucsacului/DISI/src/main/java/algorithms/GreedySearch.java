package algorithms;

import model.Bag;
import model.BagItem;
import utils.ExcelUtils;
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
    private GreedyType greedyType;

    public GreedySearch(String fileName, GreedyType greedyType) {
        this.fileName = fileName;
        this.infoReader = InfoReader.readInfo(fileName);
        this.greedyType = greedyType;
    }

    public static void main(String[] args) {
        GreedySearch greedySearch = new GreedySearch("rucsac-20.txt", GreedyType.MaxRatioValueQuantity);
        Bag bag = greedySearch.minQuantityItems();
        System.out.println(bag.getValue());

    }

    public Bag maxValueItems() {
        List<BagItem> itemList = infoReader.getBagItemList();
        Collections.sort(itemList, BagItem.compareByValue());
    /*    for (BagItem item: itemList
             ) {
            System.out.println(item.getId() + " " + item.getQuantity() + " " + item.getValue());
        }*/
        return calcBestBag(itemList);
    }

    public Bag minQuantityItems() {
        List<BagItem> itemList = infoReader.getBagItemList();
        Collections.sort(itemList, BagItem.compareByQuantity());



        return calcBestBag(itemList);
    }

    public Bag maxValueQuantityRatio() {
        List<BagItem> itemList = infoReader.getBagItemList();
        Collections.sort(itemList, BagItem.compareByRatio());
        return calcBestBag(itemList);
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
        for (BagItem bi: bag.getItems()
                ) {
            System.out.println(bi.getId() + " " + bi.getQuantity() + " " + bi.getValue());
        }
        bag.setItemsBits(infoReader.getNrObjects());
        return bag;
    }


    @Override
    public Bag findBestBag() {
        switch (greedyType) {
            case MaxRatioValueQuantity:
                return maxValueQuantityRatio();
            case MaxValue:
                return maxValueItems();
            default:
                return minQuantityItems();
        }
    }

    @Override
    public InfoReader getInfoReader() {
        return this.infoReader;
    }

    public GreedyType getGreedyType() {
        return greedyType;
    }

    public void setGreedyType(GreedyType greedyType) {
        this.greedyType = greedyType;
    }
}
