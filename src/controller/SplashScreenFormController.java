package controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SplashScreenFormController {

    public ProgressBar pgb;
    public Label lblStatus;
    private File file;   //Backup file to restore

    private SimpleStringProperty status = new SimpleStringProperty("Initializing...");
    private SimpleDoubleProperty progress = new SimpleDoubleProperty(0.0);

    public void initialize() {
        lblStatus.textProperty().bind(status);
        pgb.progressProperty().bind(progress);

        establishConnection();

    }


    private void establishConnection() {

        updateProgress("Establishing DB Connection....",0.1);
        sleep(1000);

        new Thread(() -> {

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dep008_hello", "root", "mysql");

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                if (e.getSQLState().equals("42000")){
                    Platform.runLater(this::loadImportDBForm);
                }
                /*e.printStackTrace();*/


            }

        }).start();
    }

    private void loadImportDBForm(){

        try {

            SimpleObjectProperty<File> fileProperty = new SimpleObjectProperty<>(file);


            Stage stage = new Stage();
            AnchorPane root = FXMLLoader.load(this.getClass().getResource("../view/ImportDBForm.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.setTitle("Student Attendance: Sign In");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(lblStatus.getScene().getWindow());
            stage.centerOnScreen();
            stage.showAndWait();
            file = fileProperty.getValue();

            if(file == null){
                /*Create the database*/
                updateProgress("Creating a new DB..",0.2);

                new Thread(() -> {
                    try {

                        sleep(1000);
                        /*Database create by read*/
                        Platform.runLater(() -> {
                            updateProgress("Loading Database Script..",0.3);
                        });

                        /*Read the sql script file*/
                        InputStream inputStream = this.getClass().getResourceAsStream("/assets/dbScript.sql");
                        byte[] buffer = new byte[inputStream.available()];
                        inputStream.read(buffer);
                        String script = new String(buffer);



                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }).start();
            }else{
                /*Store the database*/
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateProgress(String status, double value) {
        Platform.runLater(() -> {
            this.status.set(status);
            this.progress.set(value);
        });
    }

    private void sleep(long value){
        try {
            Thread.sleep(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
