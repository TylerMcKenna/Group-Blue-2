package com.example.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.stage.Stage;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class HelloController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private static final String UNICODE_FORMAT = "UTF-8";
    private static ArrayList<User2> userList = new ArrayList<User2>();
    @FXML
    private Label lblWelcome, lblLoginFailed;

    @FXML
    private PasswordField passFieldSU, passFieldSI;

    @FXML
    private TextField txtUsernameSU, txtEmailSU, txtEmailSI, txtBNum;

    @FXML
    private CheckBox chkProfessor;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Group groupSignIn, groupSignUp;

    // creates new styleClass for anchorPanes in Label.css
    @FXML
    private void initialize() throws IOException {
        anchorPane.getStyleClass().add("pane");
        groupSignUp.setVisible(false);

    }

    // changes page
    @FXML
    private void loadSignIn() {
        groupSignUp.setVisible(false);
        groupSignIn.setVisible(true);
    }

    // changes page
    @FXML
    private void loadSignUp() {
        groupSignIn.setVisible(false);
        groupSignUp.setVisible(true);
    }

    public void signInPressed(ActionEvent actionEvent) throws IOException {
        User currentUser = ReadWrite.checkLogin(txtEmailSI.getText(), passFieldSI.getText());

        if (currentUser == null) {
            lblLoginFailed.setVisible(true);
        } else {
            lblLoginFailed.setVisible(false);

            if (currentUser.isProfessor()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("professor-view.fxml"));
                root = loader.load();
                ProfessorController professorController = loader.getController();
                professorController.setUser(currentUser);
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("student-view.fxml"));
                root = loader.load();
                StudentController studentController = loader.getController();
                studentController.setUser(currentUser);
            }
            stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void signUpPressed(ActionEvent actionEvent) throws IOException {
        ReadWrite.addUser(txtUsernameSU.getText(), txtEmailSU.getText(), txtBNum.getText(), passFieldSU.getText(), chkProfessor.isPressed());
    }
}