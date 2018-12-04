package POS.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.sql.*;
import java.util.Date;

public class AdminController {

    private String codeLogin = null;
    private Integer status = 0;
    private String name = null;
    private Integer id = null;
    private String price = null;
    private String type = null;

    @FXML
    private Button btnBack;

    @FXML
    private ListView<String> menuList;

    @FXML
    private Label pleaseSelect;

    @FXML
    private Label todayOrders;

    @FXML
    private Label todayIncomes;

    @FXML
    private Label alltimeOrders1;

    @FXML
    private Label alltimeIncomes;

    @FXML
    private Label nameLogin;

    private String nameLoginAS;

    @FXML
    private AnchorPane editZone;

    @FXML
    private Label editText;

    @FXML
    private TextField inputName;

    @FXML
    private TextField inputPrice;

    @FXML
    private TextField inputID;

    @FXML
    private Label editSuccess;


    public void setCodeLogin(String codeLogin) {
        this.codeLogin = codeLogin;
        this.status = 1;
    }

    public String getCodeLogin() {
        return codeLogin;
    }

    @FXML
    public void initialize() throws SQLException {
        if(status == 1){
            System.out.println("> Welcome to AdminZone");
            connect();
            System.out.println("> Passing");
            nameLogin.setText("Login as : " + this.nameLoginAS);
            pleaseSelect.setVisible(true);
            editZone.setVisible(false);
            editSuccess.setVisible(false);
        }

    }

