/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineserviceapplication;

/**
 * @filename Address.java
 * @purpose This class represents an address with attributes such as street number, street name, suburb, and postcode.
 * @date 05/10/2024
 * @author Zaina Shahid
 *
 * @assumptions:
 *  - Each attribute (street number, name, suburb, and postcode) is represented as a string.
 *  - No validation is enforced on the attributes; they are assumed to be set correctly by the caller.
 *
 * @expected input:
 *  - Street number, street name, suburb, and postcode details for the address.
 *
 * @expected output:
 *  - A string representation of the address.
 */

import java.io.Serializable;

public class Address implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    //declaring variables 
    private String m_streetNum; 
    private String m_streetName;
    private String m_suburb; 
    private String m_postcode;
    
    
    
    /**
     * Default Constructor.
     * Precondition: No parameters required.
     * Post condition: An Address object with default (empty) values is created.
     */
    public Address()
    {
        this.m_streetNum = "";
        this.m_streetName = "";
        this.m_suburb = "";
        this.m_postcode = ""; 
        
    }
    
     /**
     * Parameterized Constructor.
     * Precondition: Each parameter should be a valid string.
     * Post condition: An Address object is created with the given street number, name, suburb, and postcode.
     * @param m_streetNum The street number of the address.
     * @param m_streetName The street name of the address.
     * @param m_suburb The suburb name.
     * @param m_postcode The postal code.
     */ 
    public Address(String m_streetNum, String m_streetName,String m_suburb,String m_postcode)
    {
        this.m_streetNum = m_streetNum;
        this.m_streetName = m_streetName;
        this.m_suburb = m_suburb;
        this.m_postcode = m_postcode;
        
    }
    
    
    /**
     * Getter for street number.
     * Precondition: Address object exists.
     * Post condition: Returns the street number.
     * @return The street number of the address.
     */
    public String getStreetNumber() {
        return m_streetNum;
    }

     /**
     * Setter for street number.
     * Precondition: Street number should be a valid string.
     * Post condition: Updates the street number.
     * @param m_streetNum The new street number.
     */
    public void setStreetNumber(String m_streetNum) {
        this.m_streetNum = m_streetNum;
    }

     /**
     * Getter for street name.
     * Precondition: Address object exists.
     * Post condition: Returns the street name.
     * @return The street name of the address.
     */
    public String getStreetName() {
        return m_streetName;
    }

     /**
     * Setter for street name.
     * Precondition: Street name should be a valid string.
     * Post condition: Updates the street name.
     * @param m_streetName The new street name.
     */
    public void setStreetName(String m_streetName) {
        this.m_streetName = m_streetName;
    }

    /**
     * Getter for suburb.
     * Precondition: Address object exists.
     * Post condition: Returns the suburb.
     * @return The suburb of the address.
     */
    public String getSuburb() {
        return m_suburb;
    }

    /**
     * Setter for suburb.
     * Precondition: Suburb should be a valid string.
     * Post condition: Updates the suburb.
     * @param m_suburb The new suburb.
     */
    public void setSuburb(String m_suburb) {
        this.m_suburb = m_suburb;
    }

    /**
     * Getter for postcode.
     * Precondition: Address object exists.
     * Post condition: Returns the postcode.
     * @return The postcode of the address.
     */
    public String getPostcode() {
        return m_postcode;
    }

     /**
     * Setter for postcode.
     * Precondition: Postcode should be a valid string.
     * Post condition: Updates the postcode.
     * @param m_postcode The new postcode.
     */
    public void setPostcode(String m_postcode) {
        this.m_postcode = m_postcode;
    }

    
    /**
     * Overridden toString method.
     * Precondition: Address object exists.
     * Post condition: Returns a string representation of the full address.
     * @return The full address in "street number, street name, suburb, postcode" format.
     */
    @Override
    public String toString() {
        return m_streetNum + " " + m_streetName + " " + m_suburb + " " + m_postcode;
    }
    
    
    
   
    
}

