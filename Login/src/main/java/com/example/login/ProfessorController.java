package com.example.login;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ProfessorController implements Initializable {



    ObservableList<Course> courseList;
    ObservableList<User> studentList;


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
        courseList = FXCollections.observableArrayList(
                new Course("Business Ethics", "00112"),
                new Course("Programming II", "927461")
        );


        CRN.setCellValueFactory(new PropertyValueFactory<Course, String>("CRN"));
        courseName.setCellValueFactory(new PropertyValueFactory<Course, String>("courseName"));

        email.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        userName.setCellValueFactory(new PropertyValueFactory<User, String>("name"));

        courseTable.setItems(courseList);
    }


    public void updatePressed(MouseEvent event) {
        //courseTable.getSelectionModel().getSelectedItem();
        System.out.println(courseTable.getSelectionModel().getSelectedItem().getCRN());
        CourseNameReplace.getText();
        CourseNumReplace.getText();

    }
}
