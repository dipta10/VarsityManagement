/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vm.ui.teacherInfoTable;

import database.DatabaseHandler;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import vm.ui.adminPanel.AdminPanelController;

/**
 * FXML Controller class
 *
 * @author dipta10
 */
public class TeacherInfoTableController implements Initializable {
    
    ObservableList <Person> list = FXCollections.observableArrayList();

    @FXML
    private TableView<Person> tableView;
    @FXML
    private TableColumn<Person, String> idCol;
    @FXML
    private TableColumn<Person, String> nameCol;
    @FXML
    private TableColumn<Person, String> mobileCol;
    @FXML
    private TableColumn<Person, String> emailCol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initCol();
        loadData();
        list.add(new Person("dipta10", "dipta das", "324", "dip@yahoo.com"));
        

    }    
    
    private void initCol() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mobileCol.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
    }
    
    private void loadData() {
        DatabaseHandler handler;
        handler = DatabaseHandler.getInstance();
        String qu = "SELECT * FROM teachers_info";
        ResultSet rs = handler.execQuery(qu);
        try {
            String courseId, courseTitle, courseType, courseCredits;
            while (rs.next()) {
                courseId = rs.getString("id");
                courseTitle = rs.getString("name");
                courseType = rs.getString("mobile");
                courseCredits = rs.getString("email");
                list.add(new Person(courseId, courseTitle, courseType, courseCredits));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        tableView.getItems().setAll(list);

    }
    
    public static class Person {

        private final SimpleStringProperty id;
        private final SimpleStringProperty name;
        private final SimpleStringProperty mobile;
        private final SimpleStringProperty email;

        private Person(String fName, String lName, String email, String courseCredits) {
            this.id = new SimpleStringProperty(fName);
            this.name = new SimpleStringProperty(lName);
            this.mobile = new SimpleStringProperty(email);
            this.email = new SimpleStringProperty(courseCredits);
        }

        public String getId() {
            return id.get();
        }

        public void setId (String fName) {
            id.set(fName);
        }
        
        public String getName() {
            return name.get();
        }

        public void setName(String fName) {
            name.set(fName);
        }

        public String getMobile() {
            return mobile.get();
        }

        public void setMobile(String fName) {
            mobile.set(fName);
        }

        public String getEmail() {
            return email.get();
        }
        
        public void setEmail (String credits) {
            email.set(credits);
        }

    }
    
}
