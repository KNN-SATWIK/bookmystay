import java.io.*;
import java.util.*;

/**
 * UC12 - Persistence & Recovery (Independent)
 * Version 12.0
 *
 * Demonstrates saving and restoring system state
 * using serialization and file storage.
 */

// ---------- Booking Record ----------
class BookingRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private String bookingId;
    private String roomType;

    public BookingRecord(String bookingId, String roomType) {
        this.bookingId = bookingId;
        this.roomType = roomType;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("ID: " + bookingId + " | Room: " + roomType);
    }
}

// ---------- System State ----------
class SystemState implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<BookingRecord> bookings;
    private Map<String, Integer> inventory;

    public SystemState(List<BookingRecord> bookings, Map<String, Integer> inventory) {
        this.bookings = bookings;
        this.inventory = inventory;
    }

    public List<BookingRecord> getBookings() {
        return bookings;
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }
}

// ---------- Persistence Service ----------
class PersistenceService {

    private final String FILE_NAME = "system_state.dat";

    // Save state
    public void save(SystemState state) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(state);
            System.out.println("\nState saved successfully.");

        } catch (IOException e) {
            System.out.println("ERROR: Failed to save state.");
        }
    }

    // Load state
    public SystemState load() {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) in.readObject();
            System.out.println("State loaded successfully.");
            return state;

        } catch (FileNotFoundException e) {
            System.out.println("No previous state found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ERROR: Corrupted data. Starting with safe defaults.");
        }

        // Safe fallback
        return new SystemState(new ArrayList<>(), new HashMap<>());
    }
}

// ---------- MAIN CLASS ----------
public class uc12 {

    public static void main(String[] args) {

        System.out.println("Hotel Booking System v12.0 - Persistence & Recovery\n");

        PersistenceService service = new PersistenceService();

        // Step 1: Load previous state
        SystemState state = service.load();

        List<BookingRecord> bookings = state.getBookings();
        Map<String, Integer> inventory = state.getInventory();

        // If first run, initialize defaults
        if (inventory.isEmpty()) {
            inventory.put("Single Room", 2);
            inventory.put("Double Room", 1);
            inventory.put("Suite Room", 1);
        }

        // Simulate new booking
        BookingRecord newBooking = new BookingRecord(
                "B" + (100 + bookings.size()),
                "Single Room"
        );

        bookings.add(newBooking);
        inventory.put("Single Room", inventory.get("Single Room") - 1);

        System.out.println("New booking added:");
        newBooking.display();

        // Display current state
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        // Step 2: Save state before exit
        service.save(new SystemState(bookings, inventory));
    }
}