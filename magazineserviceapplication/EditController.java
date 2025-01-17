/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineserviceapplication;

/**
 * @filename EditController.java
 * @purpose This class handles the editing functionalities for the Magazine Service Application. 
 *          It provides methods to add, edit, and delete supplements and customers, as well as 
 *          manage the customer types (Paying and Associate Customers) and their relationships.
 * @date 04/11/2024
 * @author Zaina Shahid
 * 
 * @assumptions
 *  - The magazine object is initialized before editing.
 *  - Supplement names and customer names are non-empty.
 *  - Paying customers have valid payment information.
 * 
 * @expected input:
 *  - Valid data for customer details, supplement details, and selection from GUI elements.
 * 
 * @expected output:
 *  - Updates to the magazine with new or edited supplements and customers, and visual feedback in the GUI.
 */

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.TextField;

public class EditController 
{
    
    private MagazineServiceApplicationGUI m_gui;
    private MagazineServiceHandler magazinehandler;
    private AlertsHandler alert;
    private MagazineServiceApplication mainApp;
    private Magazine magazine;
    private String m_magazineName;
    ViewController view;
    

    /**
     * Parameterized Constructor.
     * Precondition: All parameters must be valid and initialized.
     * Postcondition: Initializes EditController with references to the GUI, handler, alert system, main application,
     *                and selected magazine.
     * 
     * @param m_gui The GUI object of the application.
     * @param magazinehandler The handler managing magazine data.
     * @param alert The alert handler for displaying messages.
     * @param mainApp The main application instance.
     * @param m_magazineName The name of the magazine currently being edited.
     */
    public EditController(MagazineServiceApplicationGUI m_gui, MagazineServiceHandler magazinehandler, AlertsHandler alert, MagazineServiceApplication mainApp, String m_magazineName) 
    {
        this.m_gui = m_gui;
        this.magazinehandler = magazinehandler;
        this.alert = alert;
        this.mainApp = mainApp;
        this.m_magazineName = m_magazineName;
        this.magazine = magazinehandler.getMagazine(m_magazineName);
    }
    
    /**
     * Initializes the edit mode by setting up buttons and displaying the current magazine name.
     * Precondition: GUI, magazine, and main application objects must be initialized.
     * Postcondition: Edit mode is set up, and buttons are configured with appropriate actions.
     */
    public void initialize() 
    {
        m_gui.editMode();
        
        // Set up buttons for Edit Mode
        m_gui.getAddSupplementButton().setOnAction(e -> addSupplementMode());
        m_gui.getEditSupplementButton().setOnAction(e -> editSupplementMode());
        m_gui.getDeleteSupplementButton().setOnAction(e -> deleteSupplementMode());
        m_gui.getAddCustomerButton().setOnAction(e -> addCustomerMode());
        m_gui.getEditCustomerButton().setOnAction(e -> editCustomerMode());
        m_gui.getDeleteCustomerButton().setOnAction(e -> deleteCustomerMode());

        // Display current magazine in the header
        m_gui.getCurrentMagazine().setText("Currently editing: " + m_magazineName);

        // Navigation buttons
        m_gui.getViewButton().setOnAction(e -> mainApp.checkMagazineViewMode());
        m_gui.getCreateButton().setOnAction(e -> mainApp.switchToCreate());
        m_gui.getEditButton().setOnAction(e -> mainApp.checkMagazineEditMode());
    }

    
     /**
     * Sets up the Add Supplement mode in the GUI, allowing users to add a new supplement to the magazine.
     * Precondition: GUI elements and magazine object must be initialized.
     * Postcondition: New supplement is added to the magazine if validated.
     */
    
