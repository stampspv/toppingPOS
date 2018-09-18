package POS;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

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
    public void initialize() {
        if (this.status == 1){
            connect();
            System.out.println("> Passing");
            loginAs.setText("Login as : "+this.nameLogin);
            oldOrderList.getItems().add("Stamp");
            oldOrderList.getItems().add("Stamp");
            oldOrderList.getItems().add("Stamp");
            oldOrderList.getItems().add("Stamp");
            oldOrderList.getItems().add("Stamp");
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
            String sql = "SELECT * FROM userLogin WHERE codeLogin = "+this.getCodeLogin()+";";
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
}

