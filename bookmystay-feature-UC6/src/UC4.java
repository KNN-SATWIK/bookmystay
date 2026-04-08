import java.util.*;

/**
 * UC4 - Room Search (Read-Only)
 * Version 4.0
 */

class RoomSearchService {

    public void search(RoomInventory inventory, List<Room> rooms) {

        System.out.println("Available Rooms:\n");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getType());

            if (available > 0) {   // filter unavailable
                room.displayRoomDetails();
                System.out.println("Available: " + available + "\n");
            }
        }
    }
}

public class UC4 {

    public static void main(String[] args) {

        System.out.println("Hotel Booking System v4.0\n");

        RoomInventory inventory = new RoomInventory();

        List<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());

        RoomSearchService service = new RoomSearchService();
        service.search(inventory, rooms);
    }
}