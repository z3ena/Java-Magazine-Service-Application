/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineserviceapplication;


/**
 * @filename PayingCustomer.java
 * @purpose This class represents a paying customer, which extends the Customer class and includes
 *          additional features like payment method and management of associate customers.
 * @date 04/10/2024
 * @author Zaina Shahid
 *
 * @assumptions:
 *  - Each paying customer may have zero or more associate customers.
 *  - The payment method should be either "Credit" or "Debit".
 *  - The associate customers are managed in a thread-safe manner to support concurrent access.
 *
 * @expected input:
 *  - Customer name, email, address, and a valid payment method.
 *
 * @expected output:
 *  - Ability to retrieve, add, or remove associate customers and calculate the total cost of all
 *    associated supplements.
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PayingCustomer extends Customer 
{
    // Payment method object for the paying customer
    private PaymentMethod paymentMethod;
    
    
    // List of associate customers whose subscriptions 
    // are paid for by this paying customer
    private final List<Customer> associateCustomers;
    
    
    /**
     * Default Constructor.
     * Precondition: No parameters required.
     * Post condition: A paying customer object with default values is created.
     */
    public PayingCustomer()
    {
        super(); // Call the superclass constructor
        this.paymentMethod = new PaymentMethod();
        this.associateCustomers = Collections.synchronizedList(new ArrayList<>());
        
    }

    
     /**
     * Parameterized Constructor.
     * Precondition: Name, email, and payment method must be valid strings.
     * Post condition: A paying customer object is created with the given details.
     * @param m_name Customer's name.
     * @param m_email Customer's email.
     * @param PaymentMethod Payment method (credit/debit).
     * @param BankAccountNum Bank Account Details 
     */
    public PayingCustomer(String m_name, String m_email,Address address,PaymentMethod paymentMethod) 
    {
        super(m_name, m_email, address);
        this.paymentMethod = paymentMethod;
        this.associateCustomers = Collections.synchronizedList(new ArrayList<>());
    }

    
    /**
     * Getter for payment method.
     * Precondition: PayingCustomer object exists.
     * Post condition: Returns the payment method.
     * @return payment method.
     */
    public PaymentMethod getPaymentMethod() 
    {
        return paymentMethod;
    }
    
     /**
     * Setter for payment method.
     * Precondition: The provided payment method must be valid.
     * Post condition: Sets the payment method for this customer.
     * @param paymentMethod The new payment method.
     */
    public void setPaymentMethod(PaymentMethod paymentMethod) 
    {
        this.paymentMethod = paymentMethod;
        
    }

  
     /**
     * Helper method.
     * Precondition: PayingCustomer object exists.
     * Post condition: checks validity.
     * 
     */
   
    private void validatePaymentMethod(String paymentMethod) 
    {
        if (!paymentMethod.equalsIgnoreCase("credit") && !paymentMethod.equalsIgnoreCase("debit")) {
            throw new IllegalArgumentException("Payment method must be either 'credit' or 'debit'");
        }
    }
    
    /**
     * Getter for associate list.
     * Precondition: PayingCustomer object exists.
     * Post condition: Returns the list of associate customers.
     * @return list of associates.
     */
    public List<Customer> getAssociateCustomers() 
    {
        synchronized (associateCustomers) 
        {
            return new ArrayList<>(associateCustomers); //return copy for thread safety 
        }
    }
    
    
    // Setter for arraylist of associate customers
    public void setAssociateCustomers(ArrayList<Customer> associateCustomers) 
    {
        synchronized (associateCustomers) 
        {
        // Clear the current list and add all elements from the new list
        associateCustomers.clear();
        associateCustomers.addAll(associateCustomers);
        }
    }
    
     
    
    /**
     * Adds an associate customer.
     * Precondition: The associate customer must not already be in the list.
     * Post condition: Adds the associate customer to the list.
     * @param customer The associate customer to add.
     */

    public synchronized  void addAssociateCustomer(Customer customer) 
    {
        associateCustomers.add(customer);
    }
    
   
    /**
     * Removes an associate customer.
     * Precondition: The associate customer must exist in the list.
     * Post condition: Removes the associate customer from the list.
     * @param customer The associate customer to remove.
     */
    public synchronized  void removeAssociateCustomer(Customer customer)
    {
        associateCustomers.remove(customer);
    }
    
    
     /**
     * Checks if an associate customer with a given name exists.
     * Precondition: PayingCustomer and associateCustomers exist.
     * Post condition: Returns true if an associate customer with the given name is found.
     * @param associateCustomerName The name of the associate customer.
     * @return true if the associate customer exists, false otherwise.
     */
    public boolean compareAssociateCustomer(String associateCustomerName) {
       synchronized (associateCustomers){
        for (Customer customer : associateCustomers) 
        {
            // Check for associate customer only
            if (customer instanceof AssociateCustomer) 
            {
                // Casting to associate customer
                AssociateCustomer associateCustomer = (AssociateCustomer) customer;
                // Check same name return true
                if (associateCustomer.getName().equals(associateCustomerName)) {
                    return true;
                }
            }
        }
        return false;
       }
    }

    /**
     * Checks if this paying customer has any associate customers.
     * Precondition: PayingCustomer object exists.
     * Post condition: Returns true if there is at least one associate customer.
     * @return true if an associate customer exists, false otherwise.
     */
    public boolean containsAssociateCustomer() {
      
       synchronized (associateCustomers)
       {
        
        for (Customer customer : associateCustomers) {
            // Check for associate customer and return true if found
            if (customer instanceof AssociateCustomer) {
                return true;
            }
        }
        return false;
       }
    }

    /**
     * Calculates the total supplement cost for this paying customer and all associate customers.
     * Precondition: Supplements should be assigned to customers and associate customers.
     * Post condition: Returns the total monthly cost of all supplements for this paying customer and associates.
     * @return Total monthly supplement cost.
     */
    public double calculateTotalSupplementsCost() {
        double totalCost = 0.0;

        // Calculate the cost of supplements for the main paying customer
        totalCost += calculateSupplementsCost(this);

        synchronized (associateCustomers)
        {
        // Calculate the cost of supplements for each associate customer
        for (Customer associateCustomer : associateCustomers) {
            if (associateCustomer instanceof AssociateCustomer) {
                totalCost += calculateSupplementsCost(associateCustomer);
            }
        }
        
        }
        return totalCost;
        
    }

    /**
     * Helper method to calculate the monthly cost of supplements for a given customer.
     * Precondition: Customer has supplements assigned.
     * Post condition: Returns the total monthly cost of supplements for the customer.
     * @param customer The customer whose supplement cost is calculated.
     * @return Monthly supplement cost.
     */
    private double calculateSupplementsCost(Customer customer) {
        double cost = 0.0;

        synchronized (customer.getSupplement()){
        for (Supplement supplement : customer.getSupplement()) {
            // Monthly = weeklycost x 4
            cost += (supplement.getCost() * 4);
        }
        
        }
        return cost;
    }
  
}
