import java.util.List;

/**
 * Provides read-only search operations to find available rooms
 * without modifying the system state.
 */
public class SearchService {
    private RoomInventory inventory;

    public SearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Searches for available rooms and displays their details.
     * Only rooms with availability greater than zero are shown.
     * 
     * @param roomCatalog The list of available room types in the system domain.
     */
    public void searchAvailableRooms(List<Room> roomCatalog) {
        System.out.println("--- Search Results: Available Rooms ---");
        boolean anyAvailable = false;

        for (Room room : roomCatalog) {
            int availableCount = inventory.getAvailableRooms(room.getRoomType());
            if (availableCount > 0) {
                anyAvailable = true;
                System.out.printf("Option: %s | Pricing: $%.2f/night | Available: %d%n",
                    room.toString(), room.getPricePerNight(), availableCount);
            }
        }

        if (!anyAvailable) {
            System.out.println("No rooms are currently available.");
        }
        System.out.println("---------------------------------------");
    }
}
