package lk.ijse.dep8.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ImportDBFormController {

    public RadioButton rdoRestore;
    public ToggleGroup grouped;
    public TextField txtBrowse;
    public Button btnBrowse;
    public RadioButton rdoFirstTime;
    public Button btnOkay;
    private SimpleObjectProperty<File> fileProperty;


    public void initialize(){
        txtBrowse.setEditable(false);
        rdoRestore.selectedProperty().addListener((observable, oldValue, newValue) -> {
            btnOkay.setDisable(txtBrowse.getText().isEmpty() && newValue);
        });

    }

    public void initFileProperty(SimpleObjectProperty<File> propertyFile){
        this.fileProperty = propertyFile;
    }

    public void btnBrowseONAction(ActionEvent actionEvent) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select backup file");
        FileChooser.ExtensionFilter backup_file = new FileChooser.ExtensionFilter("Backup file", ".dep8backup");
        fileChooser.getExtensionFilters().add(backup_file);
        File file = fileChooser.showOpenDialog(btnOkay.getScene().getWindow());
        txtBrowse.setText(file != null ? file.getAbsolutePath() : "");
        fileProperty.setValue(file);
        System.out.println(fileProperty.getValue());
    }

    public void btnOkayOnAction(ActionEvent actionEvent) {
        if(rdoFirstTime.isSelected()){
            fileProperty.setValue(null);
        }
        ((Stage)(btnOkay.getScene().getWindow())).close();
    }
}
