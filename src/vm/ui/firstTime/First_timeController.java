/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vm.ui.firstTime;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import database.DatabaseHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sampleClass.MyDetails;
import vm.ui.adminPanel.AdminPanelLoader;
import vm.ui.login.LoginController;

/**
 * FXML Controller class
 *
 * @author dipta10
 */
public class First_timeController implements Initializable {
    
    private FileInputStream fis;
    DatabaseHandler handler = DatabaseHandler.getInstance();

    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField mobile;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField fatherName;
    @FXML
    private JFXTextField motherName;
    @FXML
    private JFXTextField fatherMobile;
    @FXML
    private JFXTextField motherMobile;
    @FXML
    private JFXTextField bloodGroup;
    @FXML
    private JFXTextField section;
    @FXML
    private JFXTextField image;
    @FXML
    private JFXButton chooseImage;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXButton submit;
    @FXML
    private AnchorPane rootPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void chooseImage(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.jpg"));
        File selectedFile = fc.showOpenDialog(null);
        try {
            if (selectedFile != null) {
                fis = new FileInputStream(selectedFile);
                // @fishyImage
                MyDetails.Student.fis = new FileInputStream(selectedFile);
            }
        } catch (IOException ex) {
            Logger.getLogger(First_timeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (selectedFile != null) {
//            System.out.println("selected file: " + selectedFile.getAbsolutePath());
//            System.out.println("Binary stream: " + fis.toString());
            image.setText(selectedFile.getAbsolutePath());
            
        }
    }
    
    boolean isNumber (String input) {
        for (int i = 0; i < input.length(); ++i) {
            if (!Character.isDigit(input.charAt(i))) return false;
        }
        return true;
    }

    @FXML
    private void submit(ActionEvent event) {
        if (name.getText().isEmpty()
                || password.getText().isEmpty()
                || mobile.getText().isEmpty()
                || email.getText().isEmpty()
                || fatherName.getText().isEmpty()
                || motherName.getText().isEmpty()
                || fatherMobile.getText().isEmpty()
                || motherMobile.getText().isEmpty()
                || bloodGroup.getText().isEmpty()
                || section.getText().isEmpty()
                || image.getText().isEmpty()
                ) {
            
            new MyDetails().showAlert("Please fill in all the fields and choose an image", Alert.AlertType.ERROR);
            return;
        }
        
        String query = "UPDATE student_info " +
                "SET name = '" + name.getText() + "', " +
                " pass = '" + password.getText() + "', " +
                " mobile = '" + mobile.getText() + "', " +
                " email = '" + email.getText() + "', " +
                " fathers_name = '" + fatherName.getText() + "', " +
                " mothers_name = '" + motherName.getText() + "', " +
                " father_mobile = '" + fatherMobile.getText() + "', " +
                " mother_mobile = '" + motherMobile.getText() + "', " +
                " section = '" + section.getText() + "', " +
                " blood_group = '" + bloodGroup.getText() + "', " +
                " first_time = false " +
                " WHERE id = '" + MyDetails.myId + "'";
        System.out.println("Query: " + query);
        
        
        
        
        
        handler.execAction(query);
        
        if (fis != null) {
            // this means that I can upload an image
            
            query = "UPDATE student_info SET image=? WHERE id = '"  + MyDetails.myId + "'";
            try {
                System.out.println("query: " + query);
                PreparedStatement ps = handler.con.prepareStatement(query);
                ps.setBinaryStream(1, fis);
                ps.execute();
            } catch (SQLException ex) {
                Logger.getLogger(First_timeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("I am uploading image");
        }
        
        
        
        MyDetails.Student.name = name.getText();
        MyDetails.Student.mobile = mobile.getText();
        MyDetails.Student.email = email.getText();
        MyDetails.Student.fatherName = fatherName.getText();
        MyDetails.Student.motherName = motherName.getText();
        MyDetails.Student.fatherMobile = fatherMobile.getText();
        MyDetails.Student.motherMobile = motherMobile.getText();
        MyDetails.Student.section = section.getText();
        MyDetails.Student.bloodGroup = bloodGroup.getText();
        MyDetails.Student.semester = "1";
        MyDetails.Student.year = "1";
        
        new MyDetails().showAlert("Account Activated! Log in again using your new password", Alert.AlertType.INFORMATION);
        
        System.exit(0);
        cancel();
                        
        
    }
    private void loadWindowJFXDecoration(String location, String title) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource(location));
            Stage stage = new Stage();
            JFXDecorator decorator=new JFXDecorator(stage, root, false, false, true);
            decorator.setCustomMaximize(false);
            decorator.setBorder(Border.EMPTY);

            Scene scene = new Scene(decorator);
            scene.getStylesheets().add(AdminPanelLoader.class.getResource("/styles/styles.css").toExternalForm());
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);

            stage.setIconified(false);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    void cancel() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
}
