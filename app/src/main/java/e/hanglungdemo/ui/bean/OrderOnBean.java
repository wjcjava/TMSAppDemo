package e.hanglungdemo.ui.bean;

public class OrderOnBean {

    private String itemName;

    public String getItemName() {
        return itemName == null ? "" : itemName;
    }

    public OrderOnBean setItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }
}
