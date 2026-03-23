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

    private AddOnServiceManager serviceManager;
    private BookingHistory history;
    private InvalidBookingValidator validator;
    private Set<String> knownRoomTypes;

    public BookingService(RoomInventory inventory, AddOnServiceManager serviceManager, BookingHistory history) {
        this.requestQueue = new LinkedList<>();
        this.allocatedRooms = new HashMap<>();
        this.inventory = inventory;
        this.serviceManager = serviceManager;
        this.history = history;
        this.validator = new InvalidBookingValidator();
        this.knownRoomTypes = new HashSet<>();
        // Default patterns
        knownRoomTypes.add("Single Room");
        knownRoomTypes.add("Double Room");
        knownRoomTypes.add("Suite Room");
    }

    /**
     * Accepts a new booking request and adds it to the waiting queue.
     * Synchronized for UC11 thread safety.
     * 
     * @param request The reservation intent from the guest.
     */
    public synchronized void submitRequest(Reservation request) {
        requestQueue.offer(request);
        System.out.println("Queued: " + request);
    }
    
    /**
     * Processes all queued booking requests, allocating rooms if available.
     * Synchronized for UC11 thread safety.
     */
    public synchronized void processAllocations() {
        System.out.println("\n--- Processing Room Allocations ---");
        while (!requestQueue.isEmpty()) {
            Reservation request = requestQueue.poll();
            String requestedType = request.getRequestedRoomType();
            
            try {
                // UC9: Validation
                validator.validate(request, inventory, knownRoomTypes);
                
                // UC6: Room Allocation
                int currentAvailability = inventory.getAvailableRooms(requestedType);
                String uniqueRoomId = generateUniqueRoomId(requestedType);
                String reservationId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
                
                // Store the allocation
                allocatedRooms.putIfAbsent(requestedType, new HashSet<>());
                allocatedRooms.get(requestedType).add(uniqueRoomId);
                
                // Update system state
                inventory.updateAvailability(requestedType, currentAvailability - 1);
                
                // UC7 & UC8: Confirmation and History
                double roomPrice = getRoomPrice(requestedType); // Helper
                double serviceCost = serviceManager.calculateTotalServiceCost(reservationId);
                BookingRecord record = new BookingRecord(
                    reservationId, request, uniqueRoomId, 
                    serviceManager.getServices(reservationId), 
                    roomPrice + serviceCost
                );
                history.addRecord(record);
                
                System.out.println("SUCCESS: Allocated " + uniqueRoomId + " to " + request.getGuestName() + " | Reservation ID: " + reservationId);
                
            } catch (BookingValidationException e) {
                System.out.println("VALIDATION FAILED: " + e.getMessage() + " for " + request.getGuestName());
            } catch (Exception e) {
                System.out.println("SYSTEM ERROR: " + e.getMessage());
            }
        }
        System.out.println("-----------------------------------");
    }

    private double getRoomPrice(String roomType) {
        switch (roomType) {
            case "Single Room": return 100.0;
            case "Double Room": return 200.0;
            case "Suite Room": return 500.0;
            default: return 0.0;
        }
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
