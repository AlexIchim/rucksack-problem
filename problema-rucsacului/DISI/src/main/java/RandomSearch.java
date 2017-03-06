import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.Number;

/**
 * Created by Alex Ichim on 04.03.2017.
 */
public class RandomSearch {

    public static void main(String[] args) {
        String EXCEL_FILE_LOCATION = args[0];
        WritableWorkbook myFirstWbook = null;

        try {
            myFirstWbook = Workbook.createWorkbook(new File(EXCEL_FILE_LOCATION));
            WritableSheet excelSheet = myFirstWbook.createSheet("Sheet 1", 0);

            Label label = new Label(0, 0, "Run Count");
            excelSheet.addCell(label);

            Label label1 = new Label(1, 0, "Total Weight");
            excelSheet.addCell(label1);

            Label label2 = new Label(2, 0, "Total Value");
            excelSheet.addCell(label2);


            for (int i = 0; i < 20000; i++) {
                Bag b = testRandomSearch();
                Number testID = new Number(0, i+1, i);
                excelSheet.addCell(testID);

                Number totalWeight = new Number(1, i+1, b.getQuantity());
                excelSheet.addCell(totalWeight);

                Number totalValue = new Number(2, i+1, b.getValue());
                excelSheet.addCell(totalValue);
            }
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


    private static Bag  testRandomSearch() {
        InfoReader infoReader = InfoReader.readInfo("rucsac-20.txt");
        System.out.println(infoReader.getMaxWeight());

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

        System.out.println("=========Result=========");
        System.out.println(bag.getMaxWeight());
        System.out.println(bag.getQuantity());
        System.out.println("Total value: " + bag.getValue());

        return bag;
    }
}
