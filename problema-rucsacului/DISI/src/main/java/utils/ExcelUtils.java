package utils;

import jxl.Workbook;
import jxl.write.*;
import jxl.write.Number;
import model.Bag;

import java.io.File;
import java.io.IOException;

/**
 * Created by Alex Ichim on 11.03.2017.
 */
public class ExcelUtils {
    private String fileName;
    private int nrRuns;
    private SearchStrategy searchStrategy;

    public ExcelUtils(String fileName, int nrRuns, SearchStrategy searchStrategy) {
        this.fileName = fileName;
        this.nrRuns = nrRuns;
        this.searchStrategy = searchStrategy;
    }

    public void writeExcel2() {
        String EXCEL_FILE_LOCATION = fileName;
        WritableWorkbook myFirstWbook = null;
        long average = 0, best = 0;
        String solution = "";
        try {
            myFirstWbook = Workbook.createWorkbook(new File(EXCEL_FILE_LOCATION));
            WritableSheet excelSheet = myFirstWbook.createSheet("Sheet 1", 0);

            Label label = new Label(0, 0, "Run Count");
            excelSheet.addCell(label);

            Label label1 = new Label(1, 0, "Total Weight");
            excelSheet.addCell(label1);

            Label label2 = new Label(2, 0, "Total Value");
            excelSheet.addCell(label2);

            Label labelSol = new Label(3, 0, "Solution ");
            excelSheet.addCell(labelSol);


            for (int i = 0; i < nrRuns; i++) {

                System.out.println("Run nr : " + i);
                Bag b = searchStrategy.findBestBag();
                Number testID = new Number(0, i+1, i);
                excelSheet.addCell(testID);

                Number totalWeight = new Number(1, i+1, b.getQuantity());
                excelSheet.addCell(totalWeight);

                Number totalValue = new Number(2, i+1, b.getValue());
                excelSheet.addCell(totalValue);


                System.out.println(b.getBitsString());
                Label labelBits = new Label(3, i+1, b.getBitsString());
                excelSheet.addCell(labelBits);

                average += b.getQuantity();
                if (b.getValue() > best) {
                    best = b.getValue();
                    solution = b.getBitsString();
                }
                System.out.println("\n");
            }
            average = average/nrRuns;


            System.out.println("\n\nFinal results for " + nrRuns + " iterations. ");

            System.out.println("Average:" + average);
            Label label3 = new Label(0, nrRuns+3, "Average: ");
            excelSheet.addCell(label3);

            Number averageRes = new Number(2, nrRuns+3, average);
            excelSheet.addCell(averageRes);


            Label label4 = new Label(0, nrRuns+4, "Best: ");
            excelSheet.addCell(label4);

            Number bestRes = new Number(2, nrRuns+4, best);
            excelSheet.addCell(bestRes);

            Label bestSol = new Label(3, nrRuns+4, solution);
            excelSheet.addCell(bestSol);


            System.out.println("Best solution: " + best);
            myFirstWbook.write();
        } catch (WriteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (myFirstWbook != null) {
                try {
                    myFirstWbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void writeExcel() {
        String EXCEL_FILE_LOCATION = fileName;
        WritableWorkbook myFirstWbook = null;
        long average = 0, best = 0;
        String solution = "";
        try {
            myFirstWbook = Workbook.createWorkbook(new File(EXCEL_FILE_LOCATION));
            WritableSheet excelSheet = myFirstWbook.createSheet("Sheet 1", 0);

            Label label = new Label(0, 0, "Run Count");
            excelSheet.addCell(label);

            Label label1 = new Label(1, 0, "Total Weight");
            excelSheet.addCell(label1);

            Label label2 = new Label(2, 0, "Total Value");
            excelSheet.addCell(label2);

            Label labelSol = new Label(3, 0, "Solution ");
            excelSheet.addCell(labelSol);


            for (int i = 0; i < nrRuns; i++) {

                System.out.println("Run nr : " + i);
                Bag b = searchStrategy.findBestBag();
                Number testID = new Number(0, i+1, i);
                excelSheet.addCell(testID);

                Number totalWeight = new Number(1, i+1, b.getQuantity());
                excelSheet.addCell(totalWeight);

                Number totalValue = new Number(2, i+1, b.getValue());
                excelSheet.addCell(totalValue);


                System.out.println(b.getBitsString());
                Label labelBits = new Label(3, i+1, b.getBitsString());
                excelSheet.addCell(labelBits);

                average += b.getQuantity();
                if (b.getValue() > best) {
                    best = b.getValue();
                    solution = b.getBitsString();
                }
                System.out.println("\n");
            }
            average = average/nrRuns;


            System.out.println("\n\nFinal results for " + nrRuns + " iterations. ");

            System.out.println("Average:" + average);
            Label label3 = new Label(0, nrRuns+3, "Average: ");
            excelSheet.addCell(label3);

            Number averageRes = new Number(2, nrRuns+3, average);
            excelSheet.addCell(averageRes);


            Label label4 = new Label(0, nrRuns+4, "Best: ");
            excelSheet.addCell(label4);

            Number bestRes = new Number(2, nrRuns+4, best);
            excelSheet.addCell(bestRes);

            Label bestSol = new Label(3, nrRuns+4, solution);
            excelSheet.addCell(bestSol);


            System.out.println("Best solution: " + best);
            myFirstWbook.write();
        } catch (WriteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (myFirstWbook != null) {
                try {
                    myFirstWbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