    private void addSupplementMode() 
    {
        m_gui.addSupplementMode();
        mainApp.setupMainButtons();

        magazine = magazinehandler.getMagazine(m_magazineName);

        // Set event handler for the submit button
        m_gui.getSubmitButton().setOnAction(e -> {
            String supplementName = m_gui.getSupplementNameTextField().getText();
            // If no supplement name provided
            if (!supplementName.trim().isEmpty()) {
                try {
                    float supplementCost = Float.parseFloat(m_gui.getSupplementCostTextField().getText());
                    Supplement supplement = new Supplement(supplementName, supplementCost);
                    magazine.addSupplement(supplement);
                    initialize();
                } catch (Exception ex) {
                    m_gui.getSupplementCostTextField().clear();
                    alert.showAlert("Please input only numbers for cost of supplement");
                }
            } else {
                m_gui.getSupplementNameTextField().clear();
                alert.showAlert("Supplement name cannot be empty");
            }
        });
    }
    
    
    /**
     * Sets up the Add Customer mode in the GUI, allowing users to add a new customer to the magazine.
     * Precondition: GUI elements and magazine object must be initialized.
     * Postcondition: New customer is added to the magazine if validated.
     */
    
    private void addCustomerMode() {
        m_gui.addCustomerMode();
         mainApp.setupMainButtons();

        magazine = magazinehandler.getMagazine(m_magazineName);

        addCustomerFillData(magazine);

        ArrayList<Supplement>[] supplements = new ArrayList[]{new ArrayList<>()};
        m_gui.getSupplementChoice().getSelectionModel().selectedItemProperty().addListener((ob, oldValue, newValue) -> {
            supplements[0] = new ArrayList<>(m_gui.getSupplementChoice().getSelectionModel().getSelectedItems());
        });

        // Set event handler for type of customer
        m_gui.getTypeOfCustomerComboBox().setOnAction(e -> {
            switch (m_gui.getTypeOfCustomerComboBox().getValue()) {
                case "Paying Customer":
                    setPayingCustomerVisible();
                    break;
                case "Associate Customer":
                    setAssociateCustomerVisible();
                    break;
            }
        });

        // Set event handler for the submit button
        m_gui.getSubmitButton().setOnAction(e -> {
            ArrayList<Boolean> validateList = new ArrayList<>();
            validateList = addCustomerValidation(validateList);

            // Check validation array
            Boolean isValidated = checkValidateArray(validateList);

            // All fields validated
            if (isValidated) {
                switch (m_gui.getTypeOfCustomerComboBox().getValue()) {
                    // If paying customer, add details to paying customer object
                    case "Paying Customer":
                        PayingCustomer payingCustomer = new PayingCustomer();
                        setCustomerSpecificData(payingCustomer, supplements);
                        payingCustomer.setPaymentMethod(new PaymentMethod
                             (
                                m_gui.getCardType().getValue(),
                                Integer.parseInt(m_gui.getAccountNumberTextField().getText())));
                        // Update magazine service
                        magazine.addCustomer(payingCustomer);
                        break;
                    // If associate customer, add details to associate customer object
                    case "Associate Customer":
                        AssociateCustomer associateCustomer = new AssociateCustomer();
                        setCustomerSpecificData(associateCustomer, supplements);
                        // Selection of paying customer
                        PayingCustomer selectedPayingCustomer = m_gui.getPayingCustomerChoice().getValue();
                        // Adding associate customer to selected paying customer
                        selectedPayingCustomer.addAssociateCustomer(associateCustomer);
                        // Update magazine service
                        magazine.addCustomer(associateCustomer);
                        break;
                }
                initialize();
            }
        });
    }
   
    
    
    /**
     * Sets up the edit mode for modifying supplements.
     * Precondition: GUI, mainApp, and magazine objects must be initialized.
     * Postcondition: Allows user to edit a selected supplement.
     */
    
