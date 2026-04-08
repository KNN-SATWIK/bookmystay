import java.util.*;

/**
 * UC11 - Concurrent Booking & Synchronization (Independent)
 * Version 11.0
 *
 * Demonstrates thread-safe booking using synchronized blocks
 * to prevent race conditions and ensure consistent state.
 */

// ---------- Booking Request ----------
class BookingRequest {

    private String guestName;
    private String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// ---------- Shared Booking System ----------
class ConcurrentBookingProcessor {

    // Shared queue
    private Queue<BookingRequest> queue;

    // Shared inventory
    private Map<String, Integer> inventory;

    public ConcurrentBookingProcessor() {

        queue = new LinkedList<>();

        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    // Add request (multiple threads may call this)
    public synchronized void addRequest(BookingRequest request) {
        queue.offer(request);
        System.out.println(Thread.currentThread().getName()
                + " added request for " + request.getGuestName());
    }

    // Process request (critical section)
    public void processRequest() {

        BookingRequest request;

        // Step 1: synchronized retrieval
        synchronized (this) {
            if (queue.isEmpty()) return;
            request = queue.poll();
        }

        // Step 2: synchronized allocation (critical section)
        synchronized (this) {

            int available = inventory.getOrDefault(request.getRoomType(), 0);

            if (available > 0) {

                inventory.put(request.getRoomType(), available - 1);

                System.out.println(Thread.currentThread().getName()
                        + " CONFIRMED booking for "
                        + request.getGuestName()
                        + " (" + request.getRoomType() + ")");

            } else {

                System.out.println(Thread.currentThread().getName()
                        + " FAILED booking for "
                        + request.getGuestName()
                        + " (No availability)");
            }
        }
    }

    public void displayInventory() {
        System.out.println("\nFinal Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

// ---------- Worker Thread ----------
class BookingWorker extends Thread {

    private ConcurrentBookingProcessor processor;

    public BookingWorker(ConcurrentBookingProcessor processor, String name) {
        super(name);
        this.processor = processor;
    }

    public void run() {
        // Each thread tries to process multiple requests
        for (int i = 0; i < 2; i++) {
            processor.processRequest();
        }
    }
}

// ---------- MAIN CLASS ----------
public class uc11 {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Hotel Booking System v11.0 - Concurrency\n");

        ConcurrentBookingProcessor processor = new ConcurrentBookingProcessor();

        // Simulate concurrent request submission
        processor.addRequest(new BookingRequest("Alice", "Single Room"));
        processor.addRequest(new BookingRequest("Bob", "Single Room"));
        processor.addRequest(new BookingRequest("Charlie", "Single Room"));
        processor.addRequest(new BookingRequest("David", "Double Room"));
        processor.addRequest(new BookingRequest("Eve", "Suite Room"));

        // Create multiple threads (guests)
        Thread t1 = new BookingWorker(processor, "Thread-1");
        Thread t2 = new BookingWorker(processor, "Thread-2");
        Thread t3 = new BookingWorker(processor, "Thread-3");

        // Start threads concurrently
        t1.start();
        t2.start();
        t3.start();

        // Wait for completion
        t1.join();
        t2.join();
        t3.join();

        // Final state
        processor.displayInventory();
    }
}