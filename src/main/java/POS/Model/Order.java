package POS.Model;

import java.sql.*;
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
        connect();
    }

    public Connection connect() {
        Connection conn = null;
        Statement stmt = null;
        try {
            String url = "jdbc:sqlite:db.sqlite";
            conn = DriverManager.getConnection(url);
            System.out.println("> Connection to SQLite has been successfully.");
            String sqlText = "INSERT INTO orders (`dateorder`, `who`, `mainProduct`, `option`, `topping`, `totalPrice`) VALUES (?,?,?,?,?,?)";
            PreparedStatement prepare = conn.prepareStatement(sqlText);
            prepare.setString(1, String.valueOf(dateOrder));
            prepare.setString(2, who);
            prepare.setString(3, mainProduct);
            prepare.setString(4, String.valueOf(option));
            prepare.setString(5, String.valueOf(topping));
            prepare.setString(6, String.valueOf(totalPrice));
            if (prepare.executeUpdate() == 1){
                System.out.println("> Add Success");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

        return conn;
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
    
    public String getId(){
        String id = null;
        Connection conn = null;
        Statement stmt = null;
        try {
            String url = "jdbc:sqlite:db.sqlite";
            conn = DriverManager.getConnection(url);
            System.out.println("> Connection to SQLite has been successfully.");
            String sqlText = "INSERT INTO orders (`dateorder`, `who`, `mainProduct`, `option`, `topping`, `totalPrice`) VALUES (?,?,?,?,?,?)";
            String sql = "SELECT * FROM orders ORDER BY id DESC LIMIT 1;";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            id = rs.getString(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

        return id;
    }
}
