/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vm.ui.adminPanel;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXTextField;
import database.DatabaseHandler;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sampleClass.MyDetails;
import vm.ui.singleStudentFoundNew.SingleStudentFoundController;

/**
 * FXML Controller class
 *
 * @author dipta10
 */
public class AdminPanelController implements Initializable {

    @FXML
    private JFXTextField studentId;
   
    DatabaseHandler handler = DatabaseHandler.getInstance();
    ObservableList <Person> list = FXCollections.observableArrayList();
    
    @FXML
    private JFXTextField teacherId;
    @FXML
    private JFXTextField teacherName;
    @FXML
    private JFXTextField teacherEmail;
    @FXML
    private JFXTextField teacherPhone;
    @FXML
    private JFXButton addStudent;
    @FXML
    private JFXTextField courseId;
    @FXML
    private JFXTextField courseTitle;
    @FXML
    private JFXTextField courseCredits;
    @FXML
    private JFXTextField courseYear;
    @FXML
    private JFXTextField courseSemester;
    @FXML
    private JFXTextField courseIdRemove;
    @FXML
    private JFXCheckBox courseType;
    @FXML
    private TableView<Person> tableView;
    @FXML
    private TableColumn<Person, String> courseIdCol;
    @FXML
    private TableColumn<Person, String>courseTitleCol;
    @FXML
    private TableColumn<Person, String> courseTypeCol;
    @FXML
    private TableColumn<Person, String> courseCreditsCol;
    @FXML
    private JFXTextField searchByIdText;
    @FXML
    private AnchorPane rootPane;

