package utils;

import algorithms.GreedySearch;
import algorithms.RandomHillClimbing;
import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import jxl.write.Number;
import model.Bag;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Alex Ichim on 11.03.2017.
 */
public class ExcelUtils {
    private String outputFileName;
    private int nrRuns;
    private SearchStrategy searchStrategy;

    public ExcelUtils(String fileName, int nrRuns, SearchStrategy searchStrategy) {
        this.outputFileName = fileName;
        this.nrRuns = nrRuns;
        this.searchStrategy = searchStrategy;
    }

    public void writeExcel() {
        String EXCEL_FILE_LOCATION = outputFileName;
        WritableWorkbook myFirstWbook = null;
        long average = 0, best = 0;
        String solution = "";
        try {
            myFirstWbook = Workbook.createWorkbook(new File(EXCEL_FILE_LOCATION));
            WritableSheet excelSheet = myFirstWbook.createSheet("Sheet 1", 0);
            WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 15, WritableFont.BOLD, true, UnderlineStyle.NO_UNDERLINE, Colour.RED);
            WritableCellFormat titleFormat = new WritableCellFormat(titleFont);
            WritableFont lblFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, true, UnderlineStyle.NO_UNDERLINE, Colour.BLUE);
            WritableCellFormat lblFormat = new WritableCellFormat(lblFont);
            lblFormat.setAlignment(Alignment.CENTRE);

            Label label = new Label(0, 0, "Run Count", lblFormat);
            excelSheet.addCell(label);

            Label label1 = new Label(1, 0, "Total Weight", lblFormat);
            excelSheet.addCell(label1);

            Label label2 = new Label(2, 0, "Total Value", lblFormat);
            excelSheet.addCell(label2);

            Label labelSol = new Label(3, 0, "Solution ", lblFormat);
            excelSheet.addCell(labelSol);


