/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package magazineserviceapplication;


/**
 * @filename MagazineServiceApplication.java
 * @purpose This class is the main entry point for the Magazine Service Application.
 *          It initializes the GUI, manages different modes (create, view, edit),
 *          and handles transitions between them.
 * @date 05/10/2024
 * @author Zaina Shahid
 * 
 * @assumptions
 *  - The application starts with a create mode by default.
 *  - The GUI components (buttons, choices) are initialized and responsive.
 *  - Valid magazine names are chosen when viewing or editing.
 * 
 * @expected input:
 *  - User interactions with buttons and selections for mode switching.
 * 
 * @expected output:
 *  - Different application modes displayed (create, view, edit) with functionality based on user interactions.
 */

import javafx.application.Application;
import javafx.stage.Stage;

public class MagazineServiceApplication extends Application
{
    private MagazineServiceHandler magazinehandler = new MagazineServiceHandler();
    private MagazineServiceApplicationGUI m_gui;
    private AlertsHandler alert = new AlertsHandler();
    private Magazine magazine = new Magazine();
    
    private String m_magazineName;
    
    
     /**
     * JavaFX application entry point.
     * Precondition: None.
     * Postcondition: The StartPage screen is displayed.
     * @param primaryStage the primary stage for the JavaFX application.
     */
    @Override
    public void start(Stage primaryStage)
    {
        
        // Display the StartPage screen initially
        StartPage startPage = new StartPage(primaryStage, this);
        startPage.show();
      
    }
    
    
     /**
     * Initializes the main application GUI in create mode.
     * Precondition: Called from StartPage when start button is clicked.
     * Postcondition: GUI is initialized in create mode with main buttons set up.
     * @param primaryStage the primary stage for the JavaFX application.
     */
    // This method will be called from StartPage when the start button is clicked
    public void initializeMainApplication(Stage primaryStage) 
    {
        m_gui = new MagazineServiceApplicationGUI(primaryStage);
        
        // our program will initially start with the create mode 
        switchToCreate();
        setupMainButtons();
    }
    
    
    /**
     * Sets up the main navigation buttons for switching between view, create, and edit modes.
     * Precondition: m_gui is initialized.
     * Postcondition: Main buttons are set up to navigate between modes.
     */
    public void setupMainButtons() 
    {
        m_gui.getViewButton().setOnAction(e -> checkMagazineViewMode());
        m_gui.getCreateButton().setOnAction(e -> switchToCreate());
        m_gui.getEditButton().setOnAction(e -> checkMagazineEditMode());
    }
  
   /**
     * Switches to the create mode by initializing CreateController.
     * Precondition: m_gui is initialized.
     * Postcondition: Create mode is displayed.
     */
    public void switchToCreate() 
    {
        new CreateController(m_gui, magazinehandler, alert, this).initialize();
    }

    
   /**
     * Switches to the edit mode by initializing EditController.
     * Precondition: A valid magazine name is provided.
     * Postcondition: Edit mode is displayed for the specified magazine.
     * @param m_magazineName the name of the magazine to edit.
     */
    public void switchToEdit(String m_magazineName) 
    {
        new EditController(m_gui, magazinehandler, alert, this, m_magazineName).initialize();
    }


   /**
     * Switches to the view mode by initializing ViewController.
     * Precondition: A valid magazine name is provided.
     * Postcondition: View mode is displayed for the specified magazine.
     * @param m_magazineName the name of the magazine to view.
     */
    public void switchToView(String m_magazineName) 
    {
       new ViewController(m_gui, magazinehandler, alert, this, m_magazineName).initialize();
    }


    
    /**
     * Checks for magazine selection before switching to view mode.
     * Precondition: m_gui is initialized.
     * Postcondition: User is prompted to select a magazine before viewing.
     */
    public void checkMagazineViewMode() {
        m_gui.magazineViewCheck();

        m_gui.getMagazineChoice().getItems().addAll(magazinehandler.getAllMagazineNames());

        m_gui.getSubmitButton().setOnAction(e -> {
            m_magazineName = m_gui.getMagazineChoice().getSelectionModel().getSelectedItem();
            if (m_magazineName != null) {
                switchToView(m_magazineName);
                m_gui.getMagazineChoice().setValue(null);
            } else {
                m_gui.getMagazineChoice().setValue(null);
                alert.showAlert("No magazine selected");
            }
        });

        m_gui.getCreateButton().setOnAction(e -> switchToCreate());
        m_gui.getEditButton().setOnAction(e -> checkMagazineEditMode());
    }
    
      /**
     * Checks for magazine selection before switching to edit mode.
     * Precondition: m_gui is initialized.
     * Postcondition: User is prompted to select a magazine before editing.
     */
    public void checkMagazineEditMode() {
        m_gui.magazineEdit();

        m_gui.getMagazineChoice().getItems().addAll(magazinehandler.getAllMagazineNames());

        m_gui.getSubmitButton().setOnAction(e -> {
            m_magazineName = m_gui.getMagazineChoice().getSelectionModel().getSelectedItem();
            if (m_magazineName != null) {
                switchToEdit(m_magazineName);
                m_gui.getMagazineChoice().setValue(null);
            } else {
                m_gui.getMagazineChoice().setValue(null);
                alert.showAlert("No magazine selected");
            }
        });

        m_gui.getViewButton().setOnAction(e -> checkMagazineViewMode());
        m_gui.getCreateButton().setOnAction(e -> switchToCreate());
    }
    
    
    /**
     * Main method for launching the JavaFX application.
     * Precondition: None.
     * Postcondition: Application starts and displays the GUI.
     * @param args command-line arguments for the application.
     */
    public static void main(String[] args) 
    {
       // StartPage.main(args);
        launch(args);
    }
    
}