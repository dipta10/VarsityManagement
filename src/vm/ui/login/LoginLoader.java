 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vm.ui.login;


import com.jfoenix.controls.JFXDecorator;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginLoader extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
//
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
        
        
        Parent root = FXMLLoader.load(getClass().getResource("/vm/ui/login/login.fxml"));
        JFXDecorator decorator=new JFXDecorator(stage, root, false, false, true);
        decorator.setCustomMaximize(false);
        decorator.setBorder(Border.EMPTY);
        
        Scene scene = new Scene(decorator);
        scene.getStylesheets().add(LoginLoader.class.getResource("/styles/styles.css").toExternalForm());
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        
        stage.setIconified(false);
        stage.show();
    }
    
    
    
    
}
