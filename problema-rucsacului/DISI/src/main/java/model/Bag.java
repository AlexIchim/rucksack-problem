package model;

import model.BagItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex Ichim on 04.03.2017.
 */
public class Bag {
    /**
     * @maxWeight =  model.Bag maximum total weight.
     * @items = List of items in the bag (items ID).
     * @itemsBits = List of items in the bag, represented as bits (0 - not contained, 1 - contained)
     */
    private int maxWeight;
    private List<BagItem> items;
    private List<Integer> itemsBits;

    public Bag () {
        items = new ArrayList<>();
        itemsBits = new ArrayList<>();
    }

    public Bag(int maxWeight) {
        this();
        this.maxWeight = maxWeight;
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

    public void setItemsBits(int nrItems) {
        itemsBits.clear();
       /* for (int i = 0; i < nrItems; i++) {
            if (items.contains((BagItem) allItems.get(i))) {
                itemsBits.add(1);
            } else {
                itemsBits.add(0);
            }
        }*/
        for (int i = 0; i < nrItems; i++) {
            itemsBits.add(0);
        }

        for (BagItem bi: items
             ) {
            itemsBits.set(bi.getId()-1, 1);
        }
    }

    @Override
    public String toString() {
        return "model.Bag{" +
                ", maxWeight=" + maxWeight +
                ", items=" + items +
                '}';
    }

    public String getBitsString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer i: itemsBits
             ) {
            stringBuilder.append(i);
        }
        return stringBuilder.toString();
    }

}
