package controller;

import javafx.scene.control.Label;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AlertFormController {

    public Label lblId;
    public Label lblName;
    public Label lblDate;

    public void initData(String studentId, String studentName, LocalDateTime date, boolean in){
        lblId.setText("ID: " + studentId.toUpperCase());
        lblName.setText("NAME: " + studentName.toUpperCase());
        lblDate.setText(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")) + " - " + (in? "IN" : "OUT"));
    }
}
