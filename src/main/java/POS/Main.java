package POS;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.event.ActionEvent;

public class Main extends Application {
    private static Throwable e;
    private static ArrayList<String> allLogin = new ArrayList<String>();

    @FXML
    private PasswordField inputCode;

    @FXML
    private Label incorrctMsg;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
        primaryStage.setTitle("Topping POS");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        primaryStage.show();

    }
    public static Connection connect() {
        Connection conn = null;
        Statement stmt = null;
        try {
            String url = "jdbc:sqlite:db.sqlite";
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been successfully.");
            String sql = "SELECT * FROM userLogin;";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Integer isAdmin = rs.getInt("isAdmin");
                String codeLogin = rs.getString("codeLogin");
                allLogin.add(codeLogin);

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

    public static void main(String[] args) {

        connect();
        launch(args);

    }
    @FXML
    private void handleOnButtonAction(ActionEvent event) throws IOException, SQLException {
        loginloop:
        for (String object: allLogin) {
            if (inputCode.getText().equals(object)){
                incorrctMsg.setText("Login...");
                Stage stage = (Stage) inputCode.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/cashier.fxml"));
                stage.setScene(new Scene((Parent) loader.load(),1000,600));
                Controller controller = (Controller) loader.getController();
                controller.setCodeLogin(inputCode.getText());
                controller.initialize();
                stage.setTitle("Topping POS");
                stage.setResizable(false);
                stage.show();
                break loginloop;
            }
        }
        inputCode.setText("");
        incorrctMsg.setVisible(true);
    }



}