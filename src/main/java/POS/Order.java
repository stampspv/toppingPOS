package POS;

import java.util.Date;
import java.util.List;

public class Order {

    private Date dateOrder;
    private String who;
    private String mainProduct;
    private Integer option;
    private List<String> topping;
    private Float totalPrice;

    public Order(Date date, String who, String mainProduct, Integer option, List<String> topping, Float totolPrice){
        this.dateOrder = date;
        this.who = who;
        this.mainProduct = mainProduct;
        this.topping = topping;
        this.option = option;
        this.totalPrice = totolPrice;
    }


    public String getDateOrder() {
        return dateOrder.toString();
    }

    public String getWho() {
        return who;
    }

    public String getMainProduct() {
        return mainProduct;
    }

    public Integer getOption() {
        return option;
    }

    public Float getTotolPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "dateOrder='" + dateOrder + '\'' +
                ", who='" + who + '\'' +
                ", mainProduct='" + mainProduct + '\'' +
                ", option='" + option + '\'' +
                ", topping='" + topping + '\'' +
                ", totolPrice=" + totalPrice +
                '}';
    }
}
