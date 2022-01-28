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

public class UserHomeFormController {

    public Button btnRecordAttendance;
    public Button btnViewReports;
    public Button btnUserProfile;
    public Button btnSignOut;
    public Label lblUserName;

    public void initialize() {
        lblUserName.setText("Welcome Admin : " + SecurityContextHolder.getPrincipal().getName() + " !");
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

    public void btnViewReportsOnAction(ActionEvent actionEvent) {
    }

    public void btnUserProfileOnAction(ActionEvent actionEvent) {
    }

    public void btnSignOutOnAction(ActionEvent actionEvent) {
    }
}
