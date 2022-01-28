package controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class AdminUserFormController {
    public Button btnRecordAttendance;
    public Button btnViewReport;
    public Button btnUserProfile;
    public Button btnManageUser;
    public Button btnBackRestore;
    public Button btnSignOut;


    public Label lblCurrentUser;


    public void initUserName(String name){
        lblCurrentUser.setText("Welcome Admin : "+name+" !");
    }
}
