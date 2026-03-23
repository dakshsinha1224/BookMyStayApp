import java.util.Stack;

/**
 * Validates cancellations and performs controlled rollback operations.
 */
public class CancellationService {
    private BookingHistory history;
    private RoomInventory inventory;
    private Stack<String> releasedRoomIds;

    public CancellationService(BookingHistory history, RoomInventory inventory) {
        this.history = history;
        this.inventory = inventory;
        this.releasedRoomIds = new Stack<>();
    }

    /**
     * Cancels a confirmed booking and rolls back system state.
     * 
     * @param reservationId The ID of the reservation to cancel.
     */
    public void cancelBooking(String reservationId) {
        BookingRecord record = history.findRecord(reservationId);
        
        if (record == null) {
            System.out.println("CANCELLATION FAILED: Reservation ID " + reservationId + " not found.");
            return;
        }

        if (record.isCancelled()) {
            System.out.println("CANCELLATION FAILED: Reservation ID " + reservationId + " is already cancelled.");
            return;
        }

        // Perform Rollback
        String roomId = record.getAllocatedRoomId();
        String roomType = record.getReservation().getRequestedRoomType();

        // 1. Record in rollback structure (Stack)
        releasedRoomIds.push(roomId);
        System.out.println("Rollback: Room ID " + roomId + " added to released stack.");

        // 2. Increment inventory
        int currentCount = inventory.getAvailableRooms(roomType);
        inventory.updateAvailability(roomType, currentCount + 1);

        // 3. Update history
        record.setCancelled(true);

        System.out.println("SUCCESS: Cancelled booking for " + record.getReservation().getGuestName() + ". Room " + roomId + " is now available.");
    }

    /**
     * Displays recently released room IDs in LIFO order.
     */
    public void displayReleasedRooms() {
        System.out.println("\n--- Recently Released Room IDs (LIFO) ---");
        if (releasedRoomIds.isEmpty()) {
            System.out.println("No rooms released yet.");
        } else {
            // We use a clone to avoid destructive peek/pop if we just want to see
            Stack<String> tempStack = (Stack<String>) releasedRoomIds.clone();
            while (!tempStack.isEmpty()) {
                System.out.println(tempStack.pop());
            }
        }
        System.out.println("-----------------------------------------");
    }
}
