/**
 * Created by Alex Ichim on 04.03.2017.
 */
public class BagItem {
    private int id;
    private int quantity;
    private int value;

    public BagItem() {
    }

    public BagItem(int id, int quantity, int value) {
        this.id = id;
        this.quantity = quantity;
        this.value = value;
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

    @Override
    public String toString() {
        return "BagItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", value=" + value +
                '}';
    }
}
