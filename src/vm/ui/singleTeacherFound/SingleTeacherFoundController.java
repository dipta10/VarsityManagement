package vm.ui.singleTeacherFound;

import java.net.URL;
import java.sql.Blob;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author dipta10
 */
public class SingleTeacherFoundController implements Initializable {
    
    public static String id2;
    public static String name2;
    public static String mobile2;
    public static String email2;
    public static String description2;
    public static Blob image;
    

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setData();
    }    
    
    public void setData() {
        myId.setText(id2);
        myName.setText(name2);
        myMobile.setText(mobile2);
        myEmail.setText(email2);
        myDesc.setText(description2);
    }
    
}
