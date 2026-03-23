import java.util.Set;

/**
 * Validates booking input and system state before processing.
 */
public class InvalidBookingValidator {
    
    /**
     * Validates a booking request against current inventory.
     */
    public void validate(Reservation request, RoomInventory inventory, Set<String> knownRoomTypes) throws BookingValidationException {
        String requestedType = request.getRequestedRoomType();
        
        // Check if room type is valid
        if (!knownRoomTypes.contains(requestedType)) {
            throw new InvalidRoomTypeException(requestedType);
        }
        
        // Check if inventory is sufficient
        if (inventory.getAvailableRooms(requestedType) <= 0) {
            throw new InsufficientInventoryException(requestedType);
        }
    }
}
