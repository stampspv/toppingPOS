package POS;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Controller {

    private String codeLogin = null;
    private Integer status = 0;
    private Integer neworder = 0;
    private Integer option = 3;
    private String mainProductSelect = "";
    private String nameLogin;
    private List<String> allMenu = new ArrayList<String>();
    private List<String> toppingAdd = new ArrayList<String>();
    private List<String> toppingName = new ArrayList<String>();
    private List<Float> allPrice = new ArrayList<Float>();
    private List<Float> toppingPrice = new ArrayList<Float>();
    private Float newOrderPrice = Float.valueOf(0);
    private Float newOrderSave = Float.valueOf(0);

    @FXML
    private ListView<String> oldOrderList,summaryZone;

    @FXML
    private Label loginAs,msgOrder;

    @FXML
    private GridPane newOrderZone;

    @FXML
    private Button newOrderBTN;

    @FXML
    private Button btnMenu3;

    @FXML
    private Button btnMenu2;

    @FXML
    private Button btnMenu1;

    @FXML
    private Button btnMenu4;

    @FXML
    private ImageView imageMenu1,imageMenu2,imageMenu3,imageMenu4;

    @FXML
    private Label totalPrice;

    @FXML
    private Button btnOption2;

    @FXML
    private Button btnOption1;

    @FXML
    private Button btnOption3;

    @FXML
    private Button btnOption4;

    @FXML
    private Button btnTop1;

    @FXML
    private Button btnTop2;

    @FXML
    private Button btnTop3;

    @FXML
    private Button btnTop4;

    @FXML
    public void initialize() {
        if (this.status == 1) {
            connect();
            System.out.println("> Passing");
            loginAs.setText("Login as : " + this.nameLogin);
            oldOrderList.getItems().add("Stamp");
            oldOrderList.getItems().add("Karn");
            oldOrderList.getItems().add("Armean");
            oldOrderList.getItems().add("Nut");

            // add menu
            int i =1;
            Scene scene = newOrderZone.getScene();
            for (String object: allMenu) {
                String setNameMenu = "btnMenu"+i;
                String setImageMenu = "imageMenu"+i;
                System.out.println("> Add menu "+object+" to "+setNameMenu);
                Button btn = (Button) scene.lookup("#"+setNameMenu);
                ImageView image = (ImageView) scene.lookup("#"+setImageMenu);
                image.setImage(new Image("image/"+setNameMenu+".png"));
                btn.setText(object);
                i++;
            }

            // add menu
            int j =1;
            for (String object: toppingName) {
                String setNameTop = "btnTop"+j;
                String setImageTop = "imageTop"+j;
                System.out.println("> Add topping "+object+" to "+setNameTop);
                Button btn = (Button) scene.lookup("#"+setNameTop);
                ImageView image = (ImageView) scene.lookup("#"+setImageTop);
                image.setImage(new Image("image/"+setNameTop+".png"));
                btn.setText(object);
                j++;
            }
        }
    }

    public void setCodeLogin(String codeLogin) {
        this.codeLogin = codeLogin;
        this.status = 1;
    }

    public String getCodeLogin() {
        return codeLogin;
    }

    public Connection connect() {
        Connection conn = null;
        Statement stmt = null;
        try {
            String url = "jdbc:sqlite:db.sqlite";
            conn = DriverManager.getConnection(url);
            System.out.println("> Connection to SQLite has been successfully.");
            String sql = "SELECT * FROM userLogin WHERE codeLogin = " + this.getCodeLogin() + ";";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            nameLogin = rs.getString(2);


            // ----- Add menu ---
            String sqlGetMenu = "SELECT * FROM mainProduct;";
            stmt = conn.createStatement();
            ResultSet rsGetMenu = stmt.executeQuery(sqlGetMenu);
            while (rsGetMenu.next()) {
                String menuName = rsGetMenu.getString("name");
                Float menuPrice = rsGetMenu.getFloat("price");
                allMenu.add(menuName);
                allPrice.add(menuPrice);
            }

            // ----- Add topping ---
            String sqlGetTop = "SELECT * FROM topping;";
            stmt = conn.createStatement();
            ResultSet rsGetTop = stmt.executeQuery(sqlGetTop);
            while (rsGetTop.next()) {
                String menuName = rsGetTop.getString("name");
                Float menuPrice = rsGetTop.getFloat("price");
                toppingName.add(menuName);
                toppingPrice.add(menuPrice);
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
    @FXML
    void clickToNewOrder(MouseEvent event) {
        neworder = 0;
        option = 3;
        toppingAdd.clear();
        summaryZone.getItems().clear();
        newOrderPrice = Float.valueOf(0);
        newOrderSave = Float.valueOf(0);
        totalPrice.setText("");
        newOrderZone.setVisible(true);
        System.out.println("> You clicked on New Order!!");
    }

    @FXML
    public void handleMouseClick(MouseEvent mouseEvent) {
        newOrderZone.setVisible(false);
        neworder = 0;
        option = 3;
        toppingAdd.clear();
        summaryZone.getItems().clear();
        newOrderPrice = Float.valueOf(0);
        newOrderSave = Float.valueOf(0);
        totalPrice.setText("");
        System.out.println("> You clicked on " + oldOrderList.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void clicktoSelectMainOrder(MouseEvent mouseEvent) {

        String idMenu = ((Button)mouseEvent.getSource()).getId().toString();
        String menuID = idMenu.substring(idMenu.length() - 1);
        String menuName = allMenu.get(Integer.parseInt(menuID) -1 );
        Float menuPrice = allPrice.get(Integer.parseInt(menuID) -1 );

        // new order
        msgOrder.setText("");
        neworder = 1;
        option = 3;
        summaryZone.getItems().clear();
        newOrderPrice = Float.valueOf(0);
        newOrderSave = Float.valueOf(0);

        System.out.println("> You clicked on Main order id : " + idMenu + " | " + menuID + " | " + menuName + " | " +menuPrice);
        summaryZone.getItems().add(menuName + " [ "+menuPrice+" Baht ]");
        summaryZone.getItems().add("   = Sugar : Normal 50%");
        newOrderPrice += menuPrice;
        newOrderSave = menuPrice;
        totalPrice.setText(String.valueOf(newOrderPrice));
        mainProductSelect = menuID;
        toppingAdd.clear();
    }

    @FXML
    void clickOption(MouseEvent mouseEvent) {

        String idMenu = ((Button)mouseEvent.getSource()).getId().toString();
        System.out.println("> You clicked on option : " + idMenu);
        if(neworder == 0){
            msgOrder.setText("Please select Main product before select option and topping.");
        }else{
            summaryZone.getItems().remove(1,summaryZone.getItems().size());
            toppingAdd.clear();
            newOrderPrice = newOrderSave;
            totalPrice.setText(String.valueOf(newOrderPrice));
            if(idMenu.equals("btnOption1")){
                summaryZone.getItems().add("   = Sugar : SugarFree 0%");
                option = 1;
            }else if(idMenu.equals("btnOption2")){
                summaryZone.getItems().add("   = Sugar : Light 25%");
                option = 2;
            }else if(idMenu.equals("btnOption3")){
                summaryZone.getItems().add("   = Sugar : Normal 50%");
                option = 3;
            }else if(idMenu.equals("btnOption4")){
                summaryZone.getItems().add("   = Sugar : More 75%");
                option = 4;
            }

        }


    }



    @FXML
    public void clickToResetTopping(MouseEvent mouseEvent) {
        System.out.println("> Reset Topping");
        summaryZone.getItems().remove(2,summaryZone.getItems().size());
        toppingAdd.clear();
        newOrderPrice = newOrderSave;
        totalPrice.setText(String.valueOf(newOrderPrice));
    }

    public void clickToAddTopping(MouseEvent mouseEvent) {
        String idMenu = ((Button) mouseEvent.getSource()).getId().toString();
        String menuID = idMenu.substring(idMenu.length() - 1);
        String topName = toppingName.get(Integer.parseInt(menuID) - 1);
        Float topPrice = toppingPrice.get(Integer.parseInt(menuID) - 1);

        // add topping
        System.out.println("> You clicked on topping id : " + idMenu + " | " + menuID + " | " + topName + " | " + topPrice);
        if (neworder == 0) {
            msgOrder.setText("Please select Main product before select option and topping.");
        } else {
            if(toppingAdd.contains(menuID.toString())){
            }else{
                summaryZone.getItems().add("   + "+topName + " [ " + topPrice + " Baht ]");
                newOrderPrice += Float.valueOf(topPrice);
                totalPrice.setText(String.valueOf(newOrderPrice));
                toppingAdd.add(menuID);
            }
        }
    }

    @FXML
    void comfirmOerder(MouseEvent event) {
        if(neworder==0){
            msgOrder.setText("Please select Main product and select topping.");
            newOrderZone.setVisible(true);
        }else{
            Date date = new Date();
            Order order = new Order(date,nameLogin,mainProductSelect,option,toppingAdd,newOrderPrice);
            System.out.println(order.toString());
        }


    }

    @FXML
    void resetAll(MouseEvent event) {
        neworder = 0;
        option = 3;
        toppingAdd.clear();
        summaryZone.getItems().clear();
        newOrderPrice = Float.valueOf(0);
        newOrderSave = Float.valueOf(0);
        totalPrice.setText("");
        newOrderZone.setVisible(true);
    }


}