    /**
     * Initializes the controller class.
     */
    
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setupCourseTable();
    }    
    
    private void setupCourseTable() {
        loadData();
        initCol();
        tableView.getItems().setAll(list);
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
            Logger.getLogger(AdminPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void showTeacherTable(ActionEvent event) {
        loadWindowJFXDecoration("/vm/ui/teacherInfoTable/teacher_info_table.fxml", "Teacher's Information Table");
    }
    
    public static class Person {

        private final SimpleStringProperty courseId;
        private final SimpleStringProperty courseTitle;
        private final SimpleStringProperty courseType;
        private final SimpleDoubleProperty courseCredits;

        private Person(String fName, String lName, String email, double courseCredits) {
            this.courseId = new SimpleStringProperty(fName);
            this.courseTitle = new SimpleStringProperty(lName);
            this.courseType = new SimpleStringProperty(email);
            this.courseCredits = new SimpleDoubleProperty(courseCredits);
        }

        public String getCourseId() {
            return courseId.get();
        }

        public void setCourseId(String fName) {
            courseId.set(fName);
        }
        
        public String getCourseTitle() {
            return courseTitle.get();
        }

        public void setCourseTitle(String fName) {
            courseTitle.set(fName);
        }

        public String getCourseType() {
            return courseType.get();
        }

        public void setCourseType(String fName) {
            courseType.set(fName);
        }

        public double getCourseCredits() {
            return courseCredits.get();
        }
        
        public void setCourseCredits(double credits) {
            courseCredits.set(credits);
        }

    }

    private void loadData() {
        DatabaseHandler handler;
        handler = DatabaseHandler.getInstance();
        String qu = "SELECT * FROM course_info";
        ResultSet rs = handler.execQuery(qu);
        try {
//            private final SimpleStringProperty courseId;
//        private final SimpleStringProperty courseTitle;
//        private final SimpleStringProperty courseType;
//        private final SimpleDoubleProperty courseCredits;
            String courseId, courseTitle, courseType; double courseCredits;
            while (rs.next()) {
                courseId = rs.getString("course_id");
                courseTitle = rs.getString("course_title");
                courseType = rs.getString("courseType");
                courseCredits = rs.getDouble("credit");
                list.add(new Person(courseId, courseTitle, courseType, courseCredits));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tableView.getItems().setAll(list);
    }
    
    private void initCol() {
        courseIdCol.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        courseTitleCol.setCellValueFactory(new PropertyValueFactory<>("courseTitle"));
        courseTypeCol.setCellValueFactory(new PropertyValueFactory<>("courseType"));
        courseCreditsCol.setCellValueFactory(new PropertyValueFactory<>("courseCredits"));
    }

    @FXML
    private void searchById(ActionEvent event) {
        // for students
        String search = searchByIdText.getText();
        if (search.isEmpty()) {
            showAlert("Enter the search by ID field", Alert.AlertType.ERROR);
            return;
        }
        if (search.equals("root")) {
            showAlert("Enter a valid ID for students", Alert.AlertType.INFORMATION);
            return;
        }
        
        String qu = "SELECT * FROM student_info";
        ResultSet rs = handler.execQuery(qu);
        try {
            while(rs.next()) {
                String id2 = rs.getString("id");
                if (id2.equals(search)) {
                    showAlert("Found!", Alert.AlertType.INFORMATION);
//                    String id, String name, int sem, int year,
//                                            String section, String mobile, String email,
//                                            String fatherName, String motherName, String fatherMobile,
//                                            String motherMobile, String bloodGroup, Blob image
                    assignToSingleStudentFound(id2, rs.getString("name"), rs.getInt("semester"), rs.getInt("year"),
                                                rs.getString("section"), rs.getString("mobile"), rs.getString("email"),
                                                rs.getString("fathers_name"), rs.getString("mothers_name"), rs.getString("father_mobile"),
                                                rs.getString("mother_mobile"), rs.getString("blood_group"), rs.getBlob("image"));
                    
                    loadWindowJFXDecoration("/vm/ui/singleStudentFoundNew/single_student_found.fxml", "Student found");
                    
                    return;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        showAlert("Not found", Alert.AlertType.INFORMATION);
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
    private void addStudent(ActionEvent event) {
        if (studentId.getText().isEmpty()) {
            showAlert("Enter student ID first", Alert.AlertType.ERROR);
            return;
        }
        String query = "INSERT INTO student_info values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement statement = handler.con.prepareStatement(query);
            statement.setString(1, studentId.getText());
            statement.setString(2, "123456");
            statement.setString(3, null);
            statement.setInt(4, 1);
            statement.setInt(5, 1);
            statement.setString(6, null);
            statement.setBinaryStream(7, null);
            statement.setString(8 , null);
            statement.setString(9 , null);
            statement.setString(10 , null);
            statement.setString(11 , null);
            statement.setString(12 , null);
            statement.setString(13 , null);
            statement.setString(14 , null);
            statement.setBoolean(15, true);
            statement.setBoolean(16, true);
            statement.setInt(17, 1);
            statement.execute();
            
            showAlert("Student added!", Alert.AlertType.INFORMATION);
        } catch (SQLException ex) {
            Logger.getLogger(AdminPanelController.class.getName()).log(Level.SEVERE, null, ex);
            showAlert("Failed to add Student", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void addTeacher(ActionEvent event) {
        if (teacherId.getText().isEmpty() || teacherName.getText().isEmpty() || teacherEmail.getText().isEmpty() || teacherPhone.getText().isEmpty()) {
            showAlert("please first fill in all the fields to add teacher", Alert.AlertType.ERROR);
            return;
        }
        if (!isNumber(teacherPhone.getText())) {
            showAlert("Teacher's phone should be a number", Alert.AlertType.ERROR);
            return;
        }
        
        String query = "INSERT INTO teachers_info values(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement statement = handler.con.prepareStatement(query);
            
            statement.setString(1, teacherId.getText());
            statement.setString(2, "123456");
            statement.setString(3, teacherName.getText());
            statement.setString(4, teacherPhone.getText());
            statement.setString(5, teacherEmail.getText());
            statement.setString(6, null);
            statement.setBoolean(7, true);
            statement.setBinaryStream(8, null);
            
            statement.execute();
            
            showAlert("Teacher added!", Alert.AlertType.INFORMATION);
        } catch (SQLException ex) {
            Logger.getLogger(AdminPanelController.class.getName()).log(Level.SEVERE, null, ex);
            showAlert("Failed to add Teacher", Alert.AlertType.ERROR);
        }
        
    }
    
    @FXML
    private void addCourse(ActionEvent event) {
        if (courseId.getText().isEmpty() || courseTitle.getText().isEmpty() || courseYear.getText().isEmpty() || courseSemester.getText().isEmpty() || courseCredits.getText().isEmpty()) {
            showAlert("please first fill in all the fields to course", Alert.AlertType.ERROR);
            return;
        }
        
        if (!isNumber(courseYear.getText()) || !isNumber(courseSemester.getText())) {
            showAlert("Course year and semester should be a number", Alert.AlertType.ERROR);
            return;
        }
        
        if (!isDecimalNumber(courseCredits.getText())) {
            showAlert("Credits of the course should be a decimal number", Alert.AlertType.ERROR);
            return;
        }
        
        String query = "INSERT INTO course_info values(?,?,?,?,?,?)";
        try {
            PreparedStatement statement = handler.con.prepareStatement(query);
            
            statement.setString(1, courseId.getText());
            statement.setString(2, courseTitle.getText());
            statement.setDouble(3, Double.parseDouble(courseCredits.getText()));
            statement.setInt(4, Integer.parseInt(courseYear.getText()));
            statement.setInt(5, Integer.parseInt(courseSemester.getText()));
            if (courseType.isSelected()) {
                statement.setString(6, "lab");
            } else {
                statement.setString(6, "theory");                
            }
            
            statement.execute();
            
            showAlert("Course added!", Alert.AlertType.INFORMATION);
        } catch (SQLException ex) {
            Logger.getLogger(AdminPanelController.class.getName()).log(Level.SEVERE, null, ex);
            showAlert("Failed to add Teacher", Alert.AlertType.ERROR);
        }
        
    }
    
    boolean isDecimalNumber(String input) {
        try {
            double l = Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    
    boolean isNumber(String input) {
        try {
            long l = Long.parseLong(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    void showAlert (String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    
    
    
}
