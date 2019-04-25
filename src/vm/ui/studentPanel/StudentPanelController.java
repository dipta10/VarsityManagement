/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vm.ui.studentPanel;

import com.jfoenix.controls.JFXTextField;
import database.DatabaseHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sampleClass.MyDetails;
import vm.ui.adminPanel.AdminPanelController;
import vm.ui.singleStudentFoundNew.SingleStudentFoundController;
import vm.ui.singleTeacherFound.SingleTeacherFoundController;

/**
 * FXML Controller class
 *
 * @author dipta10
 */
public class StudentPanelController implements Initializable {

    DatabaseHandler handler = DatabaseHandler.getInstance();
    ObservableList <Notificaiton> list = FXCollections.observableArrayList();
    
    private FileInputStream fis;
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
    private Label noImageAvailable;
    @FXML
    private Button chooseImage;
    @FXML
    private ImageView imageView;
    @FXML
    private JFXTextField searchByIdText;
    private JFXTextField searchByIdText2;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<Notificaiton> tableView;
    @FXML
    private TableColumn<Notificaiton, String> dateCol;
    @FXML
    private TableColumn<Notificaiton, String> teacherNameCol;
    @FXML
    private TableColumn<Notificaiton, String> msgTitleCol;
    @FXML
    private TableColumn<Notificaiton, String> msgDesCol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        setupPersonalInfoTab();
        
        initCol();
        loadData();
        
