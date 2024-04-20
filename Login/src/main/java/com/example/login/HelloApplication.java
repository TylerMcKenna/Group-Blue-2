package com.example.login;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @FXML
    private Button btnSigninScreen;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 500);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        ReadWrite.addCourse("Business Ethics", "000112");
        ReadWrite.addCourse("Programming 2", "927461");
        ReadWrite.addUser("tyler", "tmckenna@my.okcu.edu", "00116151", "tyler100", false);
        ReadWrite.addUser("jeff", "jmaxwell@okcu.edu", "22003243", "jeff100", true);
        ReadWrite.addUserCourse("22003242", "000112");
        launch();
    }
}