    private void editSupplementMode() 
    {
        m_gui.editSupplementMode();
        mainApp.setupMainButtons();

        magazine = magazinehandler.getMagazine(m_magazineName);

        // Adding existing supplements
        m_gui.getSupplementChoice().getItems().addAll(magazine.getSupplements());

        // Monitor selection
        m_gui.getSupplementChoice().getSelectionModel().selectedItemProperty().addListener((ob, oldValue, newValue) -> {
            m_gui.getSupplementNameTextField().setText(newValue.getName());
            m_gui.getSupplementCostTextField().setText(String.valueOf(newValue.getCost()));
        });

        // Set event handler for the submit button
        m_gui.getSubmitButton().setOnAction(e -> {
            Supplement supplement = m_gui.getSupplementChoice().getSelectionModel().getSelectedItem();
            // If no selection
            if (supplement != null) {
                String supplementName = m_gui.getSupplementNameTextField().getText();
                // If no supplement name
                if (!supplementName.trim().isEmpty()) {
                    try {
                        supplement.setName(supplementName);
                        supplement.setCost(Float.parseFloat(m_gui.getSupplementCostTextField().getText()));
                        initialize();
                    } catch (Exception ex) {
                        m_gui.getSupplementCostTextField().clear();
                        alert.showAlert("Please input  only numbers for the cost of the supplement");
                    }
                } else {
                    m_gui.getSupplementNameTextField().clear();
                    alert.showAlert("Supplement name cannot be empty");
                }
            } else {
                alert.showAlert("Please select a supplement to edit");
            }
        });
    }

    
    
    /**
     * Sets up the edit mode for modifying customer details.
     * Precondition: GUI, mainApp, and magazine objects must be initialized.
     * Postcondition: Allows user to edit selected customer information.
     */
    
    private void editCustomerMode() {
        m_gui.editCustomerMode();
        mainApp.setupMainButtons();

        magazine = magazinehandler.getMagazine(m_magazineName);

        editCustomerFillData();

        ArrayList<Supplement>[] supplements = new ArrayList[]{new ArrayList<>()};
        m_gui.getSupplementChoice().getSelectionModel().selectedItemProperty().addListener((ob, oldValue, newValue) -> {
            supplements[0] = new ArrayList<>(m_gui.getSupplementChoice().getSelectionModel().getSelectedItems());
        });

        // Set text based on selected customer
        m_gui.getCustomerChoice().getSelectionModel().selectedItemProperty().addListener((ob, oldValue, newValue) -> {
            editCustomerSetField(newValue, magazine);
        });

        // Set event handler for the submit button
        m_gui.getSubmitButton().setOnAction(e -> {
            ArrayList<Boolean> validateList = new ArrayList<>();
            validateList = editCustomerValidation(validateList);
            // Check validation array
            Boolean isValidated = checkValidateArray(validateList);
            // All fields validated
            if (isValidated) {
                editCustomerSetData(supplements, magazine);
                initialize();
            }
        });
    }
    
    /**
     * Validates an ArrayList of Boolean values to ensure all entries are true.
     * Precondition: validateList should be initialized.
     * Postcondition: Returns true if all validations passed, false otherwise.
     * 
     * @param validateList The list of Boolean validation results.
     * @return true if all values are true, false if any value is false.
     */
    
    private Boolean checkValidateArray(ArrayList<Boolean> validateList) {
        Boolean isValidated = true;
        for (Boolean value : validateList) {
            if (!value) {
                isValidated = false;
                break;
            }
        }
        return isValidated;
    }

    /**
     * Sets up the delete mode for supplements.
     * Precondition: GUI and magazine objects must be initialized.
     * Postcondition: Allows user to delete a selected supplement, if no customers are subscribed to it.
     */
    private void deleteSupplementMode() {
    m_gui.deleteSupplementMode();
    mainApp.setupMainButtons();

    magazine = magazinehandler.getMagazine(m_magazineName);

    // Add existing supplements to list view
    m_gui.getSupplementChoice().getItems().setAll(magazine.getSupplements());

    // Optional: Display supplement info directly in an alert or a simple text box if needed
    m_gui.getSupplementChoice().getSelectionModel().selectedItemProperty().addListener((ob, oldValue, newValue) -> {
        if (newValue != null) {
            showSupplementInfoInPanel(newValue); 
        }
    });

    m_gui.getSubmitButton().setOnAction(e -> {
        Supplement supplement = m_gui.getSupplementChoice().getSelectionModel().getSelectedItem();
        
        if (supplement != null) {
            boolean isSubscribed = magazine.getCustomerList().stream()
                .anyMatch(customer -> customer.getSupplement().contains(supplement));

            if (isSubscribed) {
                alert.showAlert("You are not able to delete a supplement that has subscriptions");
            } else {
                magazine.removeSupplement(supplement);
                initialize(); // Refresh the view after deletion
            }
        } else {
            alert.showAlert("Please select a supplement to delete");
        }
       });
    }
    
