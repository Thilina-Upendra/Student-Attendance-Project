package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import security.SecurityContextHolder;

import java.io.IOException;

public class AdminUserFormController {
    public Button btnRecordAttendance;
    public Button btnViewReport;
    public Button btnUserProfile;
    public Button btnManageUser;
    public Button btnBackRestore;
    public Button btnSignOut;


    public Label lblCurrentUser;

    public void initialize(){
        lblCurrentUser.setText("Welcome Admin : "+ SecurityContextHolder.getPrincipal().getName()+" !");
    }


    public void btnRecordAttendanceOnAction(ActionEvent actionEvent) throws IOException {
        /*Here load Record attendance UI*/
        AnchorPane root = FXMLLoader.load(this.getClass().getResource("/view/RecordAttendanceForm.fxml"));
        Scene attendanceScene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(attendanceScene);
        stage.setTitle("Student attendance System: Record Attendance");
        stage.setResizable(false);
        stage.initOwner(btnRecordAttendance.getScene().getWindow());
        stage.show();

        Platform.runLater(() -> {
            stage.centerOnScreen();
            stage.sizeToScene();
        });
    }

    public void btnViewReportOnAction(ActionEvent actionEvent) {
    }

    public void btnUserProfileOnAction(ActionEvent actionEvent) {
    }

    public void btnManageUserOnAction(ActionEvent actionEvent) {
    }

    public void btnBackRestoreOnAction(ActionEvent actionEvent) {
    }

    public void btnSignOutOnAction(ActionEvent actionEvent) {
    }
}
