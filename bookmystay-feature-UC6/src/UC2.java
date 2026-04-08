/**
 * UC2 - Room Types & Static Structure
 * Version 2.1
 */

abstract class Room {
    private String roomType;
    private int beds;
    private double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    // ✅ Consistent method used everywhere
    public String getType() {
        return roomType;
    }

    public int getBeds() {
        return beds;
    }

    public double getPrice() {
        return price;
    }

    public abstract void displayRoomDetails();
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 1500);
    }

    public void displayRoomDetails() {
        System.out.println(getType() + " | Beds: " + getBeds() + " | Price: ₹" + getPrice());
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 2500);
    }

    public void displayRoomDetails() {
        System.out.println(getType() + " | Beds: " + getBeds() + " | Price: ₹" + getPrice());
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 5000);
    }

    public void displayRoomDetails() {
        System.out.println(getType() + " | Beds: " + getBeds() + " | Price: ₹" + getPrice());
    }
}

public class UC2 {

    public static void main(String[] args) {

        System.out.println("Hotel Booking System v2.1\n");

        Room r1 = new SingleRoom();
        Room r2 = new DoubleRoom();
        Room r3 = new SuiteRoom();

        r1.displayRoomDetails();
        r2.displayRoomDetails();
        r3.displayRoomDetails();
    }
}