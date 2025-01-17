/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineserviceapplication;

/**
 * @filename AssociateCustomer.java
 * @purpose This class represents an associate customer, which extends the Customer class.
 *          An associate customer is a type of customer who is associated with a paying customer
 *          but does not directly pay for the subscription themselves.
 * @date 04/10/2024
 * @author Zaina Shahid
 *
 * @assumptions:
 *  - Associate customers are linked to a paying customer, who is responsible for their subscription payment.
 *  - The associate customer must have a valid name, email, and address.
 *
 * @expected input:
 *  - Customer name, email, and address for each associate customer.
 *
 * @expected output:
 *  - An instance of an associate customer with the specified details.
 */

public class AssociateCustomer extends Customer
{
      
   /**
     * Default Constructor.
     * Precondition: No parameters required.
     * Post condition: An associate customer object with default values is created.
     */
   public AssociateCustomer()
   {
       super();
   }
   
     /**
     * Parameterized Constructor.
     * Precondition: Name, email, and address must be valid.
     * Post condition: An associate customer object is created with the given name, email, and address.
     * @param newName The associate customer's name.
     * @param newEmail The associate customer's email.
     * @param newAddress The associate customer's address.
     */
  // Parameterized constructor
     public AssociateCustomer(String newName, String newEmail, Address newAddress) 
    {
        super(newName, newEmail, newAddress);
    }
   
    
}