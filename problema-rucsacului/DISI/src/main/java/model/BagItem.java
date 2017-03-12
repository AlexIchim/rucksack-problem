package model;

import java.util.Comparator;

/**
 * Created by Alex Ichim on 04.03.2017.
 */
public class BagItem {
    private int id;
    private int quantity;
    private int value;

    public BagItem() {
    }

    public static Comparator<BagItem> compareByQuantity() {
        return new Comparator<BagItem>() {
            @Override
            public int compare(BagItem o1, BagItem o2) {
                return Integer.compare(o1.getQuantity(), o2.getQuantity());
            }
        };
    }

    public static Comparator<BagItem> compareByValue() {
        return new Comparator<BagItem>() {
            @Override
            public int compare(BagItem o1, BagItem o2) {
                return Integer.compare(o1.getValue(), o2.getValue());
            }
        };
    }

    public static Comparator<BagItem> compareByRatio() {
        return new Comparator<BagItem>() {
            @Override
            public int compare(BagItem o1, BagItem o2) {
                return Double.compare(o1.getRatio(), o2.getRatio());
            }
        };
    }

    public static Comparator<BagItem> compareByRatioDescending() {
        return new Comparator<BagItem>() {
            @Override
            public int compare(BagItem o1, BagItem o2) {
                return Double.compare(o2.getRatio(), o1.getRatio());
            }
        };
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public double getRatio () {

        double x = value;
        return  x / quantity;
    }

    @Override
    public String toString() {
        return "model.BagItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }

        BagItem bagItem = (BagItem) o;
        return id == bagItem.id && value == bagItem.value && quantity == bagItem.quantity;
    }
}
