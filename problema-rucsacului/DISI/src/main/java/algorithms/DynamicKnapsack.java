package algorithms;

import model.Bag;
import model.BagItem;
import utils.InfoReader;

import java.util.ArrayList;

/**
 * Created by Alex Ichim on 13.03.2017.
 */
public class DynamicKnapsack {

    public static void main(String[] args) {
        Bag bag = dynKnapsack();
        System.out.println(bag.getValue());
    }

    public static Bag dynKnapsack() {
        Bag bag = new Bag();
        InfoReader infoReader = InfoReader.readInfo("rucsac-200.txt");


        //Create arraylist of items in the data set
        ArrayList<BagItem> bagItems = (ArrayList<BagItem>) infoReader.getBagItemList();
        BagItem initial = new BagItem();
        initial.setValue(0);
        initial.setQuantity(0);
        initial.setId(0);
        bagItems.add(0, initial);
        bag.setItems(new ArrayList<BagItem>());

        //Create array that will contain the optimal solution as well as the solution to the sub problem
        int[][] array = new int[bagItems.size()][infoReader.getMaxWeight()+1];

        //Create an array containing the itmes that are in the optimal solution
        int[][] itemsToKeep = new int[bagItems.size()][infoReader.getMaxWeight()+1];



        //sets all items of item number 0 to 0 for all weights up to the max weight
        array[0][0] = 0;
        for (int a = 0; a < infoReader.getMaxWeight(); a++) {
                array[0][a] = 0;
        }

        //loops through the item numbers
        for(int i = 1; i < array.length; i++)
        {
            //loops through the item weights - finds the optimal solution for the given item and the given weight in the array
            for(int j = 1; j < array[i].length; j++)
            {
                if(bagItems.get(i).getQuantity() <= j && bagItems.get(i).getValue() + array[i-1][j-bagItems.get(i).getQuantity()] >= array[i-1][j])
                {
                    array[i][j] = (bagItems.get(i).getValue() + array[i-1][j-bagItems.get(i).getQuantity()]);
                    itemsToKeep[i][j] = 1;
                }
                else {
                    array[i][j] = array[i-1][j];
                    itemsToKeep[i][j] = 0;
                }
            }
        }

        int weight = infoReader.getMaxWeight();
        //Determines which items were kept in the optimal solution of the knapsack problem
        for (int i = bagItems.size()-1; i >= 0 ; i--) {
            if (itemsToKeep[i][weight] == 1) {
                bag.getItems().add(bagItems.get(i));
                weight -= bagItems.get(i).getQuantity();
            }
        }

        return bag;
    }
}
