import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Alex Ichim on 04.03.2017.
 */
public class InfoReader {
    private int maxWeight;
    private int nrObjects;
    private List<BagItem> bagItemList;
    private String fileName;


    public static void main(String[] args) {
       InfoReader infoReader = InfoReader.readInfo("rucsac-20.txt");
       System.out.println(infoReader.getMaxWeight());

        for (BagItem bagItem: infoReader.getBagItemList()
             ) {
            System.out.println(bagItem.toString());
        }
    }

    public static InfoReader readInfo(String fileName)  {
        InfoReader infoReader = new InfoReader();
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            int count = scanner.nextInt();
            infoReader.setNrObjects(count);

            while (count > 0) {
                BagItem bagItem = new BagItem();
                bagItem.setId(scanner.nextInt());
                bagItem.setValue(scanner.nextInt());
                bagItem.setQuantity(scanner.nextInt());
                infoReader.bagItemList.add(bagItem);
                count--;
            }

            infoReader.setMaxWeight(scanner.nextInt());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return infoReader;
    }



    public InfoReader(){
        this.bagItemList = new ArrayList<>();
    }

    public InfoReader(int maxWeight, int nrObjects, List<BagItem> bagItemList) {
        this.maxWeight = maxWeight;
        this.nrObjects = nrObjects;
        this.bagItemList = new ArrayList<>();
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public int getNrObjects() {
        return nrObjects;
    }

    public void setNrObjects(int nrObjects) {
        this.nrObjects = nrObjects;
    }

    public List<BagItem> getBagItemList() {
        return bagItemList;
    }

    public void setBagItemList(List<BagItem> bagItemList) {
        this.bagItemList = bagItemList;
    }
}
