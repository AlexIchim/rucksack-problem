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
        String inputFileName = "rucsac-20.txt";
        long now; /* running time check */
        Bag bag = new Bag();

/*        *//**
         * Random Search
         */

/*        int nrRuns = 5;
        SearchStrategy randomSearch = new RandomSearch(inputFileName);
        for (int i = 1; i <= nrRuns; i++) {
            ExcelUtils randomSearchReport = new ExcelUtils("RandomSearch-Rucsac20 - 100-" + i + ".xls", 100, randomSearch);
            randomSearchReport.writeExcel();
        }*/

/*
        *//**
         * Greedy Search
         */

/*        SearchStrategy greedySearchMaxValue     = new GreedySearch(inputFileName, GreedyType.MaxValue);
        SearchStrategy greedySearchMinQuantity  = new GreedySearch(inputFileName, GreedyType.MinQuantity);
        SearchStrategy greedySearchMaxRatio    = new GreedySearch(inputFileName, GreedyType.MaxRatioValueQuantity);

        ExcelUtils excelUtils = new ExcelUtils("RucsacGreedyMaxValue200.xls", 1, greedySearchMaxValue);
        ExcelUtils excelUtils1 = new ExcelUtils("RucsacGreedyMinQuantity200.xls", 1, greedySearchMinQuantity);
        ExcelUtils excelUtils2 = new ExcelUtils("RucsacMaxRatio200.xls", 1, greedySearchMaxRatio);
        excelUtils.writeExcelSingle();
        excelUtils1.writeExcelSingle();
        excelUtils2.writeExcelSingle();*/


/*        *//**
         * Exhaustive Search - 2^n
         */
/*
        SearchStrategy exhaustiveSearch = new ExhaustiveSearch(inputFileName);
        ExcelUtils excelUtils = new ExcelUtils("ExhaustiveSearch.xls", 1, exhaustiveSearch);
        excelUtils.writeExcelSingle();
*/

        /**
         * Branch and Bound -  < 2^n
         */
/*
        ExcelUtils excelUtils = new ExcelUtils("BranchAndBound20.xls", 1, new BranchAndBoundSearch(inputFileName));
        excelUtils.writeExcelSingle();*/


        /**
         * Random Hill Climbing
         */

        int nrResets = 5000;
        ExcelUtils excelUtils = new ExcelUtils("Rucsac20SteepestHillClimb - 5000.xls", nrResets, new RandomHillClimbing(inputFileName, nrResets, BestNeighbourType.MaxValueRation));
        excelUtils.writeExcelHillClimb();
/*
        for (int i = 1; i <= 10; i++) {
             excelUtils = new ExcelUtils("Rucsac200SteepestHillClimb-MaxValueNeighbour - 1000 - "+ i  + ".xls", nrResets, new RandomHillClimbing(inputFileName, nrResets, BestNeighbourType.MaxValue));
             excelUtils.writeExcelHillClimb();
        }
*/

    }
}
