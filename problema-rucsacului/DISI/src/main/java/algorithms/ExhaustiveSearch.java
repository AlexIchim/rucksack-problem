package algorithms;

import model.Bag;
import utils.InfoReader;
import utils.SearchStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex Ichim on 04.03.2017.
 */
public class ExhaustiveSearch implements SearchStrategy {
    private int maxValue;
    private String fileName;
    private InfoReader infoReader;
    private Bag bestBag;


    public ExhaustiveSearch(String fileName) {
        this.fileName = fileName;
        this.infoReader = InfoReader.readInfo(fileName);
        this.bestBag = new Bag();
    }


    public static void main(String[] args) {
        List<Integer> itemsIndex = new ArrayList<>();
        ExhaustiveSearch exhaustiveSearch = new ExhaustiveSearch("rucsac-20.txt");
        for (int i = 0; i < exhaustiveSearch.getInfoReader().getNrObjects(); i++) {
            itemsIndex.add(i);
        }
        int n = itemsIndex.size();
        for (int i = 0; i <= 28; i++) {
            exhaustiveSearch.generateCombination(itemsIndex, n, i);
            exhaustiveSearch.getMaxValue();
        }
    }

    public Bag findBestBag() {
        List<Integer> itemsIndex = new ArrayList<>();
        for (int i = 0; i < infoReader.getNrObjects(); i++) {
            itemsIndex.add(i);
        }
        for (int i = 0; i <= infoReader.getNrObjects(); i++) {
            this.generateCombination(itemsIndex, infoReader.getNrObjects(), i);
        }
        return bestBag;
    }


    private void combinationUtil(List<Integer> arr, int data[], int start, int end, int index, int r) {
        //Current combination is ready to be printed
            if (index == r) {
                Bag bag = new Bag();
                for (int j = 0; j < r; j++) {
                    bag.setMaxWeight(infoReader.getMaxWeight());
                    bag.getItems().add(infoReader.getBagItemList().get(data[j]));
                }
                if (bag.getQuantity() <= bag.getMaxWeight() && bag.getValue() > bestBag.getValue()) {
                    bestBag = bag;
                    maxValue = bag.getValue();
                }
                //System.out.println("Length: " + data.length);
                return;
            }
            for (int i = start; i<=end && end-i+1 >= r - index; i++) {
                data[index] = arr.get(i);
                combinationUtil(arr, data, i+1, end, index+1, r);
            }
    }

    public void generateCombination(List<Integer> arr, int n, int r) {
        int data[] = new int[r];
        combinationUtil(arr, data, 0, n-1, 0, r);
    }

    public int getMaxValue() {
        return maxValue;
    }

    public InfoReader getInfoReader() {
        return InfoReader.readInfo(fileName);
    }
}
