/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineserviceapplication;

/**
 * @filename Magazine.java
 * @purpose This class represents a magazine, which contains a title, weekly cost, 
 *          and lists of supplements and customers.
 * @date 05/10/2024
 * @author Zaina Shahid
 * 
 * @assumptions
 *  - Each magazine has a unique title.
 *  - Weekly cost is a positive floating-point number.
 *  - Supplements and customers are managed in separate lists.
 * 
 * @expected input:
 *  - Valid supplements and customers to add to the lists.
 * 
 * @expected output:
 *  - Information about the magazine, its supplements, and customer details.
 */



import java.io.Serializable;
import java.util.ArrayList;

public class Magazine implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    private String m_title;
    private float weeklyCost;
    
    // List of available supplements in the magazine
    private ArrayList<Supplement> supplementList;
    
    // List of customers subscribed to the magazine
    private ArrayList<Customer> customerList;
    
    
    /**
     * Default Constructor.
     * Precondition: No parameters required.
     * Post condition: A magazine object with default values is created.
     */
    public Magazine()
    {
        this.supplementList = new ArrayList<>();  // Initialize empty list of supplements
        this.customerList = new ArrayList<>();  // Initialize empty list of customers
    }
    
    
    /**
     * Parameterized Constructor.
     * Precondition: Title and cost must be valid.
     * Post condition: A magazine object is created with the given title and weekly cost.
     * @param m_title The title of the magazine.
     * @param weeklyCost The weekly cost of the magazine.
     */
    public Magazine(String m_title, float weeklyCost)
    {
        this.m_title = m_title;
        this.weeklyCost = weeklyCost;
        this.supplementList = new ArrayList<>();  // Initialize empty list of supplements
        this.customerList = new ArrayList<>();  // Initialize empty list of customers

        
    }
    
    
    
    /**
     * Getter for magazine title.
     * Precondition: Magazine object exists.
     * Post condition: Returns the title of the magazine.
     * @return the title of the magazine.
     */
    public String getTitle() 
    {
        return m_title;
    }

    
    /**
     * Getter for weekly cost.
     * Precondition: Magazine object exists.
     * Post condition: Returns the weekly cost of the magazine.
     * @return the weekly cost of the magazine.
     */
    public float getWeeklyCost() 
    {
        return weeklyCost;
    }
    
    
    /**
     * Getter for the list of supplements.
     * Precondition: Magazine object exists.
     * Post condition: Returns the list of supplements.
     * @return the list of supplements.
     */
    public ArrayList<Supplement> getSupplements()
    {
        return supplementList;
    }
    
    
    /**
     * Adds a supplement to the magazine's supplement list.
     * Precondition: Supplement must be valid and not null.
     * Post condition: Adds the supplement to the magazine's list.
     * @param supplement The supplement to add.
     */
    public void addSupplement(Supplement supplement) 
    {
        supplementList.add(supplement);
    }
    
    
    // Removing a single supplement from arraylist of supplement
    public void removeSupplement(Supplement supplement) {
        supplementList.remove(supplement);
    }


    
    /**
     * Getter for the list of customers.
     * Precondition: Magazine object exists.
     * Post condition: Returns the list of customers.
     * @return the list of customers.
     */
    public ArrayList<Customer> getCustomerList() 
    {
        return customerList;
    }

    
    /**
     * Adds a customer to the magazine's customer list.
     * Precondition: Customer must be valid and not null.
     * Post condition: Adds the customer to the magazine's list.
     * @param customer The customer to add.
     */
    public void addCustomer(Customer customer) 
    {
        customerList.add(customer);
    }

    /**
     * Removes a customer from the magazine's customer list.
     * Precondition: Customer exists in the list.
     * Post condition: The customer is removed from the list if present.
     * @param customer The customer to remove.
     */
    public void removeCustomer(Customer customer) {
        customerList.remove(customer);
    }

    
    
}