package controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AlertFormController {

    public Label lblId;
    public Label lblName;
    public Label lblDate;
    public Button btnProceed;
    public Button btnCallPolice;
    private SimpleObjectProperty<String> stringSimpleObjectProperty;

    public void initialize() throws URISyntaxException {
        playSiren();
    }

    private void playSiren() throws URISyntaxException {
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

    public void initStringProperty(SimpleObjectProperty<String> stringSimpleObjectProperty){
        this.stringSimpleObjectProperty = stringSimpleObjectProperty;
    }

    public void btnProceedOnAction(ActionEvent actionEvent) {
        stringSimpleObjectProperty.setValue(btnProceed.getText());
        ((Stage)(btnProceed.getScene().getWindow())).close();
    }

    public void btnCallPoliceOnAction(ActionEvent actionEvent) {

    }
}