     /**
     * Sets up the delete mode for customers.
     * Precondition: GUI and magazine objects must be initialized.
     * Postcondition: Allows user to delete a selected customer.
     */
    private void deleteCustomerMode() 
    {
    m_gui.deleteCustomerMode();
    mainApp.setupMainButtons();

    magazine = magazinehandler.getMagazine(m_magazineName);

    // Add existing customers to list view
    m_gui.getCustomerChoice().getItems().addAll(magazine.getCustomerList());

    // Monitor selection and display customer info directly
    m_gui.getCustomerChoice().getSelectionModel().selectedItemProperty().addListener((ob, oldValue, newValue) -> {
        if (newValue != null) {
            showBasicCustomerInfo(newValue);  // Call the simplified method to show customer info
        }
    });

    m_gui.getSubmitButton().setOnAction(e -> {
        Customer customer = m_gui.getCustomerChoice().getSelectionModel().getSelectedItem();
        // Check if customer is selected
        if (customer != null) {
            if (customer instanceof PayingCustomer) {
                PayingCustomer selectedPayingCustomer = (PayingCustomer) customer;
                // Check if paying customer has one or more associate customers
                if (selectedPayingCustomer.containsAssociateCustomer()) {
                    alert.showAlert("You are not able to delete a paying customer that has associate customer(s)");
                } else {
                    magazine.removeCustomer(customer);
                    initialize();
                }
            } else if (customer instanceof AssociateCustomer) {
                // Remove associate customer from paying customer
                deleteAssociateCustomerFromPayingCustomer(customer, magazine);
                magazine.removeCustomer(customer);
                initialize();
            }
        } else {
            alert.showAlert("Please select a customer to delete");
        }
    });
   }
    
     /**
     * Displays basic customer information in the info panel.
     * Precondition: Customer object is not null.
     * Postcondition: Customer information is displayed in the GUI's info panel.
     * 
     * @param customer The customer whose information is displayed.
     */
    
    private void showBasicCustomerInfo(Customer customer) {
    StringBuilder text = new StringBuilder();

    text.append("Name: ").append(customer.getName()).append("\n")
        .append("Email: ").append(customer.getEmail()).append("\n")
        .append("Address: ").append(customer.getAddress()).append("\n")
        .append("Supplements Subscribed To:\n");

    for (Supplement supplement : customer.getSupplement()) {
        text.append("- ").append(supplement.getName()).append("\n");
    }

    m_gui.getInfoPanelBox().setText(text.toString());
   }
    
     /**
     * Shows supplement information in the info panel.
     * Precondition: Supplement object is not null.
     * Postcondition: Supplement details are displayed in the GUI's info panel.
     * 
     * @param supplement The supplement whose information is displayed.
     */
    private void showSupplementInfoInPanel(Supplement supplement) 
    {
    String text = "Name: " + supplement.getName() + "\n"
                + "Weekly Cost: $" + String.format("%.2f", supplement.getCost());

    // Display the text in the info panel box instead of an alert
    m_gui.getInfoPanelBox().setText(text);
   }

    
    /**
     * Fills the customer data when adding a new customer.
     * Precondition: Magazine object should be initialized.
     * Postcondition: Adds existing supplements and paying customers to the selection lists.
     * 
     * @param magazine The magazine object to retrieve
     * */
     
