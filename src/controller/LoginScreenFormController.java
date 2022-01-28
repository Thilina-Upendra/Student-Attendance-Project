package controller;

import db.DBConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginScreenFormController {
    public TextField txtEnteredUserName;
    public TextField txtEnteredPassword;
    public Button btnLogin;


    public void btnLoginOnAction(ActionEvent actionEvent) {
        if (!isValidate()) {
            new Alert(Alert.AlertType.ERROR, "Invalid user name or password", ButtonType.OK).show();
            txtEnteredPassword.requestFocus();
            txtEnteredUserName.requestFocus();
            return;
        }

        /*We are going to get verification from the user table*/

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT name, role FROM user WHERE user_name=? AND password=?");
            stm.setString(1, txtEnteredUserName.getText().trim());
            stm.setString(2, txtEnteredPassword.getText().trim());
            ResultSet resultSet = stm.executeQuery();
            if(resultSet.next()){
                String path= null;
                /*Check on role*/
                if(resultSet.getString("role").equals("ADMIN")){
                    /*Load admin home*/
                    path = "/view/AdminHomeForm.fxml";
                }else{
                    /*Load user home*/
                    path = "/view/UserHomeForm.fxml";
                }
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource(path));
                AnchorPane root = fxmlLoader.load();

                if(path.equals("/view/AdminHomeForm.fxml")){
                    AdminUserFormController adminController = fxmlLoader.getController();
                    adminController.initUserName(resultSet.getString("name"));
                }else {
                    UserHomeFormController userController = fxmlLoader.getController();
                    userController.initUserName(resultSet.getString("name"));
                }

                Scene scene = new Scene(root);
                Stage stage = (Stage) txtEnteredUserName.getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.setTitle("Student Attendance : Home");
                Platform.runLater(()->{
                    stage.sizeToScene();
                    stage.centerOnScreen();
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private boolean isValidate() {

        String username = txtEnteredUserName.getText();
        String password = txtEnteredPassword.getText();
        return !(username.length() < 4 || password.length() < 4 || !username.matches("[A-Za-z0-9]+"));
    }
}

