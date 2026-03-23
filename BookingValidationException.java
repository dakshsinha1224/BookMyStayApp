/**
 * Base exception for booking-related validation failures.
 */
public class BookingValidationException extends Exception {
    public BookingValidationException(String message) {
        super(message);
    }
}

class InvalidRoomTypeException extends BookingValidationException {
    public InvalidRoomTypeException(String roomType) {
        super("Invalid room type: " + roomType);
    }
}

class InsufficientInventoryException extends BookingValidationException {
    public InsufficientInventoryException(String roomType) {
        super("No availability for room type: " + roomType);
    }
}
