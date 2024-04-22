package org.example.predlogcic;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class professorwindow extends Application {

    // Constants defining paths and file names
    private static final String RESOURCES_PATH = "src/main/resources/Students";
    private static final String CLASSES_FILE = "Classes.txt";
    private static final String STUDENT_FILE = "StudentName.txt";
    private static final String STUDENT_ID_FILE = "StudentID.txt";
    private static final String PICTURE_PATH = "src/main/resources/picturelol/STARS.png";

    @Override
    public void start(Stage primaryStage) {
        // ListView for displaying file names
        ListView<String> listView = new ListView<>();
        // TextArea for displaying file content
        TextArea textArea = new TextArea();
        // Buttons for adding items and toggling remove mode
        Button btnAddItems = new Button("Add Items");
        Button btnRemoveMode = new Button("Remove mode");

        // Reading files from a specified folder that end with .txt
        File folder = new File(RESOURCES_PATH);
        File[] listOfFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

        // Add file names to the list view
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                listView.getItems().add(file.getName());
            }
        }

        // Event listener for listView selection changes
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    // Read file content and display it in the textArea
                    String content = new String(Files.readAllBytes(Paths.get(RESOURCES_PATH, newValue)));
                    textArea.setText(content);
                } catch (IOException e) {
                    textArea.setText("Error reading file: " + newValue);
                }
            }
        });

        // VBox to hold buttons for operations
        VBox buttonsBox = new VBox(10, btnAddItems, btnRemoveMode);

        // VBox for adding class or student entries
        VBox addItemsBox = new VBox(10);
        TextField classField = new TextField();
        Button btnAddClass = new Button("Add Class");
        TextField studentField = new TextField();
        Button btnAddStudent = new Button("Add Student");
        Button btnReturn = new Button("Return to View");

        // Adding components to addItemsBox
        addItemsBox.getChildren().addAll(
                new HBox(5, btnAddClass, classField),
                new HBox(5, btnAddStudent, studentField),
                btnReturn
        );

        // Event handlers for adding class or student
        btnAddClass.setOnAction(e -> saveToFile(classField.getText(), CLASSES_FILE));
        btnAddStudent.setOnAction(e -> {
            String studentName = studentField.getText();
            if (!studentName.isEmpty()) {
                saveToFile(studentName, STUDENT_FILE);
                String studentID = generateRandomID();  // Generate a unique ID for the student
                saveToFile(studentID, STUDENT_ID_FILE);
            }
            studentField.clear();  // Clear the input field after saving
        });
        btnReturn.setOnAction(e -> {
            addItemsBox.setVisible(false);
            buttonsBox.setVisible(true);
        });

        // Event handler to toggle visibility of addItemsBox
        btnAddItems.setOnAction(e -> {
            addItemsBox.setVisible(true);
            buttonsBox.setVisible(false);
        });

        // Components for removal of entries
        TextField removeField = new TextField();
        Button btnRemove = new Button("Remove");
        HBox removeBox = new HBox(5, btnRemove, removeField);
        removeBox.setVisible(false);

        // Toggle visibility of remove box
        btnRemoveMode.setOnAction(e -> removeBox.setVisible(!removeBox.isVisible()));

        // Handler for remove button
        btnRemove.setOnAction(e -> {
            String valueToRemove = removeField.getText();
            removeEntry(valueToRemove);
            listView.getItems().remove(valueToRemove); // Also remove from the list view
            listView.refresh();
            textArea.clear();
            removeField.clear();
        });

        // Load and display an image
        Image image = new Image(new File(PICTURE_PATH).toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(100);  // Set the image height (adjust as necessary)

        // Layout for the bottom part of the window, combining image and addItemsBox
        HBox bottomLayout = new HBox(imageView, addItemsBox);
        bottomLayout.setSpacing(10);

        // Main layout setup using BorderPane
        BorderPane root = new BorderPane();
        root.setLeft(listView);
        root.setCenter(textArea);
        root.setRight(buttonsBox);
        root.setTop(removeBox);
        root.setBottom(bottomLayout);  // Set combined layout to bottom

        // Initially hide the addItemsBox
        addItemsBox.setVisible(false);

        // Setting up the scene and stage
        Scene scene = new Scene(root, 800, 400);
        primaryStage.setTitle("Professor Window");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Helper method to save text to file
    private void saveToFile(String text, String fileName) {
        try {
            // Append text to a file, creating the file if it doesn't exist
            Files.write(Paths.get(RESOURCES_PATH, fileName), (text + System.lineSeparator()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to remove an entry from file
    private void removeEntry(String valueToRemove) {
        try {
            // Paths to various text files
            Path classesPath = Paths.get(RESOURCES_PATH, CLASSES_FILE);
            Path studentPath = Paths.get(RESOURCES_PATH, STUDENT_FILE);
            Path studentIdPath = Paths.get(RESOURCES_PATH, STUDENT_ID_FILE);

            // Remove from Classes.txt if present
            removeEntryFromFilePath(classesPath, valueToRemove);

            // Remove from StudentName.txt and StudentID.txt if present
            removeStudentEntry(studentPath, studentIdPath, valueToRemove);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper method to remove a student entry from both name and ID files
    private void removeStudentEntry(Path studentPath, Path studentIdPath, String valueToRemove) throws IOException {
        List<String> studentNames = Files.readAllLines(studentPath);
        int indexToRemove = studentNames.indexOf(valueToRemove.trim());

        if (indexToRemove != -1) {
            studentNames.remove(indexToRemove);
            Files.write(studentPath, studentNames, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);

            List<String> studentIDs = Files.readAllLines(studentIdPath);
            if (indexToRemove < studentIDs.size()) {
                studentIDs.remove(indexToRemove);
                Files.write(studentIdPath, studentIDs, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
            }
        }
    }

    // Helper method to remove any text entry from a specified file
    private void removeEntryFromFilePath(Path filePath, String valueToRemove) throws IOException {
        List<String> entries = Files.readAllLines(filePath);
        if (entries.removeIf(entry -> entry.trim().equals(valueToRemove))) {
            Files.write(filePath, entries, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        }
    }

    // Helper method to generate a random student ID
    private String generateRandomID() {
        int randomNumber = (int) (Math.random() * 100000000);  // Generate a random number with up to 8 digits
        return "B" + String.format("%08d", randomNumber);  // Ensure the number is exactly 8 digits long
    }

    public static void main(String[] args) {
        launch(args);
    }
}
