import java.util.HashMap;
import java.util.Map;

/**
 * Manages the total availability of different room types centrally.
 */
public class RoomInventory implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Integer> availabilityMap;

    public RoomInventory() {
        availabilityMap = new HashMap<>();
        // Initialize default inventory
        availabilityMap.put("Single Room", 5);
        availabilityMap.put("Double Room", 3);
        availabilityMap.put("Suite Room", 1);
    }

    /**
     * Retrieves the current available count for a specific room type.
     * 
     * @param roomType The type of room (e.g., "Single Room").
     * @return The number of rooms available, or 0 if the type does not exist.
     */
    public int getAvailableRooms(String roomType) {
        return availabilityMap.getOrDefault(roomType, 0);
    }

    /**
     * Updates the availability for a specific room type.
     * 
     * @param roomType The type of room.
     * @param count The new availability count.
     */
    public void updateAvailability(String roomType, int count) {
        if (count >= 0) {
            availabilityMap.put(roomType, count);
        } else {
            System.out.println("Error: Cannot set negative availability for " + roomType);
        }
    }
    
    /**
     * Displays all inventory state to the console.
     */
    public void displayInventory() {
        System.out.println("--- Centralized Room Inventory ---");
        for (Map.Entry<String, Integer> entry : availabilityMap.entrySet()) {
            System.out.println(entry.getKey() + " | Available: " + entry.getValue());
        }
        System.out.println("----------------------------------");
    }
}
