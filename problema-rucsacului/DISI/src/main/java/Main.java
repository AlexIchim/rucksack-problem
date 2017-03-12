import algorithms.*;
import model.Bag;
import model.BagItem;
import utils.*;

import java.util.List;

/**
 * Created by Alex Ichim on 11.03.2017.
 */
public class Main {

    public static void main(String[] args) {
        String fileName = "rucsac-200.txt";
        long now; /* running time check */
        Bag bag = new Bag();

        /**
         * Random Search
         */
/*        SearchStrategy randomSearch = new RandomSearch(fileName);
        ExcelUtils randomSearchReport = new ExcelUtils("Rucsac20 - 20000.xls", 20000, randomSearch);
        randomSearchReport.writeExcel();*/

        /**
         * Greedy Search
         */
/*
        SearchStrategy greedySearchMaxValue     = new GreedySearch(fileName, GreedyType.MaxValue);
        SearchStrategy greedySearchMinQuantity  = new GreedySearch(fileName, GreedyType.MinQuantity);
        SearchStrategy greedySearchMaxRation    = new GreedySearch(fileName, GreedyType.MaxRationValueQuantity);

        bag = greedySearchMaxValue.findBestBag();
        System.out.println("Greedy max value: " + bag.getValue());
        bag.setItemsBits(InfoReader.readInfo(fileName).getNrObjects(), InfoReader.readInfo(fileName).getBagItemList());
        System.out.println(bag.getBitsString());


        bag = greedySearchMaxRation.findBestBag();
        System.out.println("Greedy max value/quantity ration: " + bag.getValue());
        bag.setItemsBits(InfoReader.readInfo(fileName).getNrObjects(), InfoReader.readInfo(fileName).getBagItemList());
        System.out.println(bag.getBitsString());


        bag = greedySearchMinQuantity.findBestBag();
        System.out.println("Greedy min quantity: " + bag.getValue());
        bag.setItemsBits(InfoReader.readInfo(fileName).getNrObjects(), InfoReader.readInfo(fileName).getBagItemList());
        System.out.println(bag.getBitsString());
*/


        /**
         * Exhaustive Search - 2^n
         */
  /*      now = System.currentTimeMillis();
        SearchStrategy exhaustiveSearch = new ExhaustiveSearch(fileName);
        bag = exhaustiveSearch.findBestBag();
        System.out.println("Miliseconds run: " + (System.currentTimeMillis() - now));
        System.out.println("Best bag : " + bag.getValue());*/

        /**
         * Branch and Bound -  < 2^n
         */

        now = System.currentTimeMillis();
        SearchStrategy branchAndBoundSearch = new BranchAndBoundSearch(fileName);
        bag = branchAndBoundSearch.findBestBag();
        System.out.println("Miliseconds run: " + (System.currentTimeMillis() - now));
        System.out.println("Best bag : " + bag.getValue());
        System.out.println(BranchAndBoundSearch.count);
        int count = 0;


        /**
         * Random Hill Climbing
         */



      /*  System.out.println("Branch and bound: ");
        BranchAndBoundSearch branchAndBoundSolver = new BranchAndBoundSearch(fileName);
        long now = System.nanoTime();
        Bag bag = branchAndBoundSolver.solve();
        System.out.println("Solution: "  + bag.getValue());
        System.out.println(System.nanoTime()-now);

        System.out.println("Random search");
        now = System.nanoTime();
        ExhaustiveSearch exhaustiveSearch = new ExhaustiveSearch(fileName);
        Bag bag1 = exhaustiveSearch.searchBestBag();
        System.out.println("Solution: "  + bag1.getValue());

        System.out.println(System.nanoTime() - now);*/

       /* RandomHillClimbing randomClimbingStrategy = new RandomHillClimbing(fileName, ClimbingType.SteepestAscent);
        int best = 0;
        while(best < 134000) {
            Bag bag3 = randomClimbingStrategy.seachBestBag();
            best = bag3.getValue();
        }

        for (List<Integer> list:
                randomClimbingStrategy.getSolutions()) {
            for (Integer i:
                 list) {
                System.out.print(i);
            }
            System.out.println();
            System.out.println();
        }
        randomClimbingStrategy.getSolutions().clear();
        
        System.out.println("RanddomHill best: " +best);*/
    }
}
