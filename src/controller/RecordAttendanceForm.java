package controller;

import com.sun.xml.internal.bind.v2.TODO;
import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import security.SecurityContextHolder;

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
    private String studentId;

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
                studentId = txtStudentId.getText();
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

    public void btnIn_OnAction(ActionEvent actionEvent) {
        recordAttendance(true);
    }

    public void btnOut_OnAction(ActionEvent actionEvent) {
        recordAttendance(false);
    }

    private void recordAttendance(boolean in){
        Connection connection = DBConnection.getInstance().getConnection();

        try{
            String lastStatus = null;
            PreparedStatement stm1 = connection.prepareStatement("SELECT status, date FROM attendance WHERE student_id=? ORDER BY date DESC LIMIT 1");
            stm1.setString(1,studentId);
            ResultSet rst1 = stm1.executeQuery();

            if(rst1.next()){
                lastStatus = rst1.getString("status");
            }

            if((lastStatus != null && lastStatus.equals("IN") && in)
                || (lastStatus !=null && lastStatus.equals("OUT") && !in)){
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/AlertForm.fxml"));
                AnchorPane root = fxmlLoader.load();
                AlertFormController controller = fxmlLoader.getController();
                controller.initData(studentId,txtStudentName.getText(),
                        rst1.getTimestamp("date").toLocalDateTime(), in);
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Alert! Horek");
                stage.sizeToScene();
                stage.centerOnScreen();
                stage.showAndWait();


                /*------------------------*/

            } else{
                /*Here can save the attendance in the attendance table*/
                PreparedStatement stm2 = connection.prepareStatement("INSERT INTO attendance (date, status, student_id, username)  VALUES (NOW(), ?,?,?)");
                stm2.setString(1, in ? "IN" : "OUT");
                stm2.setString(2, studentId);
                stm2.setString(3, SecurityContextHolder.getPrincipal().getUserName());
                int rst2 = stm2.executeUpdate();
                if(rst2 != 1){
                    throw new RuntimeException("Failed to add the attendance.");
                }

                txtStudentId.clear();
                txtStudentIdOnAction(null);
            }
        }catch (Throwable e){
            //TODO: Continue here
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to save attendance, try again").show();
        }
    }
}

