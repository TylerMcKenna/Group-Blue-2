package com.example.login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/*
    Basic starting point class for the application that starts the application on the login page.

    In the multiline comments is a list of functions that will essentially set a "test" version of
    the database, filled with a few users, a few classes, and a few userCourse junction objects.

    Before running this "database reset" it is recommended that courseList.xml, userCourse.xml, and
    userList.xml are deleted. This is to avoid duplicate entries into the database, which are not
    currently accounted for.

    If you are getting the error "Range [0, -17) out of bounds for length 0",
    the xml files are missing the outermost start and end tags. Delete the 3 aforementioned xml files
    and run the "database reset" again.

    Unfortunately we were not able to implement a dropdown for adding courses and students in time, so
    you will have to look in the database at User bNumbers and Course CRNs to add them. Sorry about the
    inconvenience.

    Please enjoy using D3L!
*/
public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 500);
        stage.setTitle("D3l");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        /*
        ReadWrite.addCourse("Programming I", "20085");
        ReadWrite.addCourse("Programming II", "20086");
        ReadWrite.addCourse("Business Ethics", "10035");
        ReadWrite.addCourse("Composition I", "10011");
        ReadWrite.addUser("tyler", "tmckenna@my.okcu.edu", "03829481", "tyler100", false);
        ReadWrite.addUser("jeff", "jmaxwell@okcu.edu", "22003242", "jeff1000", true);
        ReadWrite.addUserCourse("22003242", "20085");
        ReadWrite.addUserCourse("22003242", "20086");
        ReadWrite.addUserCourse("22003242", "10011");
        ReadWrite.addUserCourse("03829481", "20085");
        ReadWrite.addUserCourse("03829481", "10011");
        */

        launch();
    }
}