    private void addCustomerFillData(Magazine magazine) 
    {
        m_gui.getTypeOfCustomerComboBox().getItems().addAll("Paying Customer", "Associate Customer");

        // Add supplements to list view
        m_gui.getSupplementChoice().getItems().addAll(magazine.getSupplements());

        // Create customer arraylist and add all customers
        ArrayList<Customer> customerList = new ArrayList<>();
        ArrayList<PayingCustomer> payingCustomerList = new ArrayList<>();
        customerList.addAll(magazine.getCustomerList());

        // Check if paying customer
        for (Customer customer : customerList) {
            if (customer instanceof PayingCustomer) {
                payingCustomerList.add((PayingCustomer) customer);
            }
        }
        m_gui.getPayingCustomerChoice().getItems().addAll(payingCustomerList);
        m_gui.getPayingCustomerChoice().setPromptText("Select One");
    }

    /**
     * Validates the fields when adding a new customer.
     * Precondition: GUI elements must be initialized.
     * Postcondition: Returns a list of validation results for each field.
     * 
     * @param validateList The list to store validation results.
     * @return The updated list with true for valid fields, false for invalid fields.
     */
    private ArrayList<Boolean> addCustomerValidation(ArrayList<Boolean> validateList) {
        // Validation for customer type field
        if (m_gui.getTypeOfCustomerComboBox().getSelectionModel().isEmpty()) {
            alert.showAlert("Please select type of customer");
            validateList.add(false);
        } else {
            validateList.add(true);
        }

        // Validation for customer name
        validateIfEmpty(m_gui.getCustomersNameTextField(), "Customer name cannot be empty ", validateList);

        // Validate email address
        if (isValidEmailAddress(m_gui.getEmailAddressTextField().getText())) {
            validateList.add(true);
        } else {
            alert.showAlert("Invalid email address");
            validateList.add(false);
        }

        // Validation for relevant fields
        validateIfEmpty(m_gui.getStreetNumberTextField(), "Street number cannot be empty ", validateList);
        validateIfEmpty(m_gui.getStreetNameTextField(), "Street name cannot be empty", validateList);
        validateIfEmpty(m_gui.getSuburbTextField(), "Suburb cannot be empty", validateList);
        validateIfEmpty(m_gui.getPostCodeTextField(), "Postcode cannot be empty", validateList);

        // Validation for supplement field
        if (m_gui.getSupplementChoice().getSelectionModel().isEmpty()) {
            alert.showAlert("Please select at least one supplement");
            validateList.add(false);
        } else {
            validateList.add(true);
        }

        String customer = m_gui.getTypeOfCustomerComboBox().getValue();
        // Check if paying customer
        if (customer != null && customer.equals("Paying Customer")) {
            try {
                Integer.parseInt(m_gui.getAccountNumberTextField().getText());
                validateList.add(true);
            } catch (Exception ex) {
                m_gui.getAccountNumberTextField().clear();
                alert.showAlert("Please input only numbers for account number");
                validateList.add(false);
            }
            if (m_gui.getCardType().getSelectionModel().isEmpty()) {
                alert.showAlert("Please select card type");
                validateList.add(false);
            } else {
                validateList.add(true);
            }
        }
        // Check if associate customer
        if (customer != null && customer.equals("Associate Customer")) {
            if (m_gui.getPayingCustomerChoice().getSelectionModel().isEmpty()) {
                alert.showAlert("Please select a paying customer");
                validateList.add(false);
            } else {
                validateList.add(true);
            }
        }
        return validateList;
    }
    

    /**
     * Fills in data when editing customer details.
     * Precondition: Magazine object should be initialized.
     * Postcondition: Fills customer and supplement choices in the GUI for editing.
     */
    
