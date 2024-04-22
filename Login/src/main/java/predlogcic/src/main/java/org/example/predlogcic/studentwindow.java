package org.example.predlogcic;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class studentwindow extends Application {
    // ObservableList for holding the names of enrolled classes
    private ObservableList<String> enrolledClasses = FXCollections.observableArrayList();
    // ObservableList for holding CheckBoxes for each available class
    private ObservableList<CheckBox> availableClasses = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Student Class Management");

        // Load classes from a file into availableClasses list
        loadClassesFromFile();

        // ListView for displaying enrolled classes
        ListView<String> enrolledListView = new ListView<>(enrolledClasses);
        VBox enrolledBox = new VBox(10, enrolledListView);
        enrolledBox.setPadding(new Insets(10));
        enrolledBox.setAlignment(Pos.TOP_CENTER);

        // ListView for displaying available classes with checkboxes
        ListView<CheckBox> availableListView = new ListView<>(availableClasses);
        VBox availableBox = new VBox(10, availableListView);
        availableBox.setPadding(new Insets(10));
        availableBox.setAlignment(Pos.TOP_CENTER);

        // Button to add selected classes to the enrolled list
        Button addClassButton = new Button("Add Class");
        addClassButton.setOnAction(e -> addSelectedClasses(enrolledClasses, availableListView));

        // Button to remove selected class from the enrolled list
        Button removeClassButton = new Button("Remove Class");
        removeClassButton.setOnAction(e -> removeSelectedClasses(enrolledClasses, enrolledListView));

        // HBox for layout of buttons
        HBox buttonBox = new HBox(10, addClassButton, removeClassButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));

        // HBox for layout of the two lists (enrolled and available)
        HBox mainBox = new HBox(10, enrolledBox, availableBox);
        mainBox.setAlignment(Pos.CENTER);

        // Root VBox to organize everything vertically
        VBox root = new VBox(10, mainBox, buttonBox);
        root.setPadding(new Insets(10));

        // Scene and stage setup
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to load class names from a file into availableClasses list
    private void loadClassesFromFile() {
        String path = "src/main/resources/Students/Classes.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                CheckBox checkBox = new CheckBox(line); // Each line creates a new CheckBox
                availableClasses.add(checkBox); // Add CheckBox to the list
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to add selected classes from available to enrolled
    private void addSelectedClasses(ObservableList<String> enrolled, ListView<CheckBox> available) {
        List<CheckBox> toMove = new ArrayList<>();
        for (CheckBox cb : available.getItems()) {
            if (cb.isSelected()) {
                enrolled.add(cb.getText()); // Add the class name to enrolled
                toMove.add(cb); // Mark for removal from available
            }
        }
        available.getItems().removeAll(toMove); // Remove the selected checkboxes from available
    }

    // Method to remove selected class from enrolled
    private void removeSelectedClasses(ObservableList<String> enrolled, ListView<String> enrolledListView) {
        String selected = enrolledListView.getSelectionModel().getSelectedItem(); // Get selected class name
        if (selected != null) {
            enrolled.remove(selected); // Remove the class from enrolled
            availableClasses.add(new CheckBox(selected)); // Re-add to available as a CheckBox
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