    public Connection connect() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try {
            String url = "jdbc:sqlite:db.sqlite";
            conn = DriverManager.getConnection(url);
            System.out.println("> Connection to SQLite has been successfully.");
            String sql = "SELECT * FROM userLogin WHERE codeLogin = " + this.getCodeLogin() + ";";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            nameLoginAS = rs.getString(2);


            // ----- Add menu ---
            String sqlGetMenu = "SELECT * FROM mainProduct;";
            stmt = conn.createStatement();
            ResultSet rsGetMenu = stmt.executeQuery(sqlGetMenu);
            while (rsGetMenu.next()) {
                String menuName = rsGetMenu.getString("name");
                menuList.getItems().add("Main : "+menuName);
                System.out.println("> Add main "+menuName+" to List.");
            }

            // ----- Add topping ---
            String sqlGetTop = "SELECT * FROM topping;";
            stmt = conn.createStatement();
            ResultSet rsGetTop = stmt.executeQuery(sqlGetTop);
            while (rsGetTop.next()) {
                String menuName = rsGetTop.getString("name");
                menuList.getItems().add("Topping : "+menuName);
                System.out.println("> Add topping "+menuName+" to List.");
            }

            // get alltime count
            String sqlGetAlltimeOrder = "SELECT COUNT(*) AS count FROM orders;";
            stmt = conn.createStatement();
            ResultSet rsGetAlltimeOrder = stmt.executeQuery(sqlGetAlltimeOrder);
            String count = rsGetAlltimeOrder.getString("count");
            alltimeOrders1.setText(count);
            System.out.println("> Add alltime orders count");

            // get alltime sum
            String sqlGetAlltimeSum = "SELECT SUM(totalPrice) AS count FROM orders;";
            stmt = conn.createStatement();
            ResultSet rsGetAlltimeSum = stmt.executeQuery(sqlGetAlltimeSum);
            String sum = rsGetAlltimeSum.getString("count");
            alltimeIncomes.setText(sum);
            System.out.println("> Add alltime incomes");

            // get alltime count
            Date today = new Date();
            String todaystring = String.valueOf(today);
            todaystring = todaystring.substring(0,10)+" "+todaystring.substring(24);
            System.out.println(todaystring);

            // ----- Get order today
            String sqlTodayp = "SELECT * FROM orders;";
            stmt = conn.createStatement();
            ResultSet rsToday = stmt.executeQuery(sqlTodayp);
            Integer countToday = 0;
            Float sumToday = Float.valueOf(0);
            while (rsToday.next()) {
                String dateorder = rsToday.getString("dateorder");
                dateorder = dateorder.substring(0,10)+" "+dateorder.substring(24);
                if(dateorder.equals(todaystring)){
                    countToday++;
                    Float totalPrice = rsToday.getFloat("totalPrice");
                    sumToday += totalPrice;
                }
            }
            todayOrders.setText(String.valueOf(countToday));
            System.out.println("> Add today orders count");
            todayIncomes.setText(String.valueOf(sumToday));
            System.out.println("> Add today incomes");


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (conn != null) {
                    conn.close();
                    System.out.println("> Con close");
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return conn;
    }

    @FXML
    void clicktoBack(MouseEvent event) throws IOException, InterruptedException {
        System.out.println("> Click to Admin Zone");
        Stage stage = (Stage) btnBack.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cashier.fxml"));
        stage.setScene(new Scene((Parent) loader.load(),990,600));
        Controller controller = (Controller) loader.getController();
        controller.setCodeLogin(getCodeLogin());
        controller.initialize();
        stage.setTitle("Topping POS");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void selectFromList(MouseEvent event) {
        String[] getID = menuList.getSelectionModel().getSelectedItem().split(" : ");
        String idOrder = getID[1];
        idOrder = idOrder.trim();
        System.out.println("> You clicked on " + menuList.getSelectionModel().getSelectedItem() +" as a "+idOrder);

        // ----- Check ---
        Connection conn = null;
        Statement stmt = null;

        if(getID[0].equals("Main")){
            try {
                String url = "jdbc:sqlite:db.sqlite";
                conn = DriverManager.getConnection(url);
                String sqlGetOrder = "SELECT * FROM mainProduct WHERE name = '" + (String)idOrder + "';";
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sqlGetOrder);
                name = rs.getString("name");
                price = rs.getString("price");
                id = rs.getInt("id");
                conn.close();
            }catch (SQLException $x){
                System.out.println($x);
            }
            type = "main";
        }else{
            try {
                String url = "jdbc:sqlite:db.sqlite";
                conn = DriverManager.getConnection(url);
                String sqlGetOrder = "SELECT * FROM topping WHERE name = '" + (String)idOrder + "';";
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sqlGetOrder);
                name = rs.getString("name");
                price = rs.getString("price");
                id = rs.getInt("id");
                conn.close();
            }catch (SQLException $x){
                System.out.println($x);
            }
            type = "topping";
        }

        editText.setText("Edit "+name);
        inputID.setText(String.valueOf(id));
        inputName.setText(String.valueOf(name));
        inputPrice.setText(String.valueOf(price));
        pleaseSelect.setVisible(false);
        editSuccess.setVisible(false);
        editZone.setVisible(true);

    }


    @FXML
    void editBTN(MouseEvent event) throws SQLException {
        System.out.println("> Edit "+inputName.getText());
        Connection conn = null;
        Statement stmt = null;
        try {
            String url = "jdbc:sqlite:db.sqlite";
            conn = DriverManager.getConnection(url);
            System.out.println("> Connection to SQLite has been successfully.");
            String sqlText = null;
            if(type.equals("main")){
                sqlText = "UPDATE mainProduct SET `name`= '"+inputName.getText()+"' , `price` = '"+inputPrice.getText()+"' WHERE id = '"+inputID.getText()+"'";
            }else{
                sqlText = "UPDATE topping SET `name`= '"+inputName.getText()+"' , `price` = '"+inputPrice.getText()+"' WHERE id = '"+inputID.getText()+"'";
            }
            System.out.println(sqlText);
            PreparedStatement prepare = conn.prepareStatement(sqlText);
            prepare.executeUpdate();
            prepare.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("> Error to edit");
        }

        //
        System.out.println("> Edit "+inputName.getText());
        menuList.getItems().clear();
        connect();
        editSuccess.setVisible(true);

    }

}
