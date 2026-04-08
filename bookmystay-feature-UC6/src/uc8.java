import java.util.*;

/**
 * UC8 - Booking History & Reporting (Independent)
 * Version 8.0
 *
 * Stores confirmed bookings and generates reports
 * without modifying core booking logic.
 */

// ---------- Booking Record ----------
class BookingRecord {

    private String bookingId;
    private String guestName;
    private String roomType;

    public BookingRecord(String bookingId, String guestName, String roomType) {
        this.bookingId = bookingId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("ID: " + bookingId +
                " | Guest: " + guestName +
                " | Room: " + roomType);
    }
}

// ---------- Booking History ----------
class BookingHistory {

    // Maintains insertion order
    private List<BookingRecord> records;

    public BookingHistory() {
        records = new ArrayList<>();
    }

    // Store confirmed booking
    public void addBooking(BookingRecord record) {
        records.add(record);
        System.out.println("Stored booking: " + record.getBookingId());
    }

    // Retrieve all bookings
    public List<BookingRecord> getBookings() {
        return records;
    }

    // Display history
    public void displayHistory() {
        System.out.println("\nBooking History:\n");

        if (records.isEmpty()) {
            System.out.println("No bookings available.");
            return;
        }

        for (BookingRecord r : records) {
            r.display();
        }
    }
}

// ---------- Reporting Service ----------
class BookingReportService {

    // Total bookings
    public void totalBookings(List<BookingRecord> records) {
        System.out.println("\nTotal Bookings: " + records.size());
    }

    // Room-wise summary
    public void roomTypeSummary(List<BookingRecord> records) {

        Map<String, Integer> countMap = new HashMap<>();

        for (BookingRecord r : records) {
            countMap.put(
                    r.getRoomType(),
                    countMap.getOrDefault(r.getRoomType(), 0) + 1
            );
        }

        System.out.println("\nRoom Type Summary:");

        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    // Guest-wise report
    public void guestSummary(List<BookingRecord> records) {

        System.out.println("\nGuest Booking Summary:");

        for (BookingRecord r : records) {
            System.out.println(r.getGuestName() + " booked " + r.getRoomType());
        }
    }
}

// ---------- MAIN CLASS ----------
public class uc8 {

    public static void main(String[] args) {

        System.out.println("Hotel Booking System v8.0 - Booking History\n");

        BookingHistory history = new BookingHistory();
        BookingReportService report = new BookingReportService();

        // Simulated confirmed bookings (external input)
        history.addBooking(new BookingRecord("B101", "Alice", "Single Room"));
        history.addBooking(new BookingRecord("B102", "Bob", "Double Room"));
        history.addBooking(new BookingRecord("B103", "Charlie", "Suite Room"));
        history.addBooking(new BookingRecord("B104", "Alice", "Suite Room"));

        // Display stored data
        history.displayHistory();

        // Generate reports (read-only)
        List<BookingRecord> data = history.getBookings();

        report.totalBookings(data);
        report.roomTypeSummary(data);
        report.guestSummary(data);
    }
}