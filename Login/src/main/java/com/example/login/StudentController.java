package com.example.login;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;
/*
    Student controller for student-view.fxml that is a more basic version of the professor page, allowing for users
    to add, remove, and update classes, but not to do anything regarding the users in a class.

    Made primarily by Holt and Vojin.
*/
public class StudentController implements Initializable {

    @FXML
    public Label lblSelectCourse;
    @FXML
    public Label lblCRNAlert;
    @FXML
    public TextField CourseNumReplace;
    @FXML
    public TextField CRNTextField;
    @FXML
    public Label lblHello;
    @FXML
    public TextField CourseNameReplace;
    @FXML
    public Button UpdateButton;

    ObservableList<Course> courseList;
    private User user;
    private Course selectedCourse;

    @FXML
    private TableColumn<Course, String> CRN;

    @FXML
    private TableColumn<Course, String> courseName;

    @FXML
    private TableView<Course> courseTable;

    private Parent root;
    private Stage stage;
    private Scene scene;
    public void logOut(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        root = loader.load();
        HelloController helloController = loader.getController();
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void coursePressed(MouseEvent event) {
        CourseNameReplace.setText(courseTable.getSelectionModel().getSelectedItem().getCourseName());
        CourseNumReplace.setText(courseTable.getSelectionModel().getSelectedItem().getCRN());
        selectedCourse = courseTable.getSelectionModel().getSelectedItem();
        //Puts the information from the table into the textfields
    }

    public void setUser(User user) throws IOException {
        this.user = user;
        lblHello.setText("Hello, " + user.getName());

        ObservableList<Course> courseList = FXCollections.observableList(ReadWrite.getUserClasses(user.getbNumber()));
        courseTable.setItems(courseList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        CRN.setCellValueFactory(new PropertyValueFactory<Course, String>("CRN"));
        courseName.setCellValueFactory(new PropertyValueFactory<Course, String>("courseName"));

        courseTable.setItems(courseList);
    }

    public void addClass(ActionEvent actionEvent) throws IOException {
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

    public void deleteClass(ActionEvent actionEvent) throws IOException {
        if (courseTable.getItems().size() > 1) {
            selectedCourse = courseTable.getSelectionModel().getSelectedItem();
            ReadWrite.deleteUserCourse(user.getbNumber(), selectedCourse.getCRN());
            ObservableList<Course> courseList = FXCollections.observableList(ReadWrite.getUserClasses(user.getbNumber()));
            courseTable.setItems(courseList);
        }
    }
    public void updateClicked (MouseEvent event) throws IOException {

        if (courseTable.getSelectionModel().getSelectedItem() == null) {
            lblSelectCourse.setVisible(true);
        } else {
            lblSelectCourse.setVisible(false);
        }

        ArrayList<User> classList = ReadWrite.getClassUsers(courseTable.getSelectionModel().getSelectedItem().getCRN());
        int length = classList.size();
        int index = 0;

        do {
            User infoGetUser = classList.get(index);
            ReadWrite.addUserCourse(infoGetUser.getbNumber(), CourseNumReplace.getText());
            ReadWrite.deleteUserCourse(infoGetUser.getbNumber(), courseTable.getSelectionModel().getSelectedItem().getCRN());

            index++;
            length--;
        } while (length > 0);

        ReadWrite.addCourse(CourseNameReplace.getText(), CourseNumReplace.getText());
        ReadWrite.deleteCourse(courseTable.getSelectionModel().getSelectedItem().getCourseName(), courseTable.getSelectionModel().getSelectedItem().getCRN());

        ObservableList<Course> courseList = FXCollections.observableList(ReadWrite.getUserClasses(user.getbNumber()));
        courseTable.setItems(courseList);

        CourseNameReplace.setText("");
        CourseNumReplace.setText("");
    }

}
