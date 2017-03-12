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
    private static int count;


    static {
        count = 0;
    }

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
        int r = 6;
        int n = itemsIndex.size();

        for (int i = 0; i <= 28; i++) {
            exhaustiveSearch.generateCombination(itemsIndex, n, i);
            exhaustiveSearch.getMaxValue();
            System.out.println("Result: " + i + " is : " + exhaustiveSearch.getMaxValue());
            System.out.println("Size of bag: " + exhaustiveSearch.getBestBag().getQuantity());
            System.out.println("\n");
        }

        System.out.println("Combiantions count: " + count);
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
                ExhaustiveSearch.count++;
                Bag bag = new Bag();
                for (int j = 0; j < r; j++) {
                    bag.setMaxWeight(infoReader.getMaxWeight());
                    bag.getItems().add(infoReader.getBagItemList().get(data[j]));
                }
                if (bag.getQuantity() <= bag.getMaxWeight() && bag.getValue() > bestBag.getValue()) {
                    bestBag = bag;
                    maxValue = bag.getValue();
                }
                return;
            }

        //replace index with all possible elements. The condition
        //"end-i+1 >= r-index" makes sure that inccluding one elemente
        //at index will make a combination with remaining elements
        //at remaining position
        for (int i = start; i<=end && end-i+1 >= r - index; i++) {
            data[index] = arr.get(i);
            combinationUtil(arr, data, i+1, end, index+1, r);
        }
    }

    public void generateCombination(List<Integer> arr, int n, int r) {
        int data[] = new int[r];
        combinationUtil(arr, data, 0, n-1, 0, r);
    }


    public Bag getBestBag() {
        return bestBag;
    }

    public void setBestBag(Bag bestBag) {
        this.bestBag = bestBag;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public InfoReader getInfoReader() {
        return InfoReader.readInfo(fileName);
    }

    public void setInfoReader(InfoReader infoReader) {
        this.infoReader = infoReader;
    }


}
