package controller;


import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;



import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AlertFormController {

    public Label lblId;
    public Label lblName;
    public Label lblDate;

    public void initialize() throws URISyntaxException {
        Media media = new Media(this.getClass().getResource("/assets/sirenSound2.wav").toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
        player.setCycleCount(2);
        player.play();
    }

    public void initData(String studentId, String studentName, LocalDateTime date, boolean in){
        lblId.setText("ID: " + studentId.toUpperCase());
        lblName.setText("NAME: " + studentName.toUpperCase());
        lblDate.setText(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")) + " - " + (in? "IN" : "OUT"));
    }
}
