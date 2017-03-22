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
    private InfoReader infoReader;
    private List<String> solutionsSoFar;

    public RandomSearch(String fileName) {
        this.fileName = fileName;
        this.infoReader = InfoReader.readInfo(fileName);
        this.solutionsSoFar = new ArrayList<>();
    }

    public static void main(String[] args) {
        String fileName = new String("rucsac-200.txt");
        RandomSearch randomSearch = new RandomSearch(fileName);
        System.out.println("Solution     " + " Value " + " Weight ");
        for (int i = 0; i < 5; i++) {
            Bag b = randomSearch.randomSearchBag();
            System.out.println(b.getBitsString() + " " + b.getValue() +" " + b.getQuantity());
        }
    }


    private Bag randomSearchBag() {
        Bag bag = new Bag(infoReader.getMaxWeight());
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
        bag.setItemsBits(infoReader.getNrObjects());
        return bag;
    }

    @Override
    public Bag findBestBag() {
        return randomSearchBag();
    }


    @Override
    public InfoReader getInfoReader() {
        return this.infoReader;
    }
}
