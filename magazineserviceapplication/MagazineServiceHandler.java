/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magazineserviceapplication;

/**
 * @filename MagazineServiceHandler.java
 * @purpose This class handles the storage, retrieval, and management of Magazine objects in the system.
 *          It allows for adding, saving, loading, and retrieving magazines using serialization.
 * @date 04/10/2024
 * @author Zaina Shahid
 *
 * @assumptions:
 *  - This class is used as a centralized handler for magazine-related operations.
 *  - Magazines are stored as serialized objects in `.ser` files.
 *
 * @expected input:
 *  - Magazine names as `String` identifiers for adding, retrieving, saving, and loading.
 *
 * @expected output:
 *  - The class provides alerts on successful or failed operations.
 *  - Serialized files for magazines are created and loaded from the local file system.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.*;

public class MagazineServiceHandler 
{
    private Map<String , Magazine> MagazineMap; //map to store magazine 
    private AlertsHandler alert = new AlertsHandler();
    
    // default constructor 
    
    /**
     * Default Constructor.
     * Precondition: No parameters required.
     * Post condition: Initializes an empty magazine map.
     */
    public  MagazineServiceHandler ()
    {
        MagazineMap = new HashMap<>();
    }
    
    /**
     * Returns a list of all magazine names in the system.
     * Precondition: None.
     * Post condition: Returns a list containing all magazine names.
     * 
     * @return An ArrayList of magazine names.
     */
    public ArrayList<String> getAllMagazineNames() 
    {
        return new ArrayList<>(MagazineMap.keySet());
    }

    /**
     * Adds a new magazine to the system with the specified name.
     * Precondition: `magazineName` should be a unique name.
     * Post condition: Adds a new magazine to the map.
     * 
     * @param magazineName The name of the magazine to add.
     */
    public void addMagazine(String magazineName)
    {
        Magazine magazine = new Magazine();
        MagazineMap.put(magazineName, magazine);
    }
    
    
    /**
     * Retrieves a magazine object by its name.
     * Precondition: `magazineName` must exist in the map.
     * Post condition: Returns the magazine object if found, otherwise null.
     * 
     * @param magazineName The name of the magazine to retrieve.
     * @return The Magazine object if found, otherwise null.
     */
    public Magazine getMagazine(String magazineName)
    {
        return MagazineMap.get(magazineName);
    }
    
    /**
     * Checks if a magazine with the specified name exists in the system.
     * Precondition: None.
     * Post condition: Returns true if the magazine exists, false otherwise.
     * 
     * @param magazineName The name of the magazine to check.
     * @return true if the magazine exists, false otherwise.
     */ 
    public boolean compareMagazine(String magazineName)
    {
        return MagazineMap.containsKey(magazineName);
    }
    
     /**
     * Saves a magazine to a `.ser` file for persistent storage.
     * Precondition: `magazineName` should refer to an existing magazine in the map.
     * Post condition: Saves the magazine to a serialized file, alerts user of success or errors.
     * 
     * @param magazineName The name of the magazine to save.
     */
    public void saveMagazineToFile(String magazineName) {
        try {
            File file = new File(magazineName + ".ser");

            // Check if file exists
            if (file.exists() && !file.delete()) {
                alert.showAlert("Failed to delete the existing file. Proceeding with overwrite.");
            }

            // Writing to file
            try (FileOutputStream outputFile = new FileOutputStream(file);
                 ObjectOutputStream objectOut = new ObjectOutputStream(outputFile)) {

                objectOut.writeObject(MagazineMap.get(magazineName));
                alert.showAlert(magazineName + " has been saved successfully in our system");
                return;

            } catch (NotSerializableException e) {
                alert.showAlert("Serialization error: Ensure all referenced classes are Serializable - " + e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                alert.showAlert("I/O Error during saving: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {
            alert.showAlert("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Load magazine from .ser file
    
    /**
     * Loads a magazine from a `.ser` file into the system.
     * Precondition: The `.ser` file must exist for the specified magazine name.
     * Post condition: Loads the magazine into the map, or alerts the user if there is an error.
     * 
     * @param magazineName The name of the magazine to load.
     */
    public void loadMagazineFromFile(String magazineName) {
        try (FileInputStream inputFile = new FileInputStream(magazineName + ".ser");
             ObjectInputStream objectIn = new ObjectInputStream(inputFile)) {

            MagazineMap.put(magazineName, (Magazine) objectIn.readObject());
            alert.showAlert(magazineName + " has been loaded successfully in our system");

        } catch (FileNotFoundException ex) {
            alert.showAlert("File not found: " + magazineName + ".ser");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            alert.showAlert("Class not found error during loading: " + ex.getMessage());
            ex.printStackTrace();
        } catch (InvalidClassException ex) {
            alert.showAlert("Class version mismatch. Ensure all serialized classes are up-to-date: " + ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            alert.showAlert("I/O Error during loading: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    
    
}
