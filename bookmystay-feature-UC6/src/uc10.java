import java.util.*;

/**
 * UC10 - Booking Cancellation & Rollback (Independent)
 * Version 10.0
 *
 * Handles safe cancellation of bookings with rollback logic
 * using stack-based tracking and controlled state updates.
 */

// ---------- Booking Record ----------
class BookingRecord {

    private String bookingId;
    private String roomType;
    private String roomId;

    public BookingRecord(String bookingId, String roomType, String roomId) {
        this.bookingId = bookingId;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }
}

// ---------- Cancellation Service ----------
class CancellationService {

    // Active bookings (bookingId -> record)
    private Map<String, BookingRecord> activeBookings;

    // Inventory (roomType -> count)
    private Map<String, Integer> inventory;

    // Rollback stack (recently released room IDs)
    private Stack<String> rollbackStack;

    public CancellationService() {

        activeBookings = new HashMap<>();
        rollbackStack = new Stack<>();

        inventory = new HashMap<>();
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 0);
        inventory.put("Suite Room", 0);

        // Simulated confirmed bookings
        activeBookings.put("B101", new BookingRecord("B101", "Single Room", "SR-201"));
        activeBookings.put("B102", new BookingRecord("B102", "Double Room", "DR-301"));
    }

    // Cancel booking
    public void cancelBooking(String bookingId) {

        System.out.println("\nCancellation Request: " + bookingId);

        // Step 1: Validate existence
        if (!activeBookings.containsKey(bookingId)) {
            System.out.println("ERROR: Booking does not exist or already cancelled.");
            return;
        }

        BookingRecord record = activeBookings.get(bookingId);

        // Step 2: Record rollback (LIFO)
        rollbackStack.push(record.getRoomId());

        // Step 3: Restore inventory
        String roomType = record.getRoomType();
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);

        // Step 4: Remove from active bookings
        activeBookings.remove(bookingId);

        // Step 5: Update history (simulated)
        System.out.println("Booking " + bookingId + " cancelled successfully.");

        // Show rollback info
        System.out.println("Released Room ID: " + rollbackStack.peek());
    }

    // Display inventory
    public void displayInventory() {
        System.out.println("\nUpdated Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    // Display rollback stack
    public void displayRollbackStack() {
        System.out.println("\nRollback Stack (LIFO): " + rollbackStack);
    }
}

// ---------- MAIN CLASS ----------
public class uc10 {

    public static void main(String[] args) {

        System.out.println("Hotel Booking System v10.0 - Cancellation & Rollback\n");

        CancellationService service = new CancellationService();

        // Valid cancellation
        service.cancelBooking("B101");

        // Duplicate cancellation
        service.cancelBooking("B101");

        // Invalid cancellation
        service.cancelBooking("B999");

        // Another valid cancellation
        service.cancelBooking("B102");

        // Final system state
        service.displayInventory();
        service.displayRollbackStack();
    }
}