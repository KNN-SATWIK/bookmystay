import java.util.LinkedList;
import java.util.Queue;

/**
 * UC5 - Booking Request Queue (FIFO)
 * Version 5.0
 *
 * Handles booking requests in arrival order without modifying inventory.
 */

// ---------- Reservation (Request Model) ----------
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("Guest: " + guestName + " | Requested: " + roomType);
    }
}

// ---------- Booking Queue ----------
class BookingQueue {

    private Queue<Reservation> queue;

    public BookingQueue() {
        queue = new LinkedList<>();
    }

    // Add request (FIFO)
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }

    // View all requests
    public void displayQueue() {
        System.out.println("\nCurrent Booking Queue:\n");

        if (queue.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }

        for (Reservation r : queue) {
            r.display();
        }
    }

    // Peek next request (no removal)
    public void peekNext() {
        System.out.println("\nNext Request to Process:");
        if (queue.peek() != null) {
            queue.peek().display();
        } else {
            System.out.println("Queue is empty.");
        }
    }
}

// ---------- MAIN CLASS ----------
public class UC5 {

    public static void main(String[] args) {

        System.out.println("Hotel Booking System v5.0\n");

        BookingQueue bookingQueue = new BookingQueue();

        // Simulate incoming booking requests (FIFO)
        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite Room"));

        // Display queue
        bookingQueue.displayQueue();

        // Show next request (without removing)
        bookingQueue.peekNext();
    }
}
