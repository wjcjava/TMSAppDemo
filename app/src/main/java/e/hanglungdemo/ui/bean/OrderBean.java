package e.hanglungdemo.ui.bean;

public class OrderBean {

    private String management;
    private int homeLogo;
    private String orderTitle;
    private String orderfunction;

    public int getHomeLogo() {
        return homeLogo;
    }

    public OrderBean setHomeLogo(int homeLogo) {
        this.homeLogo = homeLogo;
        return this;
    }

    public String getOrderTitle() {
        return orderTitle == null ? "" : orderTitle;
    }

    public OrderBean setOrderTitle(String orderTitle) {
        this.orderTitle = orderTitle;
        return this;
    }

    public String getOrderfunction() {
        return orderfunction == null ? "" : orderfunction;
    }

    public OrderBean setOrderfunction(String orderfunction) {
        this.orderfunction = orderfunction;
        return this;
    }

    public String getManagement() {
        return management == null ? "" : management;
    }

    public OrderBean setManagement(String management) {
        this.management = management;
        return this;
    }
}