        try {
            String query = "select * from message";
            ResultSet rs = handler.execQuery(query);
            while (rs.next()) {
                System.out.println("date: " + rs.getTimestamp("date"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }   
    
    private void initCol() {
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        teacherNameCol.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        msgTitleCol.setCellValueFactory(new PropertyValueFactory<>("msgTitle"));
        msgDesCol.setCellValueFactory(new PropertyValueFactory<>("msgDes"));
    }
    
    private void loadData() {
        try {
            String query = "select * from message where year=" + myYear.getText() + " and semester=" + mySem.getText();
            System.out.println("query: " + query);
            ResultSet rs = handler.execQuery(query);
            while (rs.next()) {
                System.out.println("date: " + rs.getTimestamp("date"));
                list.add(new Notificaiton(rs.getTimestamp("date").toString(), rs.getString("teacher_name"),
                                          rs.getString("message_title"), rs.getString("message_description")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableView.setItems(list);
    }
    
    public static class Notificaiton {
        
        private final SimpleStringProperty date;
        private final SimpleStringProperty teacherName;
        private final SimpleStringProperty msgTitle;
        private final SimpleStringProperty msgDes;

        private Notificaiton (String fName, String lName, String email, String courseCredits) {
            this.date = new SimpleStringProperty(fName);
            this.teacherName = new SimpleStringProperty(lName);
            this.msgTitle = new SimpleStringProperty(email);
            this.msgDes = new SimpleStringProperty(courseCredits);
        }

        public String getDate() {
            return date.get();
        }

        public void setDate(String fName) {
            date.set(fName);
        }
        
        public String getTeacherName() {
            return teacherName.get();
        }

        public void setTeacherName(String fName) {
            teacherName.set(fName);
        }

        public String getMsgTitle() {
            return msgTitle.get();
        }

        public void setMsgTitle(String fName) {
            msgTitle.set(fName);
        }

        public String getMsgDes() {
            return msgDes.get();
        }
        
        public void setMsgDes(String credits) {
            msgDes.set(credits);
        }
    }

    void setUpImage() {
        if (MyDetails.Student.fis != null) {
            imageView.setImage(new Image(fis));
//            noImageAvailable.setVisible(false);
//            chooseImage.setVisible(false);
        } else {
            System.out.println("Image not found in fis");
            if (MyDetails.Student.imagePath != null) {
                System.out.println("Image found in blob");
                try {
                    imageView.setImage(new Image(MyDetails.Student.imagePath.getBinaryStream()));
//                    noImageAvailable.setVisible(false);
//                    chooseImage.setVisible(false);
                } catch (SQLException ex) {
                    Logger.getLogger(StudentPanelController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("Image not found in blob");
            }
        }
    }
    
    private void setupPersonalInfoTab() {
        myId.setText(MyDetails.myId);
        myName.setText(MyDetails.Student.name);
        System.out.println("My Name: " + MyDetails.Student.name);
        mySem.setText(MyDetails.Student.semester);
        myYear.setText(MyDetails.Student.year);
        mySection.setText(MyDetails.Student.section);
        myMobile.setText(MyDetails.Student.mobile);
        myEmail.setText(MyDetails.Student.email);
        myFatherName.setText(MyDetails.Student.fatherName);
        myFatherMobile.setText(MyDetails.Student.fatherMobile);
        myMotherMobile.setText(MyDetails.Student.motherMobile);
        myMotherName.setText(MyDetails.Student.motherName);
        myBloodGroup.setText(MyDetails.Student.bloodGroup);
        setUpImage();
    }

    @FXML
    private void chooseImage(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.jpg"));
        File selectedFile = fc.showOpenDialog(null);
        try {
            if (selectedFile != null) {
                fis = new FileInputStream(selectedFile);
                noImageAvailable.setVisible(false);
//                chooseImage.setVisible(false);
                imageView.setImage(new Image(fis));
                
                if (fis != null) {
            // this means that I can upload an image
            
            String query = "UPDATE student_info SET image=? WHERE id = '"  + MyDetails.myId + "'";
            try {
                System.out.println("fis: " + fis.toString());
                System.out.println("query: " + query);
                PreparedStatement ps = handler.con.prepareStatement(query);
                ps.setBinaryStream(1, fis);
                ps.execute();
                ps.close();
                System.out.println("I am uploading image");

            } catch (SQLException ex) {
                Logger.getLogger(StudentPanelController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

            }
        } catch (IOException ex) {
            Logger.getLogger(StudentPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    @FXML
    private void searchById(ActionEvent event) {
        String search = searchByIdText.getText();
        if (search.isEmpty()) {
            new MyDetails().showAlert("Enter the search by ID field", Alert.AlertType.ERROR);
            return;
        }
        if (search.equals("root")) {
            new MyDetails().showAlert("Enter a valid ID for students", Alert.AlertType.INFORMATION);
            return;
        }
        
        
        
        String qu = "SELECT * FROM student_info";
        ResultSet rs = handler.execQuery(qu);
        try {
            while(rs.next()) {
                String id2 = rs.getString("id");
                if (id2.equals(search)) {
                    new MyDetails().showAlert("Found!", Alert.AlertType.INFORMATION);
                    assignToSingleStudentFound(id2, rs.getString("name"), rs.getInt("semester"), rs.getInt("year"),
                                                rs.getString("section"), rs.getString("mobile"), rs.getString("email"),
                                                rs.getString("fathers_name"), rs.getString("mothers_name"), rs.getString("father_mobile"),
                                                rs.getString("mother_mobile"), rs.getString("blood_group"), rs.getBlob("image"));
                    
                    new MyDetails().loadWindowJFXDecoration("/vm/ui/singleStudentFoundNew/single_student_found.fxml", "Student found");
                    
                    return;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        new MyDetails().showAlert("Not found", Alert.AlertType.INFORMATION);
    }
    
    
    public void assignToSingleStudentFound (String id, String name, int sem, int year,
                                            String section, String mobile, String email,
                                            String fatherName, String motherName, String fatherMobile,
                                            String motherMobile, String bloodGroup, Blob image) {
        
        SingleStudentFoundController.myId2 = id;
        SingleStudentFoundController.myName2 = name;
        SingleStudentFoundController.mySem2 = sem;
        SingleStudentFoundController.myYear2 = year;
        SingleStudentFoundController.mySection2 = section;
        SingleStudentFoundController.myMobile2 = mobile;
        SingleStudentFoundController.myEmail2 = email;
        SingleStudentFoundController.myFatherName2 = fatherName;
        SingleStudentFoundController.myMotherName2 = motherName;
        SingleStudentFoundController.myFatherMobile2 = fatherMobile;
        SingleStudentFoundController.myMotherMobile2 = motherMobile;
        SingleStudentFoundController.myBloodGroup2 = bloodGroup;    
        SingleStudentFoundController.myImage2 = image;
}

    private void searchById2(ActionEvent event) {
        String search = searchByIdText2.getText();
        if (search.isEmpty()) {
            new MyDetails().showAlert("Enter the search by ID field", Alert.AlertType.ERROR);
            return;
        }
        if (search.equals("root")) {
            new MyDetails().showAlert("Enter a valid ID for teachers", Alert.AlertType.INFORMATION);
            return;
        }
        
        String qu = "SELECT * FROM teachers_info";
        ResultSet rs = handler.execQuery(qu);
        try {
            while(rs.next()) {
                String id2 = rs.getString("id");
                if (id2.equals(search)) {
                    new MyDetails().showAlert("Found!", Alert.AlertType.INFORMATION);
                    
                    SingleTeacherFoundController.id2 = id2;
                    SingleTeacherFoundController.name2 = rs.getString("name");
                    SingleTeacherFoundController.email2 = rs.getString("email");
                    SingleTeacherFoundController.mobile2 = rs.getString("mobile");
                    SingleTeacherFoundController.description2 = rs.getString("description");

                    new MyDetails().loadWindowJFXDecoration("/vm/ui/singleTeacherFound/single_teacher_found.fxml", "Teacher found");
                    
                    return;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        new MyDetails().showAlert("Not found", Alert.AlertType.INFORMATION);
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
    

}

