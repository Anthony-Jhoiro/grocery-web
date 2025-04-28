package fr.anthonyquere;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface defining the core functionality for a grocery shop application.
 * Implementations of this interface should provide methods for managing grocery items
 * and retrieving runtime information.
 */
public interface MyGroceryShop {

    /**
     * Retrieves all grocery items currently in the shop.
     *
     * @return a list of grocery items
     */
    List<WebGroceryItem> getGroceries();

    /**
     * Adds a new grocery item to the shop.
     *
     * @param name     the name of the grocery item
     * @param quantity the quantity of the grocery item
     * @param category the category of the grocery item
     */
    void addGroceryItem(String name, int quantity, String category);

    /**
     * Removes a grocery item from the shop by its name.
     *
     * @param name the name of the grocery item to remove
     */
    void removeGroceryItem(String name);

    /**
     * Retrieves runtime information about the application.
     *
     * @return a Runtime record containing current date, Java version, and OS name
     */
    Runtime getRuntime();

    /**
     * Record representing a grocery item in the web application.
     * Contains the item's name, quantity, and category.
     *
     * @param name     the name of the grocery item
     * @param quantity the quantity of the grocery item
     * @param category the category of the grocery item
     */
    record WebGroceryItem(
            String name,
            Integer quantity,
            String category
    ) {
    }

    /**
     * Record representing runtime information about the application.
     * Contains the current date, Java version, and operating system name.
     *
     * @param todayDate    the current date
     * @param javaVersion  the Java version running the application
     * @param osName       the operating system name
     */
    record Runtime(
            LocalDate todayDate,
            String javaVersion,
            String osName
    ) {
    }
}