    private void editCustomerFillData() {
        // Adding customers to list view
        m_gui.getCustomerChoice().getItems().addAll(magazine.getCustomerList());

        // Adding supplements subscribed by respective customer
        m_gui.getSupplementChoice().getItems().addAll(magazine.getSupplements());

        // Adding all customers to list view
        ArrayList<Customer> customerList = new ArrayList<>();
        ArrayList<PayingCustomer> payingCustomerList = new ArrayList<>();
        customerList.addAll(magazine.getCustomerList());

        // Check for paying customer
        for (Customer customer : customerList) {
            if (customer instanceof PayingCustomer) {
                payingCustomerList.add((PayingCustomer) customer);
            }
        }

        m_gui.getPayingCustomerChoice().getItems().addAll(payingCustomerList);
        // Card type field
        m_gui.getCardType().getItems().addAll("Credit Card", "Debit Card");
    }
    
    /**
     * Sets fields in the GUI based on the selected customer.
     * Precondition: GUI and magazine objects should be initialized.
     * Postcondition: Populates customer fields in the GUI for editing.
     * 
     * @param newValue The selected customer.
     * @param magazine The magazine containing customer data.
     * */

    private void editCustomerSetField(Customer newValue, Magazine magazine) {
        if (newValue instanceof PayingCustomer) {
            setPayingCustomerVisible();
            m_gui.getTypeOfCustomerTextField().setText("Paying Customer");
            PayingCustomer payingCustomer = (PayingCustomer) newValue;
            m_gui.getAccountNumberTextField().setText(String.valueOf(payingCustomer.getPaymentMethod().getAccountNo()));
            m_gui.getCardType().setValue(payingCustomer.getPaymentMethod().getCardType());
        } else if (newValue instanceof AssociateCustomer) {
            setAssociateCustomerVisible();
            m_gui.getTypeOfCustomerTextField().setText("Associate Customer");
            editCustomerSetPayingCustomer(newValue, magazine);
        }
        m_gui.getCustomersNameTextField().setText(newValue.getName());
        m_gui.getEmailAddressTextField().setText(newValue.getEmail());
        m_gui.getStreetNumberTextField().setText(newValue.getAddress().getStreetNumber());
        m_gui.getStreetNameTextField().setText(newValue.getAddress().getStreetName());
        m_gui.getSuburbTextField().setText(newValue.getAddress().getSuburb());
        m_gui.getPostCodeTextField().setText(newValue.getAddress().getPostcode());
        m_gui.getOldSupplements().getItems().setAll(newValue.getSupplement());
    }

    
     /**
     * Finds the paying customer associated with an associate customer.
     * Precondition: Both customer and magazine objects should be initialized.
     * Postcondition: Sets the associated paying customer in the GUI.
     * 
     * @param newValue The associate customer.
     * @param magazine The magazine containing customer data.
     */
    private void editCustomerSetPayingCustomer(Customer newValue, Magazine magazine) {
        for (Customer customer : magazine.getCustomerList()) {
            if (customer instanceof PayingCustomer) {
                PayingCustomer payingCustomer = (PayingCustomer) customer;
                // Check for paying customer that has the associate customer
                if (payingCustomer.compareAssociateCustomer(newValue.getName())) {
                    // Set associated paying customer
                    m_gui.getPayingCustomerChoice().setValue(payingCustomer);
                }
            }
        }
    }
    
    /**
     * Validates fields when editing customer information.
     * Precondition: GUI elements must be initialized.
     * Postcondition: Returns a list of validation results for each field.
     * 
     * @param validateList The list to store validation results.
     * @return The updated list with true for valid fields, false for invalid fields.
     */

