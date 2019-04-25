/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vm.ui.singleStudentFoundNew;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author dipta10
 */
public class SingleStudentFoundController implements Initializable {
    
    public static String myId2;
    public static String myName2;
    public static int mySem2;
    public static int myYear2;
    public static String mySection2;
    public static String myMobile2;
    public static String myEmail2;
    public static String myFatherName2;
    public static String myMotherName2;
    public static String myFatherMobile2;
    public static String myMotherMobile2;
    public static String myBloodGroup2;
    public static Blob myImage2;
    @FXML
    private AnchorPane rootPane;
    
    private void setData() {
        myId.setText(myId2);
        myName.setText(myName2);
        mySem.setText(Integer.toString(mySem2));
        myYear.setText(Integer.toString(myYear2));
        mySection.setText(mySection2);
        myMobile.setText(myMobile2);
        myEmail.setText(myEmail2);
        myFatherMobile.setText(myFatherMobile2);
        myFatherName.setText(myFatherName2);
        myMotherName.setText(myMotherName2);
        myMotherMobile.setText(myMotherMobile2);
        myBloodGroup.setText(myBloodGroup2);
        if (myImage2 != null) {
            noImageAvailable.setVisible(false);
            try {
                imageView.setImage(new Image(myImage2.getBinaryStream()));
            } catch (SQLException ex) {
                Logger.getLogger(SingleStudentFoundController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    

    @FXML
    private Label myId;
    @FXML
    private Label myName;
    @FXML
    private Label mySem;
    @FXML
    private Label myYear;
    @FXML
    private Label mySection;
    @FXML
    private Label myMobile;
    @FXML
    private Label myEmail;
    @FXML
    private Label myFatherName;
    @FXML
    private Label myMotherName;
    @FXML
    private Label myFatherMobile;
    @FXML
    private Label myMotherMobile;
    @FXML
    private Label myBloodGroup;
    @FXML
    private ImageView imageView;
    @FXML
    private Label noImageAvailable;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setData();
    }    
    
}
