import java.util.*;

/**
 * UC6 - Reservation Confirmation & Room Allocation
 * Version 6.0
 *
 * Processes booking queue, assigns unique room IDs,
 * prevents double-booking, and updates inventory.
 */

// ---------- Booking Service ----------
class BookingService {

    private RoomInventory inventory;
    private Queue<Reservation> queue;

    // Track allocated room IDs (global uniqueness)
    private Set<String> allocatedRoomIds;

    // Map room type -> allocated room IDs
    private Map<String, Set<String>> allocationMap;

    public BookingService(RoomInventory inventory, Queue<Reservation> queue) {
        this.inventory = inventory;
        this.queue = queue;
        this.allocatedRoomIds = new HashSet<>();
        this.allocationMap = new HashMap<>();
    }

    // Process all booking requests
    public void processBookings() {

        System.out.println("Processing Booking Requests...\n");

        while (!queue.isEmpty()) {

            Reservation request = queue.poll(); // FIFO
            String roomType = request.getRoomType();

            int available = inventory.getAvailability(roomType);

            // Check availability
            if (available <= 0) {
                System.out.println("Booking FAILED for " + request.getGuestName()
                        + " (No " + roomType + " available)");
                continue;
            }

            // Generate unique room ID
            String roomId = generateRoomId(roomType);

            // Ensure uniqueness (Set)
            while (allocatedRoomIds.contains(roomId)) {
                roomId = generateRoomId(roomType);
            }

            // Record allocation
            allocatedRoomIds.add(roomId);

            allocationMap
                    .computeIfAbsent(roomType, k -> new HashSet<>())
                    .add(roomId);

            // Update inventory (atomic with allocation)
            inventory.updateAvailability(roomType, -1);

            // Confirm booking
            System.out.println("Booking CONFIRMED for " + request.getGuestName()
                    + " | Room: " + roomType
                    + " | ID: " + roomId);
        }
    }

    // Generate room ID (simple unique pattern)
    private String generateRoomId(String roomType) {
        return roomType.substring(0, 2).toUpperCase() + "-" + (100 + new Random().nextInt(900));
    }

    // Display allocation summary
    public void displayAllocations() {
        System.out.println("\nFinal Allocations:\n");

        for (Map.Entry<String, Set<String>> entry : allocationMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

// ---------- MAIN CLASS ----------
public class UC6 {

    public static void main(String[] args) {

        System.out.println("Hotel Booking System v6.0\n");

        // Reuse inventory (UC3)
        RoomInventory inventory = new RoomInventory();

        // Reuse queue (UC5 logic)
        Queue<Reservation> queue = new LinkedList<>();

        queue.offer(new Reservation("Alice", "Single Room"));
        queue.offer(new Reservation("Bob", "Double Room"));
        queue.offer(new Reservation("Charlie", "Suite Room"));
        queue.offer(new Reservation("David", "Suite Room"));
        queue.offer(new Reservation("Eve", "Suite Room")); // may fail if exhausted

        // Process bookings
        BookingService service = new BookingService(inventory, queue);

        service.processBookings();

        // Show final allocations
        service.displayAllocations();

        // Show updated inventory
        System.out.println("\nUpdated Inventory:\n");
        inventory.displayInventory();
    }
}
