/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vm.ui.teacherPanel;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import database.DatabaseHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sampleClass.MyDetails;

/**
 * FXML Controller class
 *
 * @author dipta10
 */
public class TeacherPanelController implements Initializable {

    DatabaseHandler handler = DatabaseHandler.getInstance();
    private FileInputStream fis;
    @FXML
    private Label myId;
    @FXML
    private Label myName;
    @FXML
    private Label myMobile;
    @FXML
    private Label myEmail;
    @FXML
    private Label myDesc;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private JFXTextField searchByIdText;
    @FXML
    private ImageView imageView;
    @FXML
    private ChoiceBox<String> selectSemester;
    ObservableList<String> list = FXCollections.observableArrayList("1.1", "1.2", "2.1", "2.2", "3.1", "3.2", "4.1", "4.2");
    @FXML
    private JFXTextField msgTitle;
    @FXML
    private JFXTextArea msgDes;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setupPersonalInfoTab();
        setUpImage();
        selectSemester.setItems(list);
    }    
    
    void setUpImage() {
        InputStream is;
        if (MyDetails.Teacher.imagePath != null) {
            
        }
        try {
            is = (MyDetails.Teacher.imagePath.getBinaryStream());
            imageView.setImage(new Image(is));
        } catch (Exception ex) {
        }
        
            
//            noImageAvailable.setVisible(false);
//            chooseImage.setVisible(false);
            
        
    }
    
    private void setupPersonalInfoTab() {
        myId.setText(MyDetails.myId);
        myName.setText(MyDetails.Teacher.name);
        myMobile.setText(MyDetails.Teacher.mobile);
        myEmail.setText(MyDetails.Teacher.email);
        myDesc.setText(MyDetails.Teacher.description);
    }
    
    @FXML
    private void logout(Event event) {
        new MyDetails().loadWindowJFXDecoration("/vm/ui/login/login.fxml", "Welcome");
        cancel();
    }
    
    void cancel() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void searchById(ActionEvent event) {
    }
    
    void showAlert (String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void sendMessage(ActionEvent event) {
        if (msgDes.getText().isEmpty() || msgTitle.getText().isEmpty()) {
            showAlert("Please enter all the fields correctly", Alert.AlertType.INFORMATION);
            return;
        }
        
        if (selectSemester.getSelectionModel().getSelectedItem() == null) {
            showAlert("Please select semester", Alert.AlertType.INFORMATION);
            return;
        }
        
        System.out.println("id: " + MyDetails.myId);
        System.out.println("name: " + MyDetails.Teacher.name);
        System.out.println("year: " + selectSemester.getSelectionModel().getSelectedItem());
        System.out.println("sem: " + selectSemester.getSelectionModel().getSelectedItem().substring(0, 1));
        System.out.println("year: " + selectSemester.getSelectionModel().getSelectedItem().substring(2));
//        System.out.println("year: " + MyDetails.Teacher.name);

        DatabaseHandler handler = DatabaseHandler.getInstance();
        
        
        String query = "INSERT INTO message VALUES( " +
                "'" + MyDetails.myId + "'," +
                "'" + MyDetails.Teacher.name + "'," +
                "'" + selectSemester.getSelectionModel().getSelectedItem().substring(0, 1) + "'," +
                "'" + selectSemester.getSelectionModel().getSelectedItem().substring(2) + "'," +
                "" + "CURRENT_TIMESTAMP" + " ," +
                "'" + msgTitle.getText() + "',"+
                "'" + msgDes.getText() + "'" +
                ");";
        
        handler.execAction(query);
        
        showAlert("Message Sent", Alert.AlertType.CONFIRMATION);
        
    }
    
}
