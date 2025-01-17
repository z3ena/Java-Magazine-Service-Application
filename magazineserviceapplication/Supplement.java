/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineserviceapplication;


/**
 * @filename Supplement.java
 * @purpose This class represents a magazine supplement with a name and weekly cost.
 * @date 04/10/2024
 * @author Zaina Shahid
 * 
 * @assumptions:
 *  - The supplement name is a valid string and meaningful.
 *  - Weekly cost is a non-negative floating-point number.
 * 
 * @expected input:
 *  - Supplement name (String) and weekly cost (float) for each instance.
 * 
 * @expected output:
 *  - Supplement details such as name and cost.
 */



import java.io.Serializable;
public class Supplement implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    
    // Name of the supplement
    private String sup_name;
    
    // Weekly cost of the supplement
    private float cost;
    
     /**
     * Default Constructor.
     * Precondition: No parameters required.
     * Post condition: A supplement object with default values is created.
     */
    public Supplement()
    {
        this.sup_name = "no name yet";
        this.cost = 0;
    }
    
    
    /**
     * Parameterized Constructor.
     * Precondition: Name must be valid and cost must be non-negative.
     * Post condition: A supplement object is created with the given name and cost.
     * @param sup_name The name of the supplement.
     * @param cost The weekly cost of the supplement.
     */
    public Supplement(String sup_name, float cost) 
    {
        this.sup_name = sup_name;
        this.cost = cost;
    }
    
    
    /**
     * Getter for supplement name.
     * Precondition: Supplement object exists.
     * Post condition: Returns the name of the supplement.
     * @return the supplement name.
     */
    public String getName() 
    {
        return sup_name;
    }

    /**
     * Setter for supplement name.
     * Precondition: The name must be a valid string (non-empty).
     * Post condition: Sets the name if valid, otherwise returns false.
     * @param sup_name The new name of the supplement.
     * @return true if the name is valid and set, false otherwise.
     */
    public boolean setName(String sup_name) 
    {

        if (sup_name.length() > 0) 
        {
            this.sup_name = sup_name.toLowerCase();
            return true;
        }
        return false;

    }

    /**
     * Getter for supplement cost.
     * Precondition: Supplement object exists.
     * Post condition: Returns the weekly cost of the supplement.
     * @return the weekly cost of the supplement.
     */
    public float getCost() 
    {
        return cost;
    }

    /**
     * Setter for supplement cost.
     * Precondition: The cost must be non-negative.
     * Post condition: Sets the cost if valid, otherwise returns false.
     * @param cost The new cost of the supplement.
     * @return true if the cost is valid and set, false otherwise.
     */
    public boolean setCost(float cost) 
    {

        if (cost >= 0) 
        {
            this.cost = cost;
            return true;
        }
        return false;
    }
    
    
    /**
     * Overridden toString method.
     * Precondition: Supplement object exists.
     * Post condition: Returns a string representation of the supplement, typically used for display.
     * @return The name of the supplement.
     */
    @Override
    public String toString() {
        return sup_name;
    }

}
    