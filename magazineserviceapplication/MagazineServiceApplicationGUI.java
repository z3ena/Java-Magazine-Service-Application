/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineserviceapplication;

/**
 * @filename MagazineServiceApplicationGUI.java
 * @purpose This class handles the graphical user interface (GUI) for the Magazine Service Application.
 *          It provides various modes and views, allowing users to create, edit, view, and delete magazines,
 *          supplements, and customers.
 * @date 04/10/2024
 * @author Zaina Shahid
 * 
 * @assumptions:
 * - This GUI class is initialized with a primary stage.
 * - Various modes are handled within this class, each associated with a specific function.
 * 
 * @expected input:
 * - User interaction with buttons, text fields, and list views.
 * 
 * @expected output:
 * - Different scenes and components are displayed on the primary stage.
 */



import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import java.io.File;
import java.util.List;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MagazineServiceApplicationGUI {
    
    
    // GUI COMPONENTS 
    private GridPane root;
    private TextArea infoPanelBox;
    private Button viewButton, createButton, editButton, submitButton, addSupplementButton, addCustomerButton,
            editSupplementButton, editCustomerButton, deleteSupplementButton, deleteCustomerButton,
            addMagazineButton, loadMagazineButton, saveMagazineButton;
    private ListView<Supplement> supplementsView, supplementChoice, oldSupplements;
    private ListView<Customer> customersView, customerChoice;
    private ComboBox<String> typeOfCustomerComboBox, cardType, magazineChoice;
    private ComboBox<PayingCustomer> payingCustomerChoice;
    private TextField magazineNameTextField, supplementNameTextField, supplementCostTextField, customersNameTextField,
            emailAddressTextField, streetNumberTextField, streetNameTextField, suburbTextField, postCodeTextField,
            accountNumberTextField, typeOfCustomerTextField;
    private Label payingCustomerLabel, accountNumberLabel, currentMagazine;
    private List<File> selectedFile;
    private Stage primaryStage;

    
    /**
     * Constructor initializing the GUI with the provided primary stage.
     * @param primaryStage The main stage for the application.
     */
    public MagazineServiceApplicationGUI(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
    }

    /**
     * Initializes and displays the main homepage layout.
     * Precondition: primaryStage should be set.
     * Post condition: The homepage scene is set on the primaryStage.
     */
    // Set up the main page layout
    public void homepage() 
    {
        root = new GridPane();

        Label header = new Label("Magazine Service Application");
        header.setStyle("-fx-font-size: 30px; -fx-font-family: 'Comic Sans MS'; -fx-text-fill: #E75480;");

        viewButton = createLightButton("View");
        createButton = createLightButton("Create");
        editButton = createLightButton("Edit");

        root.setMinSize(800, 800);
        root.setPadding(new Insets(20));
        root.setVgap(15);
        root.setHgap(15);
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: #FFFFFF;");

        ColumnConstraints col1 = new ColumnConstraints(220);
        col1.setHalignment(HPos.CENTER);
        ColumnConstraints col2 = new ColumnConstraints(220);
        col2.setHalignment(HPos.CENTER);
        ColumnConstraints col3 = new ColumnConstraints(220);
        col3.setHalignment(HPos.CENTER);
        root.getColumnConstraints().addAll(col1, col2, col3);

        root.add(header, 0, 0, 3, 1);
        root.add(viewButton, 0, 1);
        root.add(createButton, 1, 1);
        root.add(editButton, 2, 1);

        Scene scene = new Scene(root);
        primaryStage.setResizable(true);
        primaryStage.setTitle("ICT373 - ASSIGNMENT 2");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
     /**
     * Sets up the view mode layout to view supplements and customers.
     * Precondition: primaryStage should be set.
     * Post condition: View mode components are added to the layout.
     */
    public void viewMode() 
    {
        homepage();
        supplementsView = new ListView<>();
        customersView = new ListView<>();

        Label infoPanel = createStyledLabel("Information Panel:");
        Label supplementPanel = createStyledLabel("List of Supplements:");
        Label customerPanel = createStyledLabel("List of Customers:");
        currentMagazine = createStyledLabel("");

        infoPanelBox = new TextArea();
        infoPanelBox.setEditable(false);
        infoPanelBox.setStyle("-fx-control-inner-background: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        submitButton = createStyledButton("Submit");
        
        root.add(supplementPanel, 0, 3);
        root.add(supplementsView, 0, 4);
        root.add(customerPanel, 0, 5);
        root.add(customersView, 0, 6);
        root.add(infoPanel, 1, 3);
        root.add(infoPanelBox, 1, 4, 2, 3);
        root.add(currentMagazine, 0, 7, 3, 1);
    }
    
     /**
     * Initializes the magazine selection view mode.
     * Precondition: primaryStage should be set.
     * Post condition: Components to select a magazine for viewing are added.
     */
    public void magazineViewCheck() 
    {
        homepage();
        viewButton.setDisable(true);

        Label viewCheckHeader = createStyledLabel("Select a magazine to view:");

        magazineChoice = new ComboBox<>();
        magazineChoice.setPromptText("Options");
        magazineChoice.setStyle("-fx-background-color: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px; "
                + "-fx-font-family: 'Comic Sans MS'; -fx-text-fill: #8B5E3C; -fx-pref-width: 200px;");

        submitButton = createStyledButton("Submit");
        
        root.add(viewCheckHeader, 1, 4);
        root.add(magazineChoice, 1, 5);
        root.add(submitButton, 1, 6);
    }
    
    /**
     * Initializes the create mode layout to allow adding new magazines.
     * Precondition: primaryStage should be set.
     * Post condition: Components for creating a magazine are added to the layout.
     */

    public void createMode() 
    {
        homepage();
        createButton.setDisable(true);

        Label createHeader = createStyledLabel("Choose an Option:");
        addMagazineButton = createStyledButton("Add a new Magazine");
        loadMagazineButton = createStyledButton("Load existing Magazine");
        saveMagazineButton = createStyledButton("Save current Magazine");
        
        submitButton = createStyledButton("Submit");

        root.add(createHeader, 1, 5);
        root.add(addMagazineButton, 1, 6);
        root.add(loadMagazineButton, 1, 8);
        root.add(saveMagazineButton, 1, 10);
    }

    public void addMagazineMode() 
    {
        homepage();

        Label magazineNameLabel = createStyledLabel("Magazine name:");
        magazineNameTextField = new TextField();
        magazineNameTextField.setStyle("-fx-background-color: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        
        submitButton = createStyledButton("Submit");

        root.add(magazineNameLabel, 0, 5);
        root.add(magazineNameTextField, 1, 5);
        root.add(submitButton, 1, 6);
    }

    public void loadMagazineMode() 
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Magazine File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Serialized Files (*.ser)", "*.ser"));

        Stage stage = new Stage();
        selectedFile = fileChooser.showOpenMultipleDialog(stage);
    }

    public void saveMagazineMode()
    {
        homepage();

        Label saveMagazineHeader = createStyledLabel("Select a magazine to save:");
        magazineChoice = new ComboBox<>();
        magazineChoice.setPromptText("Options");
        magazineChoice.setStyle("-fx-background-color: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        
        submitButton = createStyledButton("Submit");

        root.add(saveMagazineHeader, 1, 4);
        root.add(magazineChoice, 1, 5);
        root.add(submitButton, 1, 6);
    }

    public void editMode() 
    {
        homepage();
        editButton.setDisable(true);

        Label editHeader = createStyledLabel("Choose an Option:");
        addSupplementButton = createStyledButton("Add Supplement");
        addCustomerButton = createStyledButton("Add Customer");
        editSupplementButton = createStyledButton("Edit Supplement");
        editCustomerButton = createStyledButton("Edit Customer");
        deleteSupplementButton = createStyledButton("Delete Supplement");
        deleteCustomerButton = createStyledButton("Delete Customer");

        currentMagazine = createStyledLabel("");

        root.add(editHeader, 1, 5);
        root.add(addSupplementButton, 1, 6);
        root.add(addCustomerButton, 1, 8);
        root.add(editSupplementButton, 1, 10);
        root.add(editCustomerButton, 1, 12);
        root.add(deleteSupplementButton, 1, 14);
        root.add(deleteCustomerButton, 1, 16);
        root.add(currentMagazine, 0, 20, 3, 1);
    }

    public void addSupplementMode()
    {
        homepage();

        Label addSupplementHeader = createStyledLabel("Add Supplement:");
        Label supplementNameLabel = createStyledLabel("Supplement name:");
        supplementNameTextField = new TextField();
        supplementNameTextField.setStyle("-fx-background-color: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        
        Label supplementCostLabel = createStyledLabel("Supplement cost (weekly):");
        supplementCostTextField = new TextField();
        supplementCostTextField.setStyle("-fx-background-color: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        submitButton = createStyledButton("Submit");

        root.add(addSupplementHeader, 1, 3);
                root.add(supplementNameLabel, 0, 5);
        root.add(supplementNameTextField, 1, 5);
        root.add(supplementCostLabel, 0, 6);
        root.add(supplementCostTextField, 1, 6);
        root.add(submitButton, 1, 7);
    }

    public void addCustomerMode()
    {
        homepage();

        Label addCustomerHeader = createStyledLabel("Add Customer:");
        Label customerTypeLabel = createStyledLabel("Select type of customer:");
        
        typeOfCustomerComboBox = new ComboBox<>();
        typeOfCustomerComboBox.setPromptText("Options");
        typeOfCustomerComboBox.setStyle("-fx-background-color: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        Label customerNameLabel = createStyledLabel("Customer name:");
        customersNameTextField = new TextField();
        customersNameTextField.setStyle("-fx-background-color: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        Label emailAddressLabel = createStyledLabel("Email Address:");
        emailAddressTextField = new TextField();
        emailAddressTextField.setStyle("-fx-background-color: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        Label streetNumberLabel = createStyledLabel("Street Number:");
        streetNumberTextField = new TextField();
        streetNumberTextField.setStyle("-fx-background-color: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        Label streetNameLabel = createStyledLabel("Street Name:");
        streetNameTextField = new TextField();
        streetNameTextField.setStyle("-fx-background-color: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        Label suburbLabel = createStyledLabel("Suburb:");
        suburbTextField = new TextField();
        suburbTextField.setStyle("-fx-background-color: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        Label postcodeLabel = createStyledLabel("Postcode:");
        postCodeTextField = new TextField();
        postCodeTextField.setStyle("-fx-background-color: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        Label supplementsLabel = createStyledLabel("Select Supplement(s):");
        supplementChoice = new ListView<>();
        supplementChoice.setStyle("-fx-background-color: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        supplementChoice.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        payingCustomerLabel = createStyledLabel("Select Paying Customer:");
        payingCustomerChoice = new ComboBox<>();
        payingCustomerChoice.setStyle("-fx-background-color: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        payingCustomerChoice.setVisible(false);

        Label accountNumberLabel = createStyledLabel("Bank Account Number:");
        accountNumberTextField = new TextField();
        accountNumberTextField.setStyle("-fx-background-color: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        
        cardType = new ComboBox<>();
        cardType.getItems().addAll("Credit Card", "Debit Card");
        cardType.setPromptText("Select Card Type");
        cardType.setStyle("-fx-background-color: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        submitButton = createStyledButton("Submit");

        root.add(addCustomerHeader, 1, 3);
        root.add(customerTypeLabel, 0, 4);
        root.add(typeOfCustomerComboBox, 1, 4);
        root.add(customerNameLabel, 0, 5);
        root.add(customersNameTextField, 1, 5);
        root.add(emailAddressLabel, 0, 6);
        root.add(emailAddressTextField, 1, 6);
        root.add(streetNumberLabel, 0, 7);
        root.add(streetNumberTextField, 1, 7);
        root.add(streetNameLabel, 0, 8);
        root.add(streetNameTextField, 1, 8);
        root.add(suburbLabel, 0, 9);
        root.add(suburbTextField, 1, 9);
        root.add(postcodeLabel, 0, 10);
        root.add(postCodeTextField, 1, 10);
        root.add(supplementsLabel, 0, 11);
        root.add(supplementChoice, 1, 11);
        root.add(payingCustomerLabel, 0, 12);
        root.add(payingCustomerChoice, 1, 12);
        root.add(accountNumberLabel, 0, 13);
        root.add(accountNumberTextField, 1, 13);
        root.add(cardType, 1, 14);

        root.add(submitButton, 1, 15);
    }

    public void editSupplementMode() 
    {
        homepage();

        Label supplementsLabel = createStyledLabel("Select supplement to edit:");
        supplementChoice = new ListView<>();
        supplementChoice.setStyle("-fx-background-color: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        Label supplementNameLabel = createStyledLabel("Supplement name:");
        supplementNameTextField = new TextField();
        supplementNameTextField.setStyle("-fx-background-color: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        
        Label supplementCostLabel = createStyledLabel("Supplement cost (weekly):");
        supplementCostTextField = new TextField();
        supplementCostTextField.setStyle("-fx-background-color: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        
        submitButton = createStyledButton("Submit");

        root.add(supplementsLabel, 0, 3);
        root.add(supplementChoice, 0, 4, 1, 12);
        root.add(supplementNameLabel, 1, 12);
        root.add(supplementNameTextField, 2, 12);
        root.add(supplementCostLabel, 1, 13);
        root.add(supplementCostTextField, 2, 13);
        root.add(submitButton, 2, 14);
    }

    public void editCustomerMode()
    {
        homepage();

        Label customersLabel = createStyledLabel("Select customer to edit:");
        customerChoice = new ListView<>();
        customerChoice.setStyle("-fx-background-color: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        Label customerTypeLabel = createStyledLabel("Type of customer:");
        typeOfCustomerTextField = new TextField();
        typeOfCustomerTextField.setDisable(true);
        typeOfCustomerTextField.setStyle("-fx-background-color: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        
        Label customerNameLabel = createStyledLabel("Customer name:");
        customersNameTextField = new TextField();
        customersNameTextField.setStyle("-fx-background-color: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        
        Label emailAddressLabel = createStyledLabel("Email Address:");
        emailAddressTextField = new TextField();
        emailAddressTextField.setStyle("-fx-background-color: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        
        Label streetNumberLabel = createStyledLabel("Street Number:");
        streetNumberTextField = new TextField();
        streetNumberTextField.setStyle("-fx-background-color: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        
        Label streetNameLabel = createStyledLabel("Street Name:");
        streetNameTextField = new TextField();
        streetNameTextField.setStyle("-fx-background-color: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        
        Label suburbLabel = createStyledLabel("Suburb:");
        suburbTextField = new TextField();
        suburbTextField.setStyle("-fx-background-color: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        
        Label postcodeLabel = createStyledLabel("Postcode:");
        postCodeTextField = new TextField();
        postCodeTextField.setStyle("-fx-background-color: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        
        oldSupplements = new ListView<>();
        oldSupplements.setStyle("-fx-background-color: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        oldSupplements.addEventFilter(MouseEvent.MOUSE_PRESSED, MouseEvent::consume);

                Label supplementsLabel = createStyledLabel("Select Supplement(s):");
        supplementChoice = new ListView<>();
        supplementChoice.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        supplementChoice.setStyle("-fx-background-color: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        payingCustomerLabel = createStyledLabel("Select Paying Customer:");
        payingCustomerChoice = new ComboBox<>();
        payingCustomerChoice.setPromptText("Select One");
        payingCustomerChoice.setStyle("-fx-background-color: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        accountNumberLabel = createStyledLabel("Bank Account Number:");
        accountNumberTextField = new TextField();
        accountNumberTextField.setStyle("-fx-background-color: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        
        submitButton = createStyledButton("Submit");

        // Arrange elements in the grid
        root.add(customersLabel, 0, 3);
        root.add(customerChoice, 0, 4, 1, 10);

        root.add(customerTypeLabel, 1, 3);
        root.add(typeOfCustomerTextField, 2, 3);

        root.add(customerNameLabel, 1, 4);
        root.add(customersNameTextField, 2, 4);

        root.add(emailAddressLabel, 1, 5);
        root.add(emailAddressTextField, 2, 5);

        root.add(streetNumberLabel, 1, 6);
        root.add(streetNumberTextField, 2, 6);

        root.add(streetNameLabel, 1, 7);
        root.add(streetNameTextField, 2, 7);

        root.add(suburbLabel, 1, 8);
        root.add(suburbTextField, 2, 8);

        root.add(postcodeLabel, 1, 9);
        root.add(postCodeTextField, 2, 9);

        Label oldSupplementsHeader = createStyledLabel("Your supplement(s):");
        root.add(oldSupplementsHeader, 1, 10);
        root.add(oldSupplements, 1, 11);

        root.add(supplementsLabel, 2, 10);
        root.add(supplementChoice, 2, 11);

        root.add(payingCustomerLabel, 1, 12);
        root.add(payingCustomerChoice, 2, 12);

        root.add(accountNumberLabel, 1, 13);
        root.add(accountNumberTextField, 2, 13);

        root.add(submitButton, 2, 14);
    }

    public void deleteSupplementMode()
    {
        homepage();

        Label supplementsLabel = createStyledLabel("Select to delete:");
        supplementChoice = new ListView<>();
        supplementChoice.setStyle("-fx-background-color: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        Label supplementsDetails = createStyledLabel("Supplement information:");
        infoPanelBox = new TextArea();
        infoPanelBox.setEditable(false);
        infoPanelBox.setStyle("-fx-control-inner-background: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        submitButton = createStyledButton("Submit");

        root.add(supplementsLabel, 0, 3);
        root.add(supplementChoice, 0, 4);
        root.add(supplementsDetails, 1, 3);
        root.add(infoPanelBox, 1, 4, 2, 1);
        root.add(submitButton, 1, 6);
    }

    public void deleteCustomerMode() 
    {
        homepage();

        Label customersLabel = createStyledLabel("Select to delete:");
        customerChoice = new ListView<>();
        customerChoice.setStyle("-fx-background-color: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        Label customersDetails = createStyledLabel("Customer information:");
        infoPanelBox = new TextArea();
        infoPanelBox.setEditable(false);
        infoPanelBox.setStyle("-fx-control-inner-background: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        submitButton = createStyledButton("Submit");

        root.add(customersLabel, 0, 3);
        root.add(customerChoice, 0, 4);
        root.add(customersDetails, 1, 3);
        root.add(infoPanelBox, 1, 4, 2, 1);
        root.add(submitButton, 1, 6);
    }

    public void magazineEdit() 
    {
        homepage();
        editButton.setDisable(true);

        Label editCheckHeader = createStyledLabel("Select a magazine to edit:");
        magazineChoice = new ComboBox<>();
        magazineChoice.setPromptText("Options");
        magazineChoice.setStyle("-fx-background-color: #F5E9DA; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        submitButton = createStyledButton("Submit");

        root.add(editCheckHeader, 1, 4);
        root.add(magazineChoice, 1, 5);
        root.add(submitButton, 1, 6);
    }

    
    /**
     * Helper method to create a styled button.
     * @param text The button text.
     * @return The styled Button object.
     */
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #FFB6C1; -fx-text-fill: #8B5E3C; "
                + "-fx-font-size: 16px; -fx-font-family: 'Comic Sans MS'; "
                + "-fx-border-color: #8B5E3C; -fx-border-radius: 15px; -fx-background-radius: 15px;");
        button.setPrefSize(200, 35);
        button.setFocusTraversable(false);
        return button;
    }

    /**
     * Helper method to create a lighter-styled button.
     * @param text The button text.
     * @return The styled Button object.
     */
    private Button createLightButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #FADADD; -fx-text-fill: black; "
                + "-fx-font-size: 16px; -fx-font-family: 'Comic Sans MS'; "
                + "-fx-border-color: #8B5E3C; -fx-border-radius: 15px; -fx-background-radius: 15px;");
        button.setPrefSize(200, 35);
        button.setFocusTraversable(false);
        return button;
    }

     /**
     * Helper method to create a styled label.
     * @param text The label text.
     * @return The styled Label object.
     */
    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 18px; -fx-font-family: 'Comic Sans MS'; -fx-text-fill: #8B5E3C;");
        return label;
    }

    // Getter methods for buttons and other components for use in the main application logic
    public Button getViewButton() { return viewButton; }
    public Button getCreateButton() { return createButton; }
    public Button getEditButton() { return editButton; }
    public Button getSubmitButton() { return submitButton; }
    public Button getAddMagazineButton() { return addMagazineButton; }
    public Button getLoadMagazineButton() { return loadMagazineButton; }
    public Button getSaveMagazineButton() { return saveMagazineButton; }
    public Button getAddSupplementButton() { return addSupplementButton; }
    public Button getAddCustomerButton() { return addCustomerButton; }
    public Button getEditSupplementButton() { return editSupplementButton; }
    public Button getEditCustomerButton() { return editCustomerButton; }
    public Button getDeleteSupplementButton() { return deleteSupplementButton; }
    public Button getDeleteCustomerButton() { return deleteCustomerButton; }
    public ListView<Supplement> getSupplementsView() { return supplementsView; }
    public ListView<Supplement> getSupplementChoice() { return supplementChoice; }
    public ListView<Supplement> getOldSupplements() { return oldSupplements; }
    public ListView<Customer> getCustomersView() { return customersView; }
    public ListView<Customer> getCustomerChoice() { return customerChoice; }
    public ComboBox<String> getMagazineChoice() { return magazineChoice; }
    public ComboBox<String> getTypeOfCustomerComboBox() { return typeOfCustomerComboBox; }
    public ComboBox<String> getCardType() { return cardType; }
    public ComboBox<PayingCustomer> getPayingCustomerChoice() { return payingCustomerChoice; }
    public TextArea getInfoPanelBox() { return infoPanelBox; }
    public TextField getMagazineNameTextField() { return magazineNameTextField; }
    public TextField getSupplementNameTextField() { return supplementNameTextField; }
    public TextField getSupplementCostTextField() { return supplementCostTextField; }
    public TextField getCustomersNameTextField() { return customersNameTextField; }
    public TextField getEmailAddressTextField() { return emailAddressTextField; }
    public TextField getStreetNumberTextField() { return streetNumberTextField; }
    public TextField getStreetNameTextField() { return streetNameTextField; }
    public TextField getSuburbTextField() { return suburbTextField; }
    public TextField getPostCodeTextField() { return postCodeTextField; }
    public TextField getAccountNumberTextField() { return accountNumberTextField; }
    public TextField getTypeOfCustomerTextField() { return typeOfCustomerTextField; }
    public Label getPayingCustomerLabel() { return payingCustomerLabel; }
    public Label getAccountNumberLabel() { return accountNumberLabel; }
    public Label getCurrentMagazine() { return currentMagazine; }
    public List<File> getSelectedFile() { return selectedFile; }
}