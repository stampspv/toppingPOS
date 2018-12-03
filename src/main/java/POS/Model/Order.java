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
    private Integer status;

    private static Order instance;

    // Singleton Pattern ----------------
    public static Order getInstance() {
        if (instance == null)
            instance = new Order();
        return instance;
    }
    // ----------------------------------

    public void setDateOrder(Date dateOrder) {
        this.dateOrder = dateOrder;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setMainProduct(String mainProduct) {
        this.mainProduct = mainProduct;
    }

    public void setOption(Integer option) {
        this.option = option;
    }

    public void setTopping(List<String> topping) {
        this.topping = topping;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Order(){
        System.out.println("> This is stamp.");
    }

    public Connection orderHere() {
        Connection conn = null;
        Statement stmt = null;
        this.status = 0;
        try {
            String url = "jdbc:sqlite:db.sqlite";
            conn = DriverManager.getConnection(url);
            System.out.println("> Connection to SQLite has been successfully."+getWho()+mainProduct);
            String sqlText = "INSERT INTO orders (`dateorder`, `who`, `mainProduct`, `option`, `topping`, `totalPrice`) VALUES (?,?,?,?,?,?)";
            PreparedStatement prepare = conn.prepareStatement(sqlText);
            prepare.setString(1, String.valueOf(this.dateOrder));
            prepare.setString(2, this.who);
            prepare.setString(3, this.mainProduct);
            prepare.setString(4, String.valueOf(this.option));
            prepare.setString(5, String.valueOf(this.topping));
            prepare.setString(6, String.valueOf(this.totalPrice));

            if (Integer.parseInt(this.mainProduct) > 4) {
                this.status = 0;
            } else if (this.totalPrice < 0) {
                this.status = 0;
            } else {
                if (prepare.executeUpdate() == 1) {
                    this.status = 1;
                    System.out.println("> Add Success");
                }
            }
        } catch (SQLException e) {
            this.status = 0;
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
