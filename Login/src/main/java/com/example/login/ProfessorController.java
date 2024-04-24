package com.example.login;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class ProfessorController implements Initializable {



    ObservableList<Course> courseList;
    ObservableList<User> studentList;
  
    private User user;
    private Course selectedCourse;

    @FXML
    private TextField CRNTextField, BNUMTextField;

    @FXML
    private Label lblHello, lblCRNAlert, lblBNumAlert;

    @FXML
    public TextField CourseNumReplace;
    @FXML
    public TextField CourseNameReplace;
    @FXML
    public Button UpdateButton;

    @FXML
    private TableColumn<Course, String> CRN;

    @FXML
    private TableColumn<Course, String> courseName;


    @FXML
    private TableView<Course> courseTable;


    @FXML
    private TableColumn<User, String> email;


    @FXML
    private TableView<User> studentTable;


    @FXML
    private TableColumn<User, String> userName;

    public void setUser(User user) throws IOException {
        this.user = user;
        lblHello.setText("Hello, " + user.getName());

        ObservableList<Course> courseList = FXCollections.observableList(ReadWrite.getUserClasses(user.getbNumber()));
        courseTable.setItems(courseList);
    }

    @FXML
    private void coursePressed(MouseEvent event) throws IOException {
            studentList = FXCollections.observableArrayList(ReadWrite.getClassUsers(courseTable.getSelectionModel().getSelectedItem().getCRN()));
            studentTable.setItems(studentList);

            CourseNameReplace.setText(courseTable.getSelectionModel().getSelectedItem().getCourseName());
            CourseNumReplace.setText(courseTable.getSelectionModel().getSelectedItem().getCRN());
            selectedCourse = courseTable.getSelectionModel().getSelectedItem();
            //Puts the information from the table into the textfields
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CRN.setCellValueFactory(new PropertyValueFactory<Course, String>("CRN"));
        courseName.setCellValueFactory(new PropertyValueFactory<Course, String>("courseName"));

        email.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        userName.setCellValueFactory(new PropertyValueFactory<User, String>("name"));

        courseTable.setItems(courseList);
    }

    @FXML
    void deleteClass(ActionEvent event) throws IOException {
        if (courseTable.getItems().size() > 1) {
            Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();
            ReadWrite.deleteUserCourse(user.getbNumber(), selectedCourse.getCRN());
            ObservableList<Course> courseList = FXCollections.observableList(ReadWrite.getUserClasses(user.getbNumber()));
            courseTable.setItems(courseList);
            studentTable.getItems().clear();
        }
    }

    @FXML
    void addClass(ActionEvent event) throws IOException {
        File courseListFile = new File("courseList.xml");
        String course = new String(Files.readAllBytes(Paths.get(courseListFile.getPath())), StandardCharsets.UTF_8);
        int indexOfCRN = course.indexOf("<CRN>" + CRNTextField.getText() + "</CRN>");
        if (indexOfCRN != -1) {
            lblCRNAlert.setVisible(false);
            ReadWrite.addUserCourse(user.getbNumber(), CRNTextField.getText());
        } else {
            lblCRNAlert.setVisible(true);
        }
        ObservableList<Course> courseList = FXCollections.observableList(ReadWrite.getUserClasses(user.getbNumber()));
        courseTable.setItems(courseList);
    }

    @FXML
    void deleteStudent(ActionEvent event) throws IOException {
        // Professors can lowkey destroy themselves.
        if (studentTable.getItems().size() > 1 && studentTable.getSelectionModel().getSelectedItem().getbNumber() != user.getbNumber()) {
            User selectedStudent = studentTable.getSelectionModel().getSelectedItem();
            Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();
            ReadWrite.deleteUserCourse(selectedStudent.getbNumber(), selectedCourse.getCRN());
            studentList = FXCollections.observableArrayList(ReadWrite.getClassUsers(courseTable.getSelectionModel().getSelectedItem().getCRN()));
            studentTable.setItems(studentList);
            ObservableList<Course> courseList = FXCollections.observableList(ReadWrite.getUserClasses(user.getbNumber()));
            courseTable.setItems(courseList);
        }
    }

    @FXML
    void addStudent(ActionEvent event) throws IOException {
        File userListFile = new File("userList.xml");
        String users = new String(Files.readAllBytes(Paths.get(userListFile.getPath())), StandardCharsets.UTF_8);
        int indexOfBNumber = users.indexOf("<bNumber>" + BNUMTextField.getText() + "</bNumber>");
        if (indexOfBNumber != -1) {
            lblBNumAlert.setVisible(false);
            ReadWrite.addUserCourse(BNUMTextField.getText(), selectedCourse.getCRN());
        } else {
            lblBNumAlert.setVisible(true);
        }
        ObservableList<User> studentList = FXCollections.observableList(ReadWrite.getClassUsers(selectedCourse.getCRN()));
        studentTable.setItems(studentList);



    @FXML
    public void updatePressed(MouseEvent event) {
        //courseTable.getSelectionModel().getSelectedItem();
        System.out.println(courseTable.getSelectionModel().getSelectedItem().getCRN());
        CourseNameReplace.getText();
        CourseNumReplace.getText();
    }
}
