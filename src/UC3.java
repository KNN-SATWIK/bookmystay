/**
 * UseCase3InventorySetup
 *
 * This class demonstrates centralized room inventory management using HashMap.
 * It replaces scattered availability variables with a single data structure.
 *
 * The RoomInventory class encapsulates all inventory-related operations,
 * ensuring consistency and scalability.
 *
 * @author Satwik
 * @version 3.1
 */

import java.util.HashMap;
import java.util.Map;

// Inventory Management Class
class RoomInventory {

    // HashMap to store room type -> availability
    private Map<String, Integer> inventory;

    // Constructor - initialize inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        // Initial room availability
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Get availability of a specific room type
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability (increase/decrease)
    public void updateAvailability(String roomType, int change) {
        int current = inventory.getOrDefault(roomType, 0);
        int updated = current + change;

        if (updated < 0) {
            System.out.println("Cannot reduce below zero for " + roomType);
        } else {
            inventory.put(roomType, updated);
        }
    }

    // Display full inventory
    public void displayInventory() {
        System.out.println("Current Room Inventory:\n");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> Available: " + entry.getValue());
        }
    }
}

// Main Application Class
public class UC3 {

    /**
     * Main method - Entry point
     */
    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Welcome to Book My Stay App");
        System.out.println("   Hotel Booking System v3.1");
        System.out.println("=====================================\n");

        // Initialize centralized inventory
        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        // Demonstrate updates
        System.out.println("\nUpdating Inventory...\n");

        inventory.updateAvailability("Single Room", -2); // booking
        inventory.updateAvailability("Suite Room", +1);  // new room added

        // Display updated inventory
        inventory.displayInventory();

        System.out.println("\nApplication executed successfully.");
    }
}