package algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Bag;
import utils.ClimbingType;
import utils.ExcelUtils;
import utils.InfoReader;
import utils.SearchStrategy;

/**
 * Created by Alex Ichim on 04.03.2017.
 */
public class RandomSearch implements SearchStrategy {

    private String fileName;

    public RandomSearch(String fileName) {
        this.fileName = fileName;
    }

    public static void main(String[] args) {
        String fileName = "rucsac-20.txt";
        SearchStrategy nextAscentStrategy = new RandomHillClimbing(fileName, ClimbingType.NextAscent);
        SearchStrategy randomClimbingStrategy = new RandomHillClimbing(fileName, ClimbingType.RandomHillClimbing);
        SearchStrategy steepestAscentClimbing = new RandomHillClimbing(fileName, ClimbingType.SteepestAscent);

        ExcelUtils excelUtils = new ExcelUtils("NewExcel.xls", 100,  new RandomSearch(fileName));
        excelUtils.writeExcel();

    }


    private Bag randomSearchBag() {
        InfoReader infoReader = InfoReader.readInfo(this.fileName);

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

        for (int i = 0; i < infoReader.getNrObjects(); i++) {
            if (bag.getItems().contains(infoReader.getBagItemList().get(i))) {
                bag.getItemsBits().add(1);
            } else {
                bag.getItemsBits().add(0);
            }
        }

        System.out.println("=========Result=========");
        System.out.println("model.Bag quantity: " +bag.getQuantity());
        System.out.println("Total value: " + bag.getValue());

        return bag;
    }

    @Override
    public Bag findBestBag() {
        return randomSearchBag();
    }
}
