package POS;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Controller {

    private String codeLogin = null;
    private Integer status = 0;
    private String nameLogin;

    @FXML
    private ListView<String> oldOrderList;

    @FXML
    private Label loginAs;

    @FXML
    private GridPane newOrderZone;

    @FXML
    private Button newOrderBTN;

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
        newOrderZone.setVisible(true);
        System.out.println("> You clicked on New Order!!");
    }

    @FXML
    public void handleMouseClick(MouseEvent mouseEvent) {
        newOrderZone.setVisible(false);
        System.out.println("> You clicked on " + oldOrderList.getSelectionModel().getSelectedItem());
    }
}


