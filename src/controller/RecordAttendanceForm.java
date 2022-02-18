package controller;

import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class RecordAttendanceForm {

    public TextField txtStudentId;
    public TextField txtStudentName;
    public Button btnIn;
    public Button btnOut;
    public TextArea txtLastRecord;
    public Label lblDateAndTime;
    public AnchorPane root;
    public ImageView imgProfile;
    private PreparedStatement stmSearchStudent;

    public void initialize() {
        lblDateAndTime.setText(String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %1$Tp", new Date()));
        /*Setting up the time and date*/
            /*new Thread(() -> {
                while(true){
                    try {
                        Platform.runLater(() -> {
                            lblDateAndTime.setText(String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %1$Tp",new Date()));
                        });
                        Thread.sleep(950);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();*/
        /*Create a new TimeLine and put new Key frame inside to the TimeLine*/
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            lblDateAndTime.setText(String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %1$Tp", new Date()));
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        /*Create a connection and pass it into RecordAttendanceForm attribute stmSearchStudent*/
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            stmSearchStudent = connection.prepareStatement("SELECT * FROM student WHERE id=?");
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to connect with DB", ButtonType.OK).showAndWait();
            e.printStackTrace();
            Platform.runLater(() -> {
                ((Stage)(btnIn.getScene().getWindow())).close();
            });
        }

        root.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case F10:
                    btnIn.fire();
                    break;
                case ESCAPE:
                    btnOut.fire();
                    break;
            }
        });

    }

    public void txtStudentIdOnAction(ActionEvent actionEvent) {
        btnIn.setDisable(true);
        btnOut.setDisable(true);
        txtStudentName.setText("Please enter/scan the student ID to proceed");
        imgProfile.setImage(new Image("/view/assets/cropedQRCode.png"));

        if (txtStudentId.getText().trim().isEmpty()) {
            return;
        }

        try {
            stmSearchStudent.setString(1, txtStudentId.getText().trim());
            ResultSet rst = stmSearchStudent.executeQuery();

            if (rst.next()) {
                txtStudentName.setText(rst.getString("name").toUpperCase());
                InputStream is = rst.getBlob("picture").getBinaryStream();
                imgProfile.setImage(new Image(is));
                imgProfile.setFitHeight(175);
                imgProfile.setPreserveRatio(true);
                btnIn.setDisable(false);
                btnOut.setDisable(false);
                txtStudentId.selectAll();
                System.out.println("Message has sent to "+rst.getString("guardian_contact"));
            } else {
                //new DepAlert(Alert.AlertType.ERROR, "Invalid Student ID, Try again!", "Oops!", "Error").show();
                new Alert(Alert.AlertType.ERROR, "Invalid Student ID, Try again", ButtonType.OK).show();
                txtStudentId.selectAll();
                txtStudentId.requestFocus();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //new DepAlert(Alert.AlertType.WARNING, "Something went wrong. Please try again!", "Connection Failure", "Error").show();
            new Alert(Alert.AlertType.ERROR, "Something went wrong. Please try again", ButtonType.OK).show();
            txtStudentId.selectAll();
            txtStudentId.requestFocus();
        }
    }

}

