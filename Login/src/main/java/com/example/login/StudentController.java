package com.example.login;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;


import java.net.URL;
import java.util.ResourceBundle;


public class StudentController implements Initializable {

    ObservableList<Course> courseList;

    @FXML
    private TableColumn<Course, String> CRN;

    @FXML
    private TableColumn<Course, String> courseName;

    @FXML
    private TableView<Course> courseTable;

    @FXML
    private void coursePressed(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        courseList = FXCollections.observableArrayList(
                new Course("Business Ethics", "00112"),
                new Course("Programming II", "927461")
        );


        CRN.setCellValueFactory(new PropertyValueFactory<Course, String>("CRN"));
        courseName.setCellValueFactory(new PropertyValueFactory<Course, String>("courseName"));

        courseTable.setItems(courseList);
    }
}
