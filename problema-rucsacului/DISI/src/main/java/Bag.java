import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex Ichim on 04.03.2017.
 */
public class Bag {
    private int maxWeight;
    private List<BagItem> items;

    public Bag () {
        items = new ArrayList<>();
    }

    public Bag(int nrItems, int maxWeight, List<BagItem> items) {
        this.maxWeight = maxWeight;
        this.items = items;
    }

    public boolean checkOverFull() {
        int quantity = 0;
        for (BagItem bi: items
             ) {
            quantity += bi.getQuantity();
        }
        return quantity > maxWeight ? true : false;
    }

    public int getQuantity() {
        int quant = 0;
        for (BagItem bi: items
             ) {
            quant += bi.getQuantity();
        }
        return quant;
    }

    public int getValue() {
        int value = 0;
        for (BagItem bi: items
                ) {
            value += bi.getValue();
        }
        return value;
    }


    public int getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public List<BagItem> getItems() {
        return items;
    }

    public void setItems(List<BagItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Bag{" +
                ", maxWeight=" + maxWeight +
                ", items=" + items +
                '}';
    }
}
