/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineserviceapplication;

/**
 * @filename ViewController.java
 * @purpose This class manages the view logic for the Magazine Service Application. It interacts with the GUI and the
 *          MagazineServiceHandler to display information about supplements and customers, and calculate billing details.
 * @date 04/11/2024
 * @author Zaina Shahid
 */
import java.util.Locale;
import javafx.concurrent.Task;
import java.util.ArrayList;

public class ViewController 
{
    
    private MagazineServiceApplicationGUI m_gui;
    private MagazineServiceHandler magazinehandler;
    private AlertsHandler alert;
    private MagazineServiceApplication mainApp;
    private Magazine magazine;
    private String m_magazineName;

    
    /**
     * Parameterized Constructor.
     * Precondition: All parameters must be initialized and valid.
     * Postcondition: Initializes ViewController with references to the GUI, handler, alert system, main application, 
     *                and selected magazine.
     * 
     * @param m_gui The GUI object of the application.
     * @param magazinehandler The handler managing magazine data.
     * @param alert The alert handler for displaying messages.
     * @param mainApp The main application instance.
     * @param m_magazineName The name of the magazine currently being viewed.
     */

    public ViewController(MagazineServiceApplicationGUI m_gui, MagazineServiceHandler magazinehandler, AlertsHandler alert, MagazineServiceApplication mainApp, String m_magazineName)
    {
        this.m_gui = m_gui;
        this.magazinehandler = magazinehandler;
        this.alert = alert;
        this.mainApp = mainApp;
        this.m_magazineName = m_magazineName;
        this.magazine = magazinehandler.getMagazine(m_magazineName);
    }

   
    /**
     * Initializes the view mode for the application, populates supplement and customer lists, 
     * and sets up listeners for selection events.
     * Precondition: GUI, magazine, and main application objects must be initialized.
     * Postcondition: View mode is set up, and data from the selected magazine is displayed in the GUI.
     */
     public void initialize() 
     {
        m_gui.viewMode();
        m_gui.getSupplementsView().getItems().addAll(magazine.getSupplements());
        m_gui.getCustomersView().getItems().addAll(magazine.getCustomerList());

        m_gui.getSupplementsView().getSelectionModel().selectedItemProperty().addListener((ob, oldValue, newValue) -> {
            if (newValue != null) {
                m_gui.getCustomersView().getSelectionModel().clearSelection();
                showSupplementInfo(newValue);
            }
        });

        m_gui.getCustomersView().getSelectionModel().selectedItemProperty().addListener((ob, oldValue, newValue) -> {
            if (newValue != null) {
                m_gui.getSupplementsView().getSelectionModel().clearSelection();
                showCustomerInfo(newValue);
            }
        });

        m_gui.getCurrentMagazine().setText("Current Magazine: " + m_magazineName);
        mainApp.setupMainButtons();
    } 
    

    
     /**
     * Displays information about the selected supplement in the info panel.
     * Precondition: Supplement object must not be null.
     * Postcondition: Supplement details are displayed in the info panel.
     * 
     * @param supplement The selected supplement to display information for.
     */
    
    public void showSupplementInfo(Supplement supplement) 
    {
         String text = "Name: " + supplement.getName() + "\n"
                    + "-----------------------------------------------------------------------------------\n"
                    + "Weekly Cost: $" + String.format(Locale.US, "%.2f", supplement.getCost());

        m_gui.getInfoPanelBox().setText(text);
    }

    
    /**
     * Displays information about the selected customer in the info panel.
     * If the customer is a PayingCustomer, additional billing and associate customer information is shown.
     * Precondition: Customer object must not be null.
     * Postcondition: Customer details are displayed in the info panel.
     * 
     * @param customer The selected customer to display information for.
     */
     public void showCustomerInfo(Customer customer) {
        StringBuilder text = new StringBuilder();

        text.append("Name: ").append(customer.getName()).append("\n")
            .append("------------------------------------------------------------------------------------\n")
            .append("Address: ").append(customer.getAddress()).append("\n")
            .append("------------------------------------------------------------------------------------\n")
            .append("Email: ").append(customer.getEmail()).append("\n")
            .append("------------------------------------------------------------------------------------\n")
            .append("Supplements Subscribed To:\n");

        for (int i = 0; i < customer.getSupplement().size(); i++) {
            int count = i + 1;
            text.append(count).append(". ").append(customer.getSupplement().get(i).getName()).append("\n");
        }

        if (customer instanceof PayingCustomer) {
            PayingCustomer payingCustomer = (PayingCustomer) customer;

            text.append("------------------------------------------------------------------------------------\n")
                .append("Status: Paying Customer\n")
                .append("Payment Method: ").append(payingCustomer.getPaymentMethod()).append("\n")
                .append("------------------------------------------------------------------------------------\n")
                .append("Associate Customers:\n");

            for (Customer associateCustomer : payingCustomer.getAssociateCustomers()) {
                text.append(" - ").append(associateCustomer.getName()).append("\n");
            }

            text.append("------------------------------------------------------------------------------------\n");

            // Use multithreading to calculate and display the total cost
            calculateBillingInfo(payingCustomer, text);

        } else if (customer instanceof AssociateCustomer) {
            text.append("------------------------------------------------------------------------------------\n")
                .append("Status: Associate Customer\n");
            m_gui.getInfoPanelBox().setText(text.toString());
        }
    }
     
     
     // This method handles calculating billing info on a separate thread
     /**
     * Calculates the total monthly cost for a PayingCustomer and updates the UI with the billing information.
     * This method runs the calculation on a separate thread to avoid blocking the UI.
     * Precondition: PayingCustomer and text StringBuilder must be initialized.
     * Postcondition: The calculated billing information is displayed in the info panel.
     * 
     * @param payingCustomer The paying customer for whom to calculate billing.
     * @param text The StringBuilder to append billing information to.
     */
    private void calculateBillingInfo(PayingCustomer payingCustomer, StringBuilder text) 
    {
        // Create a task to calculate the total supplements cost
        Task<Double> billingTask = new Task<>() {
            @Override
            protected Double call() {
                return payingCustomer.calculateTotalSupplementsCost();
            }
        };

        // Update the UI with the billing information once the calculation is complete
        billingTask.setOnSucceeded(event -> {
            double totalCost = billingTask.getValue();
            text.append("Monthly Total Cost: $").append(String.format(Locale.US, "%.2f", totalCost));
            m_gui.getInfoPanelBox().setText(text.toString());
        });

        // In case of any error, handle it gracefully
        billingTask.setOnFailed(event -> {
            text.append("Error calculating billing information.");
            m_gui.getInfoPanelBox().setText(text.toString());
        });

        // Run the task in a background thread
        new Thread(billingTask).start();
    }
}