import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;

/**
 * Manages incoming booking requests and performs safe room allocation.
 * Uses a Queue for fairness and a Set to guarantee unique room ID assignments.
 */
public class BookingService {
    private Queue<Reservation> requestQueue;
    private Map<String, Set<String>> allocatedRooms;
    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.requestQueue = new LinkedList<>();
        this.allocatedRooms = new HashMap<>();
        this.inventory = inventory;
    }

    /**
     * Accepts a new booking request and adds it to the waiting queue.
     * 
     * @param request The reservation intent from the guest.
     */
    public void submitRequest(Reservation request) {
        requestQueue.offer(request);
        System.out.println("Queued: " + request);
    }
    
    /**
     * Processes all queued booking requests, allocating rooms if available.
     * Prevents double booking by enforcing unique room IDs.
     */
    public void processAllocations() {
        System.out.println("\n--- Processing Room Allocations ---");
        while (!requestQueue.isEmpty()) {
            Reservation request = requestQueue.poll();
            String requestedType = request.getRequestedRoomType();
            
            // Check availability
            int currentAvailability = inventory.getAvailableRooms(requestedType);
            if (currentAvailability > 0) {
                // Generate a unique room ID
                String uniqueRoomId = generateUniqueRoomId(requestedType);
                
                // Store the allocation
                allocatedRooms.putIfAbsent(requestedType, new HashSet<>());
                allocatedRooms.get(requestedType).add(uniqueRoomId);
                
                // Update system state
                inventory.updateAvailability(requestedType, currentAvailability - 1);
                
                System.out.println("SUCCESS: Allocated " + uniqueRoomId + " to " + request.getGuestName());
            } else {
                System.out.println("FAILED: No availability for " + request.getGuestName() + " (" + requestedType + ")");
            }
        }
        System.out.println("-----------------------------------");
    }
    
    /**
     * Generates a unique room identifier.
     */
    private String generateUniqueRoomId(String roomType) {
        String baseType = roomType.substring(0, 3).toUpperCase();
        String uniqueId = baseType + "-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        return uniqueId;
    }

    public int getQueueSize() {
        return requestQueue.size();
    }
    
    public void displayAllocations() {
        System.out.println("--- Current Room Allocations ---");
        for (Map.Entry<String, Set<String>> entry : allocatedRooms.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("--------------------------------");
    }
}
