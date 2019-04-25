/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vm.ui.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import database.DatabaseHandler;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import sampleClass.MyDetails;
import vm.ui.adminPanel.AdminPanelLoader;

/**
 * FXML Controller class
 *
 * @author dipta10
 */
public class LoginController implements Initializable {
    
    DatabaseHandler handler = DatabaseHandler.getInstance();

    @FXML
    private JFXTextField txtUsername;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXButton btnLogin;
    @FXML
    private JFXSpinner loggingProgress;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Hyperlink alertMessage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loggingProgress.setVisible(false);
    }

    @FXML
    private void loginAction(ActionEvent event) {
        System.out.println("hello world");
        loggingProgress.setVisible(true);

        PauseTransition pauseTransition = new PauseTransition();
        pauseTransition.setDuration(Duration.seconds(3));
        pauseTransition.setOnFinished(ev -> {
            System.out.println("Complte one");

            System.out.println("Complte two");
            loggingProgress.setVisible(false);
            completeLogin();
        });
        pauseTransition.play();

    }

    

    void loadWindow(String location, String title, StageStyle style) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(location));
            Stage stage = new Stage(style);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void completeLogin() {
        String userName = txtUsername.getText();
        String password = txtPassword.getText();
        
        if (userName.isEmpty() || password.isEmpty()) {
            alertMessage.setText("Please fill in all the fileds!!!");
        } else {
            alertMessage.setText("");
        }
        
        boolean passMismatch = false;
        boolean userNameMismatch = false;
        
        // student id search
        String query = "";
        query = "SELECT * FROM `student_info` where id='"+ txtUsername.getText() + "'";
        ResultSet rs = handler.execQuery(query);
        boolean passIncorrect = false;
        boolean userNameIncorrect = false;
        try {
            System.out.println("HERE 1");
            if (rs.next()) {
                System.out.println("HERE 2");
                MyDetails.myId = userName; MyDetails.myPassword = password;
                // @workleft
                // root is in the first row therefore root is being verified always
                if (txtUsername.getText().equals("root")) {
                    String pass = rs.getString("pass");
                    if (pass.equals(txtPassword.getText())) {
                        loadWindowJFXDecoration("/vm/ui/adminPanel/admin_panel.fxml", "Details");
                        cancel();
                    } else {
                        alertMessage.setText("Password given is incorrect!");
                    }
                    return;
                }
                
                String realPass = rs.getString("pass");
                if (realPass.equals(txtPassword.getText())) {
                    // first time
                    boolean firstTime = rs.getBoolean("first_time");
                    if (firstTime) {
                        loadWindowJFXDecoration("/vm/ui/firstTime/first_time.fxml", "Details");
                        cancel();
                    } else {

                        MyDetails.Student.name = rs.getString("name");
                        System.out.println("My Name: " + rs.getString("name"));
                        MyDetails.Student.mobile = rs.getString("mobile");
                        MyDetails.Student.email = rs.getString("email");
                        MyDetails.Student.fatherName = rs.getString("fathers_name");
                        MyDetails.Student.motherName = rs.getString("mothers_name");
                        MyDetails.Student.fatherMobile = rs.getString("father_mobile");
                        MyDetails.Student.motherMobile = rs.getString("mother_mobile");
                        MyDetails.Student.section = rs.getString("section");
                        MyDetails.Student.bloodGroup = rs.getString("blood_group");
                        MyDetails.Student.semester = Integer.toString(rs.getInt("semester"));
                        MyDetails.Student.year = Integer.toString(rs.getInt("year"));
                        MyDetails.Student.imagePath = rs.getBlob("image");

                        loadWindowJFXDecoration("/vm/ui/studentPanel/student_panel.fxml", "Student Panel");
                        cancel();
                    }
                    return;
                } else {
//                    alertMessage.setText("Password given is incorrect!");                    
                    passMismatch = true;
                   
                }
            } else {
//                alertMessage.setText("User id: " + txtUsername.getText() + " not found");
                userNameMismatch = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (passMismatch) {
            alertMessage.setText("Password given is incorrect!");
            
            return;
        }
        
        query = "SELECT * FROM `teachers_info` where id='"+ txtUsername.getText() + "'";
        rs = handler.execQuery(query);
        try {
            if (rs.next()) {
                MyDetails.myId = userName; MyDetails.myPassword = password;
                String realPass = rs.getString("password");
                if (realPass.equals(txtPassword.getText())) {
                    // first time
                    
                    MyDetails.Teacher.name = rs.getString("name");
                    MyDetails.Teacher.description = rs.getString("description");
                    MyDetails.Teacher.mobile = rs.getString("mobile");
                    MyDetails.Teacher.email = rs.getString("email");
                    MyDetails.Teacher.imagePath = rs.getBlob("image");
                    
                        loadWindowJFXDecoration("/vm/ui/teacherPanel/teacher_panel.fxml", "Teacher's Panel");
                        cancel();
                        return;
                } else {
                    alertMessage.setText("Password given is incorrect!");  
                    return;
                }
            } else {
                alertMessage.setText("User id: " + txtUsername.getText() + " not found");
                return;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
//        if (userName.equals("root") && password.equals("admin")) {
//            loadWindowJFXDecoration("/vm/ui/adminPanel/admin_panel.fxml", "Admin Panel");
//            cancel();
//        }
        // database search

        // error message
    }
    

    void cancel() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
    
    void loadWindow(String location, String title) {
        try {
            Parent parent = (Parent) FXMLLoader.load(getClass().getResource(location));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            scene.getStylesheets().add(LoginController.class.getResource("/styles/styles.css").toExternalForm());
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);

            stage.setIconified(false);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
