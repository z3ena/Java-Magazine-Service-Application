/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineserviceapplication;

/**
 * @filename Customer.java
 * @purpose This is the superclass representing a customer, which serves as a base class for 
 *          specific customer types, such as PayingCustomer and AssociateCustomer.
 * @date 04/10/2024
 * @author Zaina Shahid
 *
 * @assumptions:
 *  - Each customer must have a unique ID, name, email, and address.
 *  - The email is expected to be a valid string format.
 *  - Supplements associated with a customer are valid and have non-negative costs.
 *
 * @expected input:
 *  - Customer name, email, address, and a list of subscribed supplements.
 *
 * @expected output:
 *  - Customer details, including ID, name, email, address, and subscribed supplements.
 */

import java.io.Serializable;
import java.util.ArrayList;


public class Customer implements Serializable
{
    private static final long serialVersionUID = 1L;
    // static counter for customerID
    private static int IDcounter = 1;
    
    // declaring variables for customer attributes 
    private int m_customerID;
    private String m_name;
    private String m_email;
    private Address address;
    
    // List of supplements subscribed to by the customer
    private ArrayList<Supplement> supplements;
    
    
     /**
     * Default Constructor
     * Precondition: No parameters required.
     * Post condition: A customer object with default values is created.
     * 
     */
    public Customer()
    {
        this.m_customerID = IDcounter++;  // Auto-increment ID for each new customer
        this.m_name = "no name yet";
        this.m_email = "noemail@somthing.com";
        this.address = new Address();
        this.supplements = new ArrayList<>();  // Empty supplement list
    }
    
    
     /**
     * Parameterized Constructor 
     * Precondition: Customer's name and email should be valid strings.
     * Post condition: A customer object is created with the provided name and email.
     * @param m_name Customer's name.
     * @param m_email Customer's email.
     * @param address address object of the customer 
     * 
     * 
     */
    public Customer(String m_name, String m_email, Address address)
    {
        this.m_customerID = IDcounter++;
        this.m_name = m_name.toLowerCase();
        this.m_email = m_email.toLowerCase();
        this.address = address;
        this.supplements = new ArrayList<>(); // Initializes an empty list of supplements
   
    }
    
    
    /**
     * Getter for customer ID.
     * Precondition: Customer object exists.
     * Post condition: Returns the unique ID of the customer.
     * @return customer ID.
     */
    public int getCustomerId()
    {
        return m_customerID;
    }

    
    /**
     * Getter for customer name.
     * Precondition: Customer object exists.
     * Post condition: Returns the name of the customer.
     * @return customer's name.
     */
    public String getName() 
    {
        return m_name;
    }
    

    /**
     * Setter for customer name.
     * Precondition: Name must be a valid string (non-null and non-empty).
     * Post condition: If valid, updates the customer's name.
     * @param m_name the customer's new name.
     * @return true if name is updated, false if invalid.
     */
    public boolean setName(String m_name) 
    {
        if (m_name != null && m_name.length() > 0) {
            this.m_name = m_name.toLowerCase();
            return true;
        }
        return false;
    }
    
    
    /**
     * Getter for customer email.
     * Precondition: Customer object exists.
     * Post condition: Returns the email of the customer.
     * @return customer's email.
     */
    public String getEmail() 
    {
        return m_email;
    }

    
    /**
     * Setter for customer email.
     * Precondition: Email must be a valid string (non-null and non-empty).
     * Post condition: If valid, updates the customer's email.
     * @param m_email the customer's new email.
     * @return true if email is updated, false if invalid.
     */
    public boolean setEmail(String m_email) {
        if (m_email != null && m_email.length() > 0) {
            this.m_email = m_email.toLowerCase();
            return true;
        }
        return false;
    }

    
    /**
     * Compares two customers by name and email.
     * Precondition: Both customer objects must be initialized.
     * Post condition: Returns true if both customers have the same name and email, otherwise false.
     * @param obj the object to compare.
     * @return true if both customers are the same, false otherwise.
     */
    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj) return true;
        if (!(obj instanceof Customer)) 
        {   
            return false;
        }
        else
        {
        Customer customer = (Customer) obj;
        return this.m_name.equals(customer.getName()) && this.m_email.equals(customer.getEmail());
        }
    }

    
    /**
     * Getter for supplements.
     * Precondition: Customer object exists.
     * Post condition: Returns the list of supplements the customer has subscribed to.
     * @return list of supplements.
     */
     public ArrayList<Supplement> getSupplement() 
    {
        return supplements;
    }

     
    /**
     * Adds a supplement to the customer's subscription list.
     * Precondition: Supplement must be valid and not null.
     * Post condition: Adds the supplement to the list.
     * @param supplement the supplement to add.
     */ 
    public void addSupplement(Supplement supplement) 
    {
        supplements.add(supplement);
    }
    
    /**
     * Setter for list of supplements.
     * Precondition: Supplement list must be valid (non-null).
     * Post condition: Sets the customer's supplement list.
     * @param supplements The list of supplements to set.
     */
    public void setSupplement(ArrayList<Supplement> supplements) 
    {
        this.supplements = supplements;
    }

    
    /**
     * Removes a supplement from the customer's subscription list.
     * Precondition: Supplement must be in the customer's list.
     * Post condition: Removes the specified supplement from the list.
     * @param supplement The supplement to remove.
     */
    public void removeSupplement(Supplement supplement) 
    {
        this.supplements.remove(supplement);
    }
    
    
    /**
     * Getter for customer address.
     * Precondition: Customer object exists.
     * Post condition: Returns the customer's address.
     * @return Customer's address.
     */
    public Address getAddress() {
        return address;
    }

     /**
     * Setter for customer address.
     * Precondition: Address must be a valid Address object.
     * Post condition: Updates the customer's address.
     * @param newAddress The new address to set.
     */
    public void setAddress(Address newAddress) {
        address = newAddress;
    }
    
    /**
     * Overridden toString method.
     * Precondition: Customer object exists.
     * Post condition: Returns a string representation of the customer's name.
     * @return Customer's name.
     */

    @Override
    public String toString() {
        return m_name;
    }
    
    
   
    
    /**
     * Calculates the monthly cost of the supplements for the customer.
     * Precondition: Supplements should be initialized.
     * Post condition: Returns the total monthly cost of supplements.
     * @return total monthly cost.
     */
    public float calculateMonthlyCost() 
    {
        float totalCost = 0;
        for (Supplement supplement : supplements) {
            totalCost += supplement.getCost() * 4; // 4 weeks in a month
        }
        return totalCost;
    }
}
 