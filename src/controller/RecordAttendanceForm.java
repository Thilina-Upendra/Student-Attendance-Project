package controller;

import com.sun.javafx.binding.StringFormatter;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.util.Date;

public class RecordAttendanceForm {

    public TextField txtStudentId;
    public TextField txtStudentName;
    public Button btnIn;
    public Button btnOut;
    public TextArea txtLastRecord;
    public Label lblDateAndTime;


    public void initialize(){
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
    }

    public void txtStudentIdOnAction(ActionEvent actionEvent) {
    }
}
