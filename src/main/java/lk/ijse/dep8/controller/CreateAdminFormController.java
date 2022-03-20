package lk.ijse.dep8.controller;

import lk.ijse.dep8.db.DBConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateAdminFormController {

    public TextField txtName;
    public TextField txtUserName;
    public TextField txtPassword;
    public TextField txtConfirmPassword;
    public Button btnCreateAccount;

    public void btnCreateAccountOnAction(ActionEvent actionEvent) {

        if(!isValidated()){
            return;
        }

        /*after all the validation redirect to the login form */

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement stm = connection.prepareStatement("INSERT INTO user (user_name, name, password, role) VALUES (?,?,?,?)");
            stm.setString(1, txtUserName.getText().trim());
            stm.setString(2, txtName.getText().trim());
            stm.setString(3, txtPassword.getText().trim());
            stm.setString(4, "ADMIN");
            stm.executeUpdate();

            new Alert(Alert.AlertType.INFORMATION, "Your account has been created successfully").showAndWait();

            /* Let's redirect to the Login Form */
            AnchorPane root = FXMLLoader.load(this.getClass().getResource("/view/LoginScreenForm.fxml"));
            Scene loginScene = new Scene(root);
            Stage primaryStage = new Stage();
            primaryStage.setScene(loginScene);
            primaryStage.setTitle("Student Attendance System: Log In");
            primaryStage.setResizable(false);
            primaryStage.centerOnScreen();
            Platform.runLater(() -> primaryStage.sizeToScene());
            primaryStage.show();
            ((Stage)(btnCreateAccount.getScene().getWindow())).close();
        } catch (SQLException | IOException e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please try again").show();
            e.printStackTrace();
        }
    }

    private boolean isValidated() {
        /*String name = txtName.getText();
        String filteredUserName = txtUserName.getText().trim();
        String password = txtPassword.getText();
        String confirmedPassword = txtConfirmPassword.getText();
        if (!(filteredUserName.length() > 4 && filteredUserName.matches("[A-z0-9]"))) {
            new Alert(Alert.AlertType.ERROR, "Invalid Username and Password..", ButtonType.OK).showAndWait();
            return false;
        } else if (!(password.length() > 4)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Username and Password..", ButtonType.OK).showAndWait();
            return false;
        } else if (!(confirmedPassword.length() > 4 && confirmedPassword.equals(password))) {
            new Alert(Alert.AlertType.ERROR, "Invalid Password confirmation..").showAndWait();
            return false;
        }
        return true;*/
        String name = txtName.getText().trim();
        String username = txtUserName.getText().trim();
        String password = txtPassword.getText().trim();
        String confirmPassword = txtConfirmPassword.getText().trim();

        if (!name.matches("[A-Za-z ]+")) {
            new Alert(Alert.AlertType.ERROR, "Please enter valid name").show();
            txtName.selectAll();
            txtName.requestFocus();
            return false;
        } else if (username.length() < 4) {
            new Alert(Alert.AlertType.ERROR, "Username should be at least 4 characters long").show();
            txtUserName.selectAll();
            txtUserName.requestFocus();
            return false;
        } else if (!username.matches("[A-Za-z0-9]+")) {
            new Alert(Alert.AlertType.ERROR, "Username can contain only characters and digits").show();
            txtUserName.selectAll();
            txtUserName.requestFocus();
            return false;
        } else if (password.length() < 4) {
            new Alert(Alert.AlertType.ERROR, "Password should be at least 4 characters long").show();
            txtPassword.selectAll();
            txtPassword.requestFocus();
            return false;
        } else if (!password.equals(confirmPassword)) {
            new Alert(Alert.AlertType.ERROR, "Password mismatch").show();
            txtConfirmPassword.selectAll();
            txtConfirmPassword.requestFocus();
            return false;
        }
        return true;
    }
}
