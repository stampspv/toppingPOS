package POS.Controller;

import POS.Model.Order;
import animatefx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
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
    private ImageView bgOrders;

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
    private Label msgSuccess;

    @FXML
    private AnchorPane historyZone;

    @FXML
    private Label historyOrderID;

    @FXML
    private Label historyMain;

    @FXML
    private Label historyOption;

    @FXML
    private Label historyToppings;

    @FXML
    private Label historyTotal;

    @FXML
    private Label historyDate;

    @FXML
    private Label historyWho;

    @FXML
    private ImageView historyImage;

    @FXML
    private Label activeNewOrder;

    @FXML
    private Label activeOrders;

    @FXML
    private Button ordersBTN;

    @FXML
    private Label pleaseSelect;

    @FXML
    public void initialize() {
        if (this.status == 1) {
            connect();
            System.out.println("> Passing");
            newOrderZone.setVisible(true);
            historyZone.setVisible(false);
            loginAs.setText("Login as : " + this.nameLogin);
            activeOrders.setVisible(false);
            activeNewOrder.setVisible(true);
            bgOrders.setVisible(false);
            oldOrderList.setVisible(false);
            oldOrderList.toBack();
            pleaseSelect.setVisible(false);

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
                new FadeIn(image).playOnFinished(new FadeIn(btn)).play();
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
                new FadeIn(image).playOnFinished(new FadeIn(btn)).play();
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

            // ----- Add ListView ---
            String sqlGetOrders = "SELECT * FROM orders ORDER BY id DESC;";
            stmt = conn.createStatement();
            ResultSet rsGetOrder = stmt.executeQuery(sqlGetOrders);
            while (rsGetOrder.next()) {
                Integer orderId = rsGetOrder.getInt("id");
                Float orderPrice = rsGetOrder.getFloat("totalPrice");
                oldOrderList.getItems().add("Order : "+String.valueOf(orderId)+" [ "+orderPrice+" Baht ]");
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
        msgSuccess.setVisible(false);
        neworder = 0;
        option = 3;
        toppingAdd.clear();
        summaryZone.getItems().clear();
        newOrderPrice = Float.valueOf(0);
        newOrderSave = Float.valueOf(0);
        totalPrice.setText("");
        newOrderZone.setVisible(true);
        new FadeIn(newOrderZone).play();
        historyZone.setVisible(false);
        activeOrders.setVisible(false);
        activeNewOrder.setVisible(true);
        new FadeIn(activeNewOrder).play();
        bgOrders.setVisible(false);
        oldOrderList.setVisible(false);
        oldOrderList.toBack();
        pleaseSelect.setVisible(false);
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
        msgSuccess.setVisible(false);
        activeOrders.setVisible(true);
        activeNewOrder.setVisible(false);
        bgOrders.setVisible(true);
        oldOrderList.setVisible(true);
        pleaseSelect.setVisible(false);

        System.out.println("> You clicked on " + oldOrderList.getSelectionModel().getSelectedItem());
        String[] getID = oldOrderList.getSelectionModel().getSelectedItem().split(" ");
        String idOrder = getID[2];
        historyOrderID.setText("Order ID : "+idOrder);
        // connect
        Connection conn = null;
        Statement stmt = null;
        try {
            String url = "jdbc:sqlite:db.sqlite";
            conn = DriverManager.getConnection(url);
            String sqlGetOrder = "SELECT * FROM orders WHERE id = " + idOrder + ";";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlGetOrder);
            historyDate.setText("Order at : "+rs.getString(2));
            historyWho.setText("Order by : "+rs.getString(3));
            historyTotal.setText("Total Price : "+rs.getString(7)+" Baht");

            String toppingList = rs.getString(6);

            // Option
            if(rs.getString(5).equals("1")){
                historyOption.setText("Option : Sugar Free 0%");
            }else if(rs.getString(5).equals("2")){
                historyOption.setText("Option : Sugar Light 25%");
            }else if(rs.getString(5).equals("3")){
                historyOption.setText("Option : Sugar Normal 50%");
            }else if(rs.getString(5).equals("4")){
                historyOption.setText("Option : Sugar More 75%");
            }

            // Main
            String idMain = rs.getString(4);
            String sqlGetMain = "SELECT * FROM mainProduct WHERE id = " + rs.getString(4) + ";";
            ResultSet rsMain = stmt.executeQuery(sqlGetMain);
            historyMain.setText("Main : "+rsMain.getString(2));

            // Toppings
            char[] toppings = toppingList.toCharArray();
            String toppingAdd = "";
            for(char topping : toppings){
                if (Character.isDigit(topping)){
                    String sqlGetTopping = "SELECT * FROM topping WHERE id = " + topping + ";";
                    ResultSet rsTop = stmt.executeQuery(sqlGetTopping);
                    toppingAdd += rsTop.getString(2)+",";
                }

            }
            historyToppings.setText("Toppings : "+toppingAdd);

            // Image
            String setNameMenu = "btnMenu"+idMain;
            historyImage.setImage(new Image("image/"+setNameMenu+".png"));
            conn.close();

        }catch (SQLException e) {
            e.printStackTrace();
        }


        historyZone.setVisible(true);
        new FadeInUp(historyZone).play();
    }

    @FXML
    public void clicktoSelectMainOrder(MouseEvent mouseEvent) {

        String idMenu = ((Button)mouseEvent.getSource()).getId().toString();
        String menuID = idMenu.substring(idMenu.length() - 1);
        String menuName = allMenu.get(Integer.parseInt(menuID) -1 );
        Float menuPrice = allPrice.get(Integer.parseInt(menuID) -1 );

        // new order
        historyZone.setVisible(false);
        msgSuccess.setVisible(false);
        msgOrder.setText("");
        neworder = 1;
        option = 3;
        summaryZone.getItems().clear();
        newOrderPrice = Float.valueOf(0);
        newOrderSave = Float.valueOf(0);
        bgOrders.setVisible(false);
        oldOrderList.setVisible(false);

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
        historyZone.setVisible(false);
        msgSuccess.setVisible(false);
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
        historyZone.setVisible(false);
        msgSuccess.setVisible(false);
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
        historyZone.setVisible(false);
        msgSuccess.setVisible(false);
        System.out.println("> You clicked on topping id : " + idMenu + " | " + menuID + " | " + topName + " | " + topPrice);
        if (neworder == 0) {
            msgOrder.setText("Please select Main product before select option and topping.");
            new Shake(msgOrder.getScene().getRoot()).play();
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
            new Shake(msgOrder.getScene().getRoot()).play();
            newOrderZone.setVisible(true);
            historyZone.setVisible(false);
            activeOrders.setVisible(false);
            activeNewOrder.setVisible(true);
            bgOrders.setVisible(false);
            oldOrderList.setVisible(false);
            pleaseSelect.setVisible(false);
        }else{
            Date date = new Date();

            // Singleton Pattern ----------------
            Order order = Order.getInstance();
            order.setDateOrder(date);
            order.setWho(nameLogin);
            order.setMainProduct(mainProductSelect);
            order.setOption(option);
            order.setTopping(toppingAdd);
            order.setTotalPrice(newOrderPrice);
            order.orderHere();
            // ----------------------------------

            System.out.println("> "+order.toString());
            oldOrderList.getItems().add(0,"Order : "+order.getId()+" [ "+newOrderPrice+" Baht ]");
            resetAll();
            msgSuccess.setVisible(true);
            new SlideInUp(msgSuccess).play();

        }
    }

    private void resetAll() {
        msgSuccess.setVisible(false);
        neworder = 0;
        option = 3;
        toppingAdd.clear();
        summaryZone.getItems().clear();
        newOrderPrice = Float.valueOf(0);
        newOrderSave = Float.valueOf(0);
        totalPrice.setText("");
        newOrderZone.setVisible(true);
        historyZone.setVisible(false);
        activeOrders.setVisible(false);
        activeNewOrder.setVisible(true);
        bgOrders.setVisible(false);
        oldOrderList.setVisible(false);
        pleaseSelect.setVisible(false);
        pleaseSelect.setVisible(false);
    }

    @FXML
    void resetAll(MouseEvent event) {
        msgSuccess.setVisible(false);
        neworder = 0;
        option = 3;
        toppingAdd.clear();
        summaryZone.getItems().clear();
        newOrderPrice = Float.valueOf(0);
        newOrderSave = Float.valueOf(0);
        totalPrice.setText("");
        newOrderZone.setVisible(true);
        historyZone.setVisible(false);
        activeOrders.setVisible(false);
        activeNewOrder.setVisible(true);
        bgOrders.setVisible(false);
        oldOrderList.setVisible(false);
        pleaseSelect.setVisible(false);
    }

    @FXML
    void clickToOrders(MouseEvent event) {
        bgOrders.setVisible(true);
        new FadeIn(bgOrders).play();
        oldOrderList.setVisible(true);
        new FadeIn(oldOrderList).play();
        System.out.println("> You click to show all Orders");
        activeOrders.setVisible(true);
        new FadeIn(activeOrders).play();
        activeNewOrder.setVisible(false);
        newOrderZone.setVisible(false);
        historyZone.setVisible(false);
        pleaseSelect.setVisible(true);
        new FadeIn(pleaseSelect).play();

    }

    @FXML
    void clicktoAdmin(MouseEvent event) throws IOException, SQLException {
        System.out.println("> Click to Admin Zone");
        Stage stage = (Stage) loginAs.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin.fxml"));
        stage.setScene(new Scene((Parent) loader.load(),990,600));
        AdminController controller = (AdminController) loader.getController();
        controller.setCodeLogin(getCodeLogin());
        controller.initialize();
        stage.setTitle("Admin Topping POS");
        stage.setResizable(false);
        stage.show();
    }

}


