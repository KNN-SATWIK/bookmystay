/**
 * UseCase2RoomInitialization
 *
 * This class demonstrates basic object-oriented modeling using abstraction,
 * inheritance, polymorphism, and encapsulation in a Hotel Booking System.
 *
 * It creates different room types and displays their static availability.
 *
 * @author Satwik
 * @version 2.1
 */
abstract class Room {
    private String roomType;
    private int beds;
    private double price;

    // Constructor
    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    // Getters (Encapsulation)
    public String getRoomType() {
        return roomType;
    }

    public int getBeds() {
        return beds;
    }

    public double getPrice() {
        return price;
    }

    // Abstract method
    public abstract void displayRoomDetails();
}

// Single Room Class
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 1500.0);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println(getRoomType() + " | Beds: " + getBeds() + " | Price: ₹" + getPrice());
    }
}

// Double Room Class
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 2500.0);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println(getRoomType() + " | Beds: " + getBeds() + " | Price: ₹" + getPrice());
    }
}

// Suite Room Class
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 5000.0);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println(getRoomType() + " | Beds: " + getBeds() + " | Price: ₹" + getPrice());
    }
}

public class UC2 {

    /**
     * Main method - Entry point
     */
    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Welcome to Book My Stay App");
        System.out.println("   Hotel Booking System v2.1");
        System.out.println("=====================================\n");

        // Polymorphic references
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability (simple variables)
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        // Display details
        System.out.println("Available Room Types:\n");

        single.displayRoomDetails();
        System.out.println("Available: " + singleAvailable + "\n");

        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleAvailable + "\n");

        suite.displayRoomDetails();
        System.out.println("Available: " + suiteAvailable + "\n");

        System.out.println("Application executed successfully.");
    }
}
