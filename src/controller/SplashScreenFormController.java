package controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SplashScreenFormController {

    public ProgressBar pgb;
    public Label lblStatus;

    private SimpleStringProperty status = new SimpleStringProperty("Initializing...");
    private SimpleDoubleProperty progress = new SimpleDoubleProperty(0.0);

    public void initialize() {
        lblStatus.textProperty().bind(status);
        pgb.progressProperty().bind(progress);


    }


    private Connection establishConnection() throws Throwable {

        try {
            /*Load the connector*/
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/student_attendance", "root", "mysql");
            updateProgress("Found an existing DB..", 0.5);
            Thread.sleep(1000);

            updateProgress("Setting up the connection..", 0.8);
            Thread.sleep(1000);

            return connection;
        } catch (SQLException e) {
            if(e.getSQLState().equals("42000")){
                createDB();
                return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3360/student_attendance","root","mysql");
            }else{
                updateProgress("Network failure",0.8);
                Thread.sleep(1000);
                throw new RuntimeException("The network has been failed...");
            }
        }
    }

    private void createDB(){

    }

    private void updateProgress(String status, double value) {
        Platform.runLater(() -> {
            this.status.set(status);
            this.progress.set(value);
        });
    }
}
