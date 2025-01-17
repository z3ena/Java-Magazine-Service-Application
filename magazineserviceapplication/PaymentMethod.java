/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineserviceapplication;

/**
 * @filename PaymentMethod.java
 * @purpose This class represents a payment method associated with a customer, which includes 
 *          details like card type and bank account number.
 * @date 05/10/2024
 * @author Zaina Shahid
 *
 * @assumptions:
 *  - `m_cardType` should be either "Credit Card" or "Debit Card".
 *  - `m_BankAccountNo` represents the bank account number associated with the payment method.
 *
 * @expected input:
 *  - Card type (either "Credit Card" or "Debit Card") and a valid bank account number.
 *
 * @expected output:
 *  - A string representation of the payment method, including card type and bank account number.
 */

import java.io.Serializable;

public class PaymentMethod implements Serializable 
        
{
    
    private static final long serialVersionUID = 1L;
    
    // can be either credit card / debit card with bank accountnum details 
    
    private String m_cardType;
    private int m_BankAccountNo;
    
    
     /**
     * Default Constructor.
     * Precondition: No parameters required.
     * Post condition: A PaymentMethod object with default values is created.
     */
    public PaymentMethod()
    {
        this.m_cardType = "";
        this.m_BankAccountNo = 0;
    
    }
    
    /**
     * Parameterized Constructor.
     * Precondition: `m_cardType` should be a valid string, and `m_BankAccountNo` should be a positive integer.
     * Post condition: A PaymentMethod object is created with the specified card type and bank account number.
     * @param m_cardType The type of card ("Credit Card" or "Debit Card").
     * @param m_BankAccountNo The bank account number associated with the payment method.
     */
    public PaymentMethod(String m_cardType, int m_BankAccountNo)
    {
        this.m_cardType = m_cardType;
        this.m_BankAccountNo = m_BankAccountNo;
        
    }
    
    
    
    /**
     * Getter for card type.
     * Precondition: PaymentMethod object exists.
     * Post condition: Returns the type of card associated with this payment method.
     * @return the type of card (either "Credit Card" or "Debit Card").
     */
    public String getCardType() {
        return m_cardType;
    }

    /**
     * Setter for card type.
     * Precondition: `m_cardType` should be a valid string (either "Credit Card" or "Debit Card").
     * Post condition: Updates the card type associated with this payment method.
     * @param m_cardType The new card type to be set.
     */
    public void setCardType(String m_cardType) {
        this.m_cardType = m_cardType;
    }

    /**
     * Getter for bank account number.
     * Precondition: PaymentMethod object exists.
     * Post condition: Returns the bank account number associated with this payment method.
     * @return the bank account number.
     */
    public int getAccountNo() {
        return m_BankAccountNo;
    }

    /**
     * Setter for bank account number.
     * Precondition: `m_BankAccountNo` should be a positive integer.
     * Post condition: Updates the bank account number associated with this payment method.
     * @param m_BankAccountNo The new bank account number to be set.
     */
    public void setAccountNo(int m_BankAccountNo) {
        this.m_BankAccountNo = m_BankAccountNo;
    }

    /**
     * Overridden toString method.
     * Precondition: PaymentMethod object exists.
     * Post condition: Returns a string representation of the payment method, 
     *                including the card type and bank account number.
     * @return A formatted string with card type and bank account number.
     */
    @Override
    public String toString() {
        return m_cardType + "\n" + m_BankAccountNo;
    }
    
    
    
}
