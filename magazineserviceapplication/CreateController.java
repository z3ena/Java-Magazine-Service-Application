/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineserviceapplication;

/**
 * @filename CreateController.java
 * @purpose Controller class for handling the creation, loading, and saving of magazines.
 * @date 05/10/2024
 * @author Zaina Shahid
 * 
 * @assumptions
 *  - The GUI has buttons and text fields initialized as per the requirements.
 *  - Magazines are uniquely identified by their names.
 *  - Only valid magazine names are processed.
 * 
 * @expected input:
 *  - User-provided magazine names and file selections.
 * 
 * @expected output:
 *  - Magazines saved, loaded, or added to the application as specified by the user.
 */

import javafx.stage.Stage;
import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.TextField;

public class CreateController
{
    
    private MagazineServiceApplicationGUI m_gui;
    private MagazineServiceHandler magazinehandler;
    private AlertsHandler alert;
    private MagazineServiceApplication mainApp;
    
    
    /**
     * Constructor for CreateController.
     * Precondition: GUI, MagazineHandler, AlertHandler, and MainApp are not null.
     * Postcondition: CreateController is initialized with the provided dependencies.
     * 
     * @param m_gui The main GUI application instance.
     * @param magazinehandler Handler for magazine-related operations.
     * @param alert Alert handler for displaying user notifications.
     * @param mainApp The main application class instance.
     */
    public CreateController(MagazineServiceApplicationGUI m_gui, MagazineServiceHandler magazinehandler, AlertsHandler alert, MagazineServiceApplication mainApp) 
    {
        this.m_gui = m_gui;
        this.magazinehandler = magazinehandler;
        this.alert = alert;
        this.mainApp = mainApp;
    }
   
    
     /**
     * Initializes the Create Mode by setting up buttons and handlers.
     * Precondition: The GUI should be in Create Mode.
     * Postcondition: Buttons are configured for creating, loading, and saving magazines.
     */
    public void initialize() 
    {
        m_gui.createMode();

        m_gui.getAddMagazineButton().setOnAction(e -> addMagazineMode());
        m_gui.getLoadMagazineButton().setOnAction(e -> loadMagazineMode());
        m_gui.getSaveMagazineButton().setOnAction(e -> saveMagazineMode());

        m_gui.getViewButton().setOnAction(e -> mainApp.checkMagazineViewMode());
        m_gui.getEditButton().setOnAction(e -> mainApp.checkMagazineEditMode());
    }
    
    
    /**
     * Switches the GUI to Add Magazine Mode and sets up the form for adding a new magazine.
     * Precondition: GUI elements for magazine name input and submission button are present.
     * Postcondition: Validates and adds a new magazine if input is correct.
     */
    private void addMagazineMode()
    {
        m_gui.addMagazineMode();
        mainApp.setupMainButtons();

        m_gui.getSubmitButton().setOnAction(e -> {
            String m_magazineName = m_gui.getMagazineNameTextField().getText();
            // To check if magazine field is empty
            if (!m_magazineName.trim().isEmpty()) {
                // To check length of magazine name does not exceed 25 letters
                if (m_magazineName.length() > 25) {
                    alert.showAlert("Magazine name cannot be more than 25 characters");
                } else {
                    // Check for same magazine name
                    if (magazinehandler.compareMagazine(m_magazineName)) {
                        alert.showAlert("'" + m_magazineName + "' already exist");
                    } else {
                        magazinehandler.addMagazine(m_magazineName);
                        //switchToCreate();
                    }
                }
            } else {
                m_gui.getMagazineNameTextField().clear();
                alert.showAlert("Magazine name cannot be empty or contain only whitespaces");
            }
        });

    }
   
    
     /**
     * Handles loading a magazine from a file using the FileChooser.
     * Precondition: A valid file must be selected from the FileChooser.
     * Postcondition: Loads the selected magazine(s) into the application.
     */
     private void loadMagazineMode() 
     {
        m_gui.loadMagazineMode();

        // Check if file is selected
        if (m_gui.getSelectedFile() != null) {
            for (File file : m_gui.getSelectedFile()) {
                String m_magazineName = file.getName().replace(".ser", "");
                magazinehandler.loadMagazineFromFile(m_magazineName);
            }
        } else {
            alert.showAlert("No file selected");
        }
    }
     
     /**
     * Saves a selected magazine to a file.
     * Precondition: The magazine must exist in the application.
     * Postcondition: Saves the selected magazine to a .ser file.
     */
      private void saveMagazineMode() 
      {
        m_gui.saveMagazineMode();
         mainApp.setupMainButtons();

        m_gui.getMagazineChoice().getItems().addAll(magazinehandler.getAllMagazineNames());

        m_gui.getSubmitButton().setOnAction(e -> {
            // Retrieve selected magazine
            String m_magazineName = m_gui.getMagazineChoice().getSelectionModel().getSelectedItem();
            // Check if magazine is selected
            if (m_magazineName != null) {
                magazinehandler.saveMagazineToFile(m_magazineName);
                initialize();
              
            } else {
                m_gui.getMagazineChoice().setValue(null);
                alert.showAlert("No magazine selected");
            }
        });
    }


}