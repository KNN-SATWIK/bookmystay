import java.util.*;

/**
 * UC9 - Validation & Error Handling (Independent)
 * Version 9.0
 *
 * Ensures invalid inputs and inconsistent states
 * are detected early using validation and exceptions.
 */

// ---------- Custom Exceptions ----------
class InvalidRoomTypeException extends Exception {
    public InvalidRoomTypeException(String message) {
        super(message);
    }
}

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// ---------- Booking Input Model ----------
class BookingInput {

    private String guestName;
    private String roomType;

    public BookingInput(String guestName, String roomType) {
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

// ---------- Validator ----------
class InvalidBookingValidator {

    private Set<String> validRoomTypes;
    private Map<String, Integer> inventory;

    public InvalidBookingValidator() {

        // Allowed room types
        validRoomTypes = new HashSet<>(
                Arrays.asList("Single Room", "Double Room", "Suite Room")
        );

        // Simulated inventory
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    // Validate booking
    public void validate(BookingInput input)
            throws InvalidRoomTypeException, InvalidBookingException {

        // Fail-fast: check room type
        if (!validRoomTypes.contains(input.getRoomType())) {
            throw new InvalidRoomTypeException(
                    "Invalid room type: " + input.getRoomType()
            );
        }

        // Check guest name
        if (input.getGuestName() == null || input.getGuestName().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty");
        }

        // Check availability
        int available = inventory.getOrDefault(input.getRoomType(), 0);

        if (available <= 0) {
            throw new InvalidBookingException(
                    "No availability for " + input.getRoomType()
            );
        }
    }

    // Safe inventory update
    public void updateInventory(String roomType) throws InvalidBookingException {

        int current = inventory.getOrDefault(roomType, 0);

        if (current <= 0) {
            throw new InvalidBookingException(
                    "Cannot reduce inventory below zero for " + roomType
            );
        }

        inventory.put(roomType, current - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

// ---------- MAIN CLASS ----------
public class uc9 {

    public static void main(String[] args) {

        System.out.println("Hotel Booking System v9.0 - Validation & Error Handling\n");

        InvalidBookingValidator validator = new InvalidBookingValidator();

        // Simulated inputs (valid + invalid cases)
        List<BookingInput> inputs = Arrays.asList(
                new BookingInput("Alice", "Single Room"),     // valid
                new BookingInput("Bob", "Suite Room"),        // no availability
                new BookingInput("", "Double Room"),          // invalid name
                new BookingInput("Charlie", "Luxury Room")    // invalid type
        );

        for (BookingInput input : inputs) {

            System.out.println("\nProcessing booking for: "
                    + input.getGuestName() + " | " + input.getRoomType());

            try {
                // Step 1: Validate
                validator.validate(input);

                // Step 2: Update inventory safely
                validator.updateInventory(input.getRoomType());

                System.out.println("Booking SUCCESS");

            } catch (InvalidRoomTypeException e) {
                System.out.println("ERROR: " + e.getMessage());

            } catch (InvalidBookingException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }

        // System continues running safely
        validator.displayInventory();
    }
}