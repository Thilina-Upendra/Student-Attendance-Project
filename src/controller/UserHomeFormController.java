package controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class UserHomeFormController {

    public Button btnRecordAttendance;
    public Button btnViewReports;
    public Button btnUserProfile;
    public Button btnSignOut;
    public Label lblUserName;

    public void initUserName(String name){
        lblUserName.setText("Welcome User : "+name+" !");
    }
}
