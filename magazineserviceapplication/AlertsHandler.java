/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineserviceapplication;


/**
 * @filename AlertsHandler.java
 * @purpose This class is responsible for displaying alert messages in a modal dialog window. 
 *          It is used to show notifications or warnings to the user with a customizable message.
 * @date 04/10/2024
 * @author Zaina Shahid
 *
 * @assumptions:
 *  - This class is designed to be used within a JavaFX application.
 *  - The alert message passed as a parameter is a non-empty string.
 *
 * @expected input:
 *  - A string message that will be displayed in the alert box.
 *
 * @expected output:
 *  - A modal dialog window with the specified message and a "Close" button to dismiss the alert.
 */

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.geometry.Pos;

   
public class AlertsHandler 
{

    
    /**
     * Displays an alert window with the specified message.
     * Precondition: The message text must be a valid, non-null string.
     * Post condition: A modal dialog with the message is shown, which the user can close by pressing "Close".
     * 
     * @param text The message to display in the alert window.
     */
    public void showAlert(String text) {
        Stage alertWindow = new Stage();
        Scene scene;

        // Settings for the new window
        alertWindow.initModality(Modality.APPLICATION_MODAL);
        alertWindow.setTitle("Alert Box");
        alertWindow.setResizable(false);

        // Label for the custom alert message
        Label alertLabel = new Label(text);
        alertLabel.setStyle("-fx-font-size: 16px; -fx-wrap-text: true; "
                + "-fx-font-family: 'Comic Sans MS'; -fx-text-fill: #8B5E3C;"); 

        // Close window button
        Button closeButton = new Button("Close");
        closeButton.setStyle("-fx-background-color: #FFD1DC; -fx-text-fill: #8B5E3C; "
                + "-fx-font-size: 14px; -fx-font-family: 'Comic Sans MS'; "
                + "-fx-border-color: #FFC0CB; -fx-border-radius: 15px; -fx-background-radius: 15px;");
        closeButton.setOnAction(e -> alertWindow.close());

        // Arranging of nodes
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color: #FFF5E1;"); 

        // Add custom text and close button to VBox
        box.getChildren().addAll(alertLabel, closeButton);

        // Set the scene
        scene = new Scene(box, 250, 150); 
        alertWindow.setScene(scene);
        alertWindow.showAndWait();
    }
}



