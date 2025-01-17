/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineserviceapplication;

/**
 * @filename StartPage.java
 * @purpose This class represents the start page for the Magazine Service Application. It displays 
 *          student details and a "Start" button that launches the main application interface.
 * @date 04/10/2024
 * @author Zaina Shahid
 *
 * @assumptions:
 *  - This class is designed to be used as the entry screen in a JavaFX application.
 *  - A valid reference to `Stage` and `MagazineServiceApplication` should be provided.
 *
 * @expected input:
 *  - None; this class only displays a static start page with a button to proceed.
 *
 * @expected output:
 *  - A window displaying the student details and a button to proceed to the main application.
 */

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartPage {

    private Stage primaryStage;
    private MagazineServiceApplication mainApp;

     /**
     * Parameterized Constructor.
     * Precondition: `primaryStage` and `mainApp` must be valid instances.
     * Post condition: A StartPage object is created with the provided stage and main application reference.
     * 
     * @param primaryStage The primary stage on which the StartPage is displayed.
     * @param mainApp A reference to the main application for transitioning.
     */
    public StartPage(Stage primaryStage, MagazineServiceApplication mainApp) 
    {
        this.primaryStage = primaryStage;
        this.mainApp = mainApp;
    }

    /**
     * Displays the start page with student details and a "Start" button.
     * Precondition: `primaryStage` is a valid JavaFX stage.
     * Post condition: The start page scene is set on the primary stage.
     */
    public void show()
    {
        // Create a label for student details
        Label studentDetailsLabel = new Label(displayStudentDetails());
        studentDetailsLabel.setStyle("-fx-text-fill: #000000; " +                // Black text
                                     "-fx-font-size: 16px; " +
                                     "-fx-font-family: 'Comic Sans MS'; " +      // Comic Sans font
                                     "-fx-font-weight: normal; " +               // Not too bold
                                     "-fx-padding: 10; " +
                                     "-fx-background-color: #ffffff; " +         // White background
                                     "-fx-background-radius: 10; " +
                                     "-fx-border-radius: 10; " +
                                     "-fx-border-color: #ff9999; " +             // Pink border
                                     "-fx-border-width: 1;");

        // Create a start button with minimal pink accents
        Button startButton = new Button("Start");
        startButton.setStyle("-fx-background-color: #ffffff; " +                 // White background
                             "-fx-text-fill: #000000; " +                        // Black text
                             "-fx-font-size: 14px; " +
                             "-fx-font-family: 'Comic Sans MS'; " +              // Comic Sans font
                             "-fx-font-weight: normal; " +
                             "-fx-padding: 10 20; " +
                             "-fx-background-radius: 10; " +
                             "-fx-border-radius: 10; " +
                             "-fx-border-color: #ff9999; " +                     // Soft pink border
                             "-fx-border-width: 2;");

        // Define button action to open the main application UI
        startButton.setOnAction(e -> openMainApplication(primaryStage));

        // Arrange elements in a VBox
        VBox root = new VBox(20, studentDetailsLabel, startButton);
        root.setStyle("-fx-background-color: #ffffff; " +                        // White background
                      "-fx-padding: 20;");
        root.setSpacing(20);
        root.setAlignment(javafx.geometry.Pos.CENTER);

        // Set the scene and show the start page
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Magazine Service Application - Start Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    /**
     * Opens the main application by calling `initializeMainApplication` on the main application instance.
     * Precondition: `mainApp` is a valid instance of `MagazineServiceApplication`.
     * Post condition: The main application interface is displayed.
     * 
     * @param primaryStage The primary stage to set the main application scene on.
     */

    private void openMainApplication(Stage primaryStage) {
        
        // Use the existing mainApp reference to initialize the main application
        mainApp.initializeMainApplication(primaryStage);
       
    }

    /**
     * Generates and returns a formatted string containing student details.
     * Precondition: None.
     * Post condition: Returns the formatted string with student information.
     * 
     * @return A string with student details.
     */
    // Display student details
    private String displayStudentDetails() {
        return "Name: Zaina Shahid\n" +
               "Student ID: 34669919\n" +
               "Tutor Name: Noor Alkhateeb\n" +
               "ICT373 Assignment - 2 \n" +
               "Date: 3rd November 2024";
    }

    
}