            long totalTime = 0, localTime  = 0;
            for (int i = 0; i < nrRuns; i++) {

                System.out.println("Run nr : " + i);

                /*  Counting time taken for running alorithm */
                localTime = System.nanoTime();
                Bag b = searchStrategy.findBestBag();
                totalTime += (System.nanoTime() - localTime);
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

            Label kLabel = new Label(0, nrRuns + 2, "K Value: ", lblFormat);
            excelSheet.addCell(kLabel);

            Number kNumber = new Number(1, nrRuns + 2, nrRuns);
            excelSheet.addCell(kNumber);

            System.out.println("Average:" + average);
            Label label3 = new Label(0, nrRuns+3, "Average: ", lblFormat);
            excelSheet.addCell(label3);

            Number averageRes = new Number(2, nrRuns+3, average);
            excelSheet.addCell(averageRes);


            Label label4 = new Label(0, nrRuns+4, "Best: ", lblFormat);
            excelSheet.addCell(label4);

            Number bestRes = new Number(2, nrRuns+4, best);
            excelSheet.addCell(bestRes);

            Label bestSol = new Label(3, nrRuns+4, solution);
            excelSheet.addCell(bestSol);


            Label timeLbl = new Label(0, nrRuns + 5, "Total time(s) : ", lblFormat);
            excelSheet.addCell(timeLbl);

            Number timeNr = new Number(1, nrRuns + 5, totalTime / 1000000000.0);
            excelSheet.addCell(timeNr);

            Label lblMaxWeight = new Label(0, nrRuns + 6, "Max bag weight: ", lblFormat);
            excelSheet.addCell(lblMaxWeight);

            Number numberMaxWeight = new Number(1, nrRuns + 6, searchStrategy.getInfoReader().getMaxWeight());
            excelSheet.addCell(numberMaxWeight);


            Label lblNrObjects = new Label(0, nrRuns + 7, "Objects number to choose from: ", lblFormat);
            excelSheet.addCell(lblNrObjects);

            Number numberNrObjects = new Number(1, nrRuns + 7, searchStrategy.getInfoReader().getNrObjects());
            excelSheet.addCell(numberNrObjects);


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

    public void writeExcelSingle() {
        String EXCEL_FILE_LOCATION = outputFileName;
        WritableWorkbook myFirstWbook = null;
        try {
            myFirstWbook = Workbook.createWorkbook(new File(EXCEL_FILE_LOCATION));
            WritableSheet excelSheet = myFirstWbook.createSheet("Sheet 1", 0);
            WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 15, WritableFont.BOLD, true, UnderlineStyle.NO_UNDERLINE, Colour.RED);
            WritableCellFormat titleFormat = new WritableCellFormat(titleFont);
            WritableFont lblFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, true, UnderlineStyle.NO_UNDERLINE, Colour.BLUE);
            WritableCellFormat lblFormat = new WritableCellFormat(lblFont);


            //abel lblSol = new Label(0, 0, "Greedy Search by " + ((GreedySearch) searchStrategy).getGreedyType() + " solution ", titleFormat );
            Label lblSol = new Label(0, 0, "Exhaustive Search Solution", titleFormat);
            //Label lblSol = new Label(0, 0, "Branch and Bound Search Solution", titleFormat);

            excelSheet.mergeCells(0,0, 5, 0);
            excelSheet.addCell(lblSol);


            Label label1 = new Label(0, 1, "Total Weight", lblFormat);
            excelSheet.addCell(label1);

            Label label2 = new Label(1, 1, "Total Value", lblFormat);
            excelSheet.addCell(label2);

            Label labelSol = new Label(2, 1, "Solution ", lblFormat);
            excelSheet.addCell(labelSol);

            long time = System.nanoTime();
            Bag b = searchStrategy.findBestBag();
            time = System.nanoTime() - time;

            Number totalWeight = new Number(0, 2, b.getQuantity());
            excelSheet.addCell(totalWeight);

            Number totalValue = new Number(1, 2, b.getValue());
            excelSheet.addCell(totalValue);

            Label solution = new Label(2, 2, b.getBitsString());
            excelSheet.addCell(solution);


            Label lblTimeTaken = new Label(0, 4, "Time taken(s): ", lblFormat);
            excelSheet.addCell(lblTimeTaken);

            Number nrTimeTaken = new Number(1, 4, time / 1000000000.0);
            excelSheet.addCell(nrTimeTaken);

            Label lblMaxWeight = new Label(0, 5, "Max bag weight: ", lblFormat);
            excelSheet.addCell(lblMaxWeight);

            Number numberMaxWeight = new Number(1, 5, searchStrategy.getInfoReader().getMaxWeight());
            excelSheet.addCell(numberMaxWeight);


            Label lblNrObjects = new Label(0, 6, "Objects number to choose from: ", lblFormat);
            excelSheet.addCell(lblNrObjects);

            Number numberNrObjects = new Number(1, 6, searchStrategy.getInfoReader().getNrObjects());
            excelSheet.addCell(numberNrObjects);

            System.out.println(b.getBitsString());

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


    public void writeExcelHillClimb() {
        String EXCEL_FILE_LOCATION = outputFileName;
        WritableWorkbook myFirstWbook = null;
        try {
            myFirstWbook = Workbook.createWorkbook(new File(EXCEL_FILE_LOCATION));
            WritableSheet excelSheet = myFirstWbook.createSheet("Sheet 1", 0);

            WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 15, WritableFont.BOLD, true, UnderlineStyle.NO_UNDERLINE, Colour.RED);
            WritableCellFormat titleFormat = new WritableCellFormat(titleFont);

            WritableFont lblFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, true, UnderlineStyle.NO_UNDERLINE, Colour.BLUE);
            WritableCellFormat lblFormat = new WritableCellFormat(lblFont);

            Label lblTxt = new Label(0, 0, "Steepest Ascent Hill Climbing - Nr Of Resets " + nrRuns, titleFormat);

            excelSheet.mergeCells(0, 0, 8, 0);
            excelSheet.addCell(lblTxt);


            Label label1 = new Label(0, 1, "Total Weight", lblFormat);
            excelSheet.addCell(label1);

            Label label2 = new Label(1, 1, "Total Value", lblFormat);
            excelSheet.addCell(label2);

            Label labelSol = new Label(2, 1, "Solution ", lblFormat);
            excelSheet.addCell(labelSol);

            long time = System.nanoTime();
            Bag b = searchStrategy.findBestBag();
            time = System.nanoTime() - time;
            List<List<String>> lists = ((RandomHillClimbing) searchStrategy).getSolutions();
            Number totalWeight = new Number(0, 2, b.getQuantity());
            excelSheet.addCell(totalWeight);

            Number totalValue = new Number(1, 2, b.getValue());
            excelSheet.addCell(totalValue);

            Label solution = new Label(2, 2, b.getBitsString());
            excelSheet.addCell(solution);


            Label lblClimbs = new Label(3, 0, "Climbs");
            excelSheet.addCell(lblClimbs);





            int i = 7;
            int max = -1;

            Label lblDisplayClimbs = new Label(0, 5, "Display Climbs", titleFormat);
            excelSheet.mergeCells(0, 5, 5, 5);
            excelSheet.addCell(lblDisplayClimbs);

            Label lblId = new Label(0, 6, "Climb ID", lblFormat);
            excelSheet.addCell(lblId);
            for (List<String> list: lists
                 ) {
                int j = 0;
                for (String s: list
                     ) {
                    Label solLbl = new Label(j+1, i, s);
                    excelSheet.addCell(solLbl);
                    if (j > max) {

                        Label lblStep = new Label(j+1, 6, "Step " + j, lblFormat);
                        excelSheet.addCell(lblStep);
                        max = j;
                    }
                    if (j == 0) {
                        Number nrClimbId = new Number(j, i, i-6);
                        excelSheet.addCell(nrClimbId);
                    }
                    j++;
                }
                i++;
            }


            Label lblTime = new Label(0, ++i, "Time taken(s): ");
            excelSheet.addCell(lblTime);

            Number nrTime = new Number(1, i, time / 1000000000.0);
            excelSheet.addCell(nrTime);



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
