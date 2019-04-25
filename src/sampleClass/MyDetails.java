
package sampleClass;

import com.jfoenix.controls.JFXDecorator;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Border;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import vm.ui.adminPanel.AdminPanelLoader;

public class MyDetails {
    public static String myId;
    public static String myPassword;
    
    public void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
    }
    
    public static class Student {
        public static String name;
        public static String mobile;
        public static String email;
        public static String fatherName;
        public static String motherName;
        public static String fatherMobile;
        public static String motherMobile;
        public static String section;
        public static String bloodGroup;
        public static String semester;
        public static String year;
        public static FileInputStream fis;
        public static Blob imagePath;
    }
       
    
    public static class Teacher {
        public static String name;
        public static String id;
        public static String mobile;
        public static String email;
        public static String description;
        public static FileInputStream fis;
        public static Blob imagePath;
    }
    
    
    public void loadWindowJFXDecoration(String location, String title) {
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
            Logger.getLogger(MyDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }


    
}
