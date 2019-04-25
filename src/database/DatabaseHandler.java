package database;

import java.sql.*;

import com.mysql.jdbc.Connection;

import javax.swing.*;

public class DatabaseHandler {

    private static DatabaseHandler handler;
    private static final String db_url = "jdbc:mysql://127.0.0.1/VarsityManagement2";
    public static Connection con = null;
    private static Statement statement = null;
    private static final String username = "root";
    private static final String password = "";

    private DatabaseHandler() {
        createConnection();
        setupStudentInfoTable();
        setupCourseInfoTable();
        setupTeachersInfoTable();
        setupStudentCgpaTable();
        setupMessageTable();
    }

    public static DatabaseHandler getInstance() {
        if (handler == null) {
            handler = new DatabaseHandler();
        }
        return handler;
    }

    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            statement = con.createStatement();
            result = statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Exception AT : execQuery(String query) " + e.getLocalizedMessage());
            return null;
        } finally {

        }

        return result;
    }

    public boolean execAction(String query) {
        try {
            statement = con.createStatement();
            statement.execute(query);
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception AT : execACTION(String query) " + e.getLocalizedMessage());
            return false;
        } finally {
        }
    }
    
    

    public void setupMemberTable() {
        try {
            String TABLE_NAME = "MEMBER";
            statement = con.createStatement();
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exists! Ready to go");

            } else {
                statement.execute("CREATE TABLE " + TABLE_NAME + " ("
                        + " id varchar(200) PRIMARY KEY,"
                        + " password varchar(20), "
                        + " name varchar(200), "
                        + " mobile varchar(20), "
                        + " email varchar(200) "
                        + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void setupStudentInfoTable() {
        try {
            String TABLE_NAME = "student_info";
            statement = con.createStatement();
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exists! Ready to go");

            } else {
                statement.execute("CREATE TABLE student_info ( id VARCHAR(30) NOT NULL PRIMARY KEY , pass VARCHAR(50) DEFAULT 123456 , name VARCHAR(50) , semester INT , year INT , section VARCHAR(2) , image BLOB , fathers_name VARCHAR(50) , mothers_name VARCHAR(50) , father_mobile VARCHAR(12)  , mother_mobile VARCHAR(12)  , mobile VARCHAR(12)  , email VARCHAR(50), blood_group VARCHAR(5)  , first_time BOOLEAN DEFAULT true , active  BOOLEAN DEFAULT false, semester_number INT );");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void setupCourseInfoTable() {
        try {
            String TABLE_NAME = "course_info";
            statement = con.createStatement();
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exists! Ready to go");

            } else {
                statement.execute("CREATE TABLE course_info ( course_id VARCHAR(10) NOT NULL PRIMARY KEY, course_title VARCHAR(30) NOT NULL , credit DOUBLE NOT NULL , year INT NOT NULL , semester INT NOT NULL , courseType VARCHAR(8) NOT NULL )");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
           public void setupMessageTable() {
        try {
            String TABLE_NAME = "message";
            System.out.println("here");
            statement = con.createStatement();
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exists! Ready to go");

            } else {
                
                statement.execute("CREATE TABLE message ( teacher_id VARCHAR(30) NOT NULL , teacher_name VARCHAR(50) NOT NULL , year INT NOT NULL , semester INT NOT NULL , date TIMESTAMP NOT NULL , message_title VARCHAR(100)  , message_description VARCHAR(2000))");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public void setupTeachersInfoTable() {
        try {
            String TABLE_NAME = "teachers_info";
            statement = con.createStatement();
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exists! Ready to go");

            } else {
                statement.execute("CREATE TABLE teachers_info ( id VARCHAR(30) NOT NULL , password VARCHAR(50) NOT NULL , name VARCHAR(50) NOT NULL , mobile VARCHAR (13) , email VARCHAR (30) , description VARCHAR(2000)  , active BOOLEAN DEFAULT true , image BLOB )");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void setupStudentCgpaTable() {
        try {
            String TABLE_NAME = "student_cgpa";
            statement = con.createStatement();
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exists! Ready to go");

            } else {
                statement.execute("CREATE TABLE student_cgpa ( id VARCHAR(30) PRIMARY KEY , s11 DOUBLE  , s12 DOUBLE  , s21 DOUBLE  , s22 DOUBLE  , s31 DOUBLE  , s32 DOUBLE  , s41 DOUBLE  , s42 DOUBLE  , current_cgpa DOUBLE  )");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void createConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = (Connection) DriverManager.getConnection(db_url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        
        DatabaseHandler dbh = new DatabaseHandler();
        

    }
}