    private ArrayList<Boolean> editCustomerValidation(ArrayList<Boolean> validateList) {
        // If no customer selected
        if (m_gui.getCustomerChoice().getSelectionModel().getSelectedItem() != null) {

            // Validation for customer name
            validateIfEmpty(m_gui.getCustomersNameTextField(), "Customer name cannot be empty or contain only whitespaces", validateList);

            // Validate email address
            if (isValidEmailAddress(m_gui.getEmailAddressTextField().getText())) {
                validateList.add(true);
            } else {
                alert.showAlert("Invalid email address");
                validateList.add(false);
            }

            // Validation for relevant fields
            validateIfEmpty(m_gui.getStreetNumberTextField(), "Street number cannot be empty or contain only whitespaces", validateList);
            validateIfEmpty(m_gui.getStreetNameTextField(), "Street name cannot be empty or contain only whitespaces", validateList);
            validateIfEmpty(m_gui.getSuburbTextField(), "Suburb cannot be empty or contain only whitespaces", validateList);
            validateIfEmpty(m_gui.getPostCodeTextField(), "Postcode cannot be empty or contain only whitespaces", validateList);

            // Validation for supplement field
            if (m_gui.getSupplementChoice().getSelectionModel().isEmpty()) {
                alert.showAlert("Please select at least one supplement");
                validateList.add(false);
            } else {
                validateList.add(true);
            }

            // Check for paying customer type
            if (m_gui.getTypeOfCustomerTextField().getText().equals("Paying Customer")) {
                try {
                    Integer.parseInt(m_gui.getAccountNumberTextField().getText());
                    validateList.add(true);
                } catch (Exception ex) {
                    m_gui.getAccountNumberTextField().clear();
                    alert.showAlert("Please input numbers only for account number");
                    validateList.add(false);
                }
            }
        } else {
            alert.showAlert("Please select a customer to edit");
            validateList.add(false);
        }
        return validateList;
    }

    
    /**
     * Sets the customer data for the edited customer in the magazine.
     * Precondition: Customer, GUI, and magazine objects should be initialized.
     * Postcondition: Updates the magazine with the edited customer data.
     * 
     * @param supplements Array of selected supplements to assign to the customer.
     * @param magazine The magazine containing customer data.
     */
     private void editCustomerSetData(ArrayList<Supplement>[] supplements, Magazine magazine) {
        Customer customer = m_gui.getCustomerChoice().getSelectionModel().getSelectedItem();
        // If paying customer selected, update all fields
        if (m_gui.getTypeOfCustomerTextField().getText().equals("Paying Customer")) {
            PayingCustomer selectedPayingCustomer = (PayingCustomer) customer;
            setCustomerSpecificData(selectedPayingCustomer, supplements);
            selectedPayingCustomer.setPaymentMethod(new PaymentMethod(
                    m_gui.getCardType().getValue(),
                    Integer.parseInt(m_gui.getAccountNumberTextField().getText())));
        } // If associate customer selected, update all fields
        else if (m_gui.getTypeOfCustomerTextField().getText().equals("Associate Customer")) {
            AssociateCustomer selectedAssociateCustomer = (AssociateCustomer) customer;
            setCustomerSpecificData(selectedAssociateCustomer, supplements);
            PayingCustomer selectedPayingCustomer = m_gui.getPayingCustomerChoice().getValue();
            // To remove associate customer from paying customer and add to new
            if (!selectedPayingCustomer.compareAssociateCustomer(selectedAssociateCustomer.getName())) {
                deleteAssociateCustomerFromPayingCustomer(customer, magazine);
                selectedPayingCustomer.addAssociateCustomer(selectedAssociateCustomer);
            }
        }
    }
     
    
     /**
     * Sets general data for a customer, such as name, email, address, and subscribed supplements.
     * Precondition: Customer and GUI elements should be initialized.
     * Postcondition: Sets the provided data in the customer object.
     * 
     * @param customer The customer to set data for.
     * @param supplements Array of selected supplements to assign to the customer.
     */
    private void setCustomerSpecificData(Customer customer, ArrayList<Supplement>[] supplements) {
        customer.setName(m_gui.getCustomersNameTextField().getText());
        customer.setEmail(m_gui.getEmailAddressTextField().getText());
        customer.setAddress(new Address(
                m_gui.getStreetNumberTextField().getText(),
                m_gui.getStreetNameTextField().getText(),
                m_gui.getSuburbTextField().getText(),
                m_gui.getPostCodeTextField().getText()
        ));
        customer.setSupplement(supplements[0]);
    }
    
