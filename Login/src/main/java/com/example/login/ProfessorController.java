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

    @FXML
    private TextField CRNTextField;

    @FXML
    private Label lblHello, lblCRNAlert;
    @FXML
    public Label lblSelectCourse;

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
        if (event.getClickCount() == 2) //Checking double click
        {
            studentList = FXCollections.observableArrayList(ReadWrite.getClassUsers(courseTable.getSelectionModel().getSelectedItem().getCRN()));
            studentTable.setItems(studentList);

            CourseNameReplace.setText(courseTable.getSelectionModel().getSelectedItem().getCourseName());
            CourseNumReplace.setText(courseTable.getSelectionModel().getSelectedItem().getCRN());
            //Puts the information from the table into the textfields
        }
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
    void deleteStudent(ActionEvent event) {

    }

    @FXML
    void addStudent(ActionEvent event) {
    }


    @FXML
    public void updateClicked(MouseEvent event) throws IOException {

        if (courseTable.getSelectionModel().getSelectedItem() == null) {
            lblSelectCourse.setVisible(true);
        } else {
            lblSelectCourse.setVisible(false);
        }

        System.out.println(courseTable.getSelectionModel().getSelectedItem().getCRN());

        ArrayList<User> classList = ReadWrite.getClassUsers(courseTable.getSelectionModel().getSelectedItem().getCRN());
        int length = classList.size();
        int index = 0;

        do {
            User infoGetUser = classList.get(index);
            ReadWrite.addUserCourse(infoGetUser.getbNumber(),CourseNumReplace.getText());
            ReadWrite.deleteUserCourse(infoGetUser.getbNumber(),courseTable.getSelectionModel().getSelectedItem().getCRN());

            index++;
            length--;

        } while (length > 0);

        ReadWrite.addCourse(CourseNameReplace.getText(),CourseNumReplace.getText());
        ReadWrite.deleteCourse(courseTable.getSelectionModel().getSelectedItem().getCourseName(),courseTable.getSelectionModel().getSelectedItem().getCRN());

        ObservableList<Course> courseList = FXCollections.observableList(ReadWrite.getUserClasses(user.getbNumber()));
        courseTable.setItems(courseList);

        CourseNameReplace.setText("");
        CourseNumReplace.setText("");

    }
}
