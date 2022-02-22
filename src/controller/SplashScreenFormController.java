package controller;

import db.DBConnection;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SplashScreenFormController {

    public ProgressBar pgb;
    public Label lblStatus;
    private File file;   //Backup file to restore
    SimpleObjectProperty<File> fileProperty = new SimpleObjectProperty<>();

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
                Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dep8_student_attendance", "root", "mysql");

                sleep(1000);
                Platform.runLater(() -> {
                    updateProgress("First step of Establishing..",0.3);
                });

                sleep(1000);
                Platform.runLater(() -> {
                    updateProgress("Second step of Establishing..",0.6);
                });

                sleep(1000);
                Platform.runLater(() -> {
                    updateProgress("Third step of Establishing..",0.9);
                });
                sleep(1000);

                Platform.runLater(() -> {
                    updateProgress("Final step of Establishing..",1.0);
                });
                sleep(1000);

                /*If we get the connection successfully load the login form*/
                Platform.runLater(()->{
                    loadLoginForm(connection);
                });

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

    private void loadLoginForm(Connection connection) {

        DBConnection.getInstance().init(connection);

        try {
            Stage stage = new Stage();
            AnchorPane root = FXMLLoader.load(this.getClass().getResource("/view/LoginScreenForm.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Student Attendance System: Create Admin");
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.sizeToScene();
            stage.show();

            /* Let's close the splash screen eventually */
            ((Stage)(lblStatus.getScene().getWindow())).close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadImportDBForm(){

        try {

            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/ImportDBForm.fxml"));
            AnchorPane root = fxmlLoader.load();
            ImportDBFormController controller = fxmlLoader.getController();
            controller.initFileProperty(fileProperty);

            //AnchorPane root = FXMLLoader.load(this.getClass().getResource("../view/ImportDBForm.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.setTitle("Student Attendance: Sign In");
            stage.setResizable(false);

            /*On the Splash screen, and it should be base on the splash screen*/
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(lblStatus.getScene().getWindow());
            /*-------------------------------------------------*/
            stage.centerOnScreen();
            stage.showAndWait();

            if(fileProperty.getValue() == null){
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
                        sleep(1000);


                        inputStream.read(buffer);
                        String script = new String(buffer);

                        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306?allowMultiQueries=true", "root", "mysql");

                        Platform.runLater(() -> {
                            updateProgress("Execute database Script..",0.5);
                        });

                        Statement stm = connection.createStatement();
                        stm.execute(script);
                        connection.close();
                        sleep(1000);

                        Platform.runLater(() -> {
                            updateProgress("Obtaining a new Database connection..",0.7);
                        });
                        connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dep8_student_attendance", "root", "mysql");
                        DBConnection.getInstance().init(connection);

                        Platform.runLater(() -> {
                            updateProgress("Setting up UI",0.8);
                            sleep(1000);
                            loadCreateAdminForm();
                        });
                    } catch (IOException | SQLException e) {
                        e.printStackTrace();
                    }
                }).start();
            }else{

                /*Store the database*/
                System.out.println("Restoring....");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCreateAdminForm() {
        Stage stage = new Stage();
        try {
            AnchorPane root = FXMLLoader.load(this.getClass().getResource("/view/CreateAdminForm.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Student Attendance System: Create Admin");
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.sizeToScene();
            stage.show();

            /* Let's close the splash screen eventually */
            ((Stage)(lblStatus.getScene().getWindow())).close();
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