    /**
     * Removes an associate customer from a paying customer's list of associates.
     * Precondition: Selected customer and magazine objects should be initialized.
     * Postcondition: The associate customer is removed from the paying customer's list.
     * 
     * @param selectedCustomer The associate customer to remove.
     * @param magazine The magazine containing customer data.
     */

    private void deleteAssociateCustomerFromPayingCustomer(Customer selectedCustomer, Magazine magazine) {
        for (Customer customer : magazine.getCustomerList()) {
            if (customer instanceof PayingCustomer) {
                PayingCustomer payingCustomer = (PayingCustomer) customer;
                if (payingCustomer.compareAssociateCustomer(selectedCustomer.getName())) {
                    // Delete associate customer from paying customer
                    payingCustomer.removeAssociateCustomer(selectedCustomer);
                }
            }
        }
    }

     /**
     * Validates an email address using a regular expression.
     * Precondition: Email address should be a non-null string.
     * Postcondition: Returns true if the email address is valid, false otherwise.
     * 
     * @param emailAddress The email address to validate.
     * @return true if the email address matches the pattern, false otherwise.
     */
     
    private static Boolean isValidEmailAddress(String emailAddress) 
    {
        Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$");
        Matcher matcher = pattern.matcher(emailAddress);
        return matcher.matches();
    }

     /**
     * Checks if a TextField is empty and displays an alert if it is.
     * Precondition: TextField, errorMessage, and validateList must be initialized.
     * Postcondition: Adds a validation result to validateList and shows an alert if empty.
     * 
     * @param textField The TextField to check.
     * @param errorMessage The error message to display if the field is empty.
     * @param validateList The list to store the validation result.
     */
    private void validateIfEmpty(TextField textField, String errorMessage, ArrayList<Boolean> validateList) {
        if (textField.getText().trim().isEmpty()) {
            textField.clear();
            alert.showAlert(errorMessage);
            validateList.add(false);
        } else {
            validateList.add(true);
        }
    }
   
     /**
     * Sets the visibility of fields specific to paying customers.
     * Precondition: GUI elements must be initialized.
     * Postcondition: Shows fields relevant to paying customers and hides associate customer fields.
     */
    private void setPayingCustomerVisible() {
      if (m_gui != null) {
        if (m_gui.getPayingCustomerLabel() != null) {
            m_gui.getPayingCustomerLabel().setVisible(false);
        }
        if (m_gui.getPayingCustomerChoice() != null) {
            m_gui.getPayingCustomerChoice().setVisible(false);
        }
        if (m_gui.getAccountNumberLabel() != null) {
            m_gui.getAccountNumberLabel().setVisible(true);
        }
        if (m_gui.getAccountNumberTextField() != null) {
            m_gui.getAccountNumberTextField().setVisible(true);
        }
        if (m_gui.getCardType() != null) {
            m_gui.getCardType().setVisible(true);
        }
      }
    }

    /**
     * Sets the visibility of fields specific to associate customers.
     * Precondition: GUI elements must be initialized.
     * Postcondition: Shows fields relevant to associate customers and hides paying customer fields.
     */
     private void setAssociateCustomerVisible() {
       if (m_gui != null) {
        if (m_gui.getPayingCustomerLabel() != null) {
            m_gui.getPayingCustomerLabel().setVisible(true);
        }
        if (m_gui.getPayingCustomerChoice() != null) {
            m_gui.getPayingCustomerChoice().setValue(null);
            m_gui.getPayingCustomerChoice().setVisible(true);
        }
        if (m_gui.getAccountNumberLabel() != null) {
            m_gui.getAccountNumberLabel().setVisible(false);
        }
        if (m_gui.getAccountNumberTextField() != null) {
            m_gui.getAccountNumberTextField().clear();
            m_gui.getAccountNumberTextField().setVisible(false);
        }
        if (m_gui.getCardType() != null) {
            m_gui.getCardType().setValue(null);
            m_gui.getCardType().setVisible(false);
         }
      }
    }
    


}
