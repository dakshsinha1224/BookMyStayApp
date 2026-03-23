import java.util.Arrays;
import java.util.List;

/**
 * The Main class serves as the entry point for the Hotel Booking Management System.
 * Demonstrates UC7 to UC12 functionality.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   BookMyStayApp - Extended Management System");
        System.out.println("============================================\n");

        // Initialization
        RoomInventory inventory = new RoomInventory();
        AddOnServiceManager serviceManager = new AddOnServiceManager();
        BookingHistory history = new BookingHistory();
        BookingService bookingService = new BookingService(inventory, serviceManager, history);
        BookingReportService reportService = new BookingReportService(history);
        CancellationService cancellationService = new CancellationService(history, inventory);
        PersistenceService persistenceService = new PersistenceService();
        ConcurrentBookingProcessor concurrentProcessor = new ConcurrentBookingProcessor(bookingService);

        // UC12: System Recovery (Try to load previous state)
        RoomInventory[] invRef = {inventory};
        BookingHistory[] histRef = {history};
        persistenceService.loadSystemState(invRef, histRef);
        inventory = invRef[0];
        history = histRef[0];
        // Re-inject recovered state into services
        bookingService = new BookingService(inventory, serviceManager, history);
        reportService = new BookingReportService(history);
        cancellationService = new CancellationService(history, inventory);
        concurrentProcessor = new ConcurrentBookingProcessor(bookingService);

        System.out.println("\n--- Current Inventory Status ---");
        inventory.displayInventory();

        // UC7: Add-On Service Selection (Demo)
        System.out.println("\n[UC7] Pre-selecting services for future bookings...");
        Service wifi = new Service("High-Speed Wi-Fi", 15.0);
        Service breakfast = new Service("Buffet Breakfast", 25.0);
        Service spa = new Service("Spa Access", 50.0);

        // We don't have reservation IDs yet, but we can simulate adding services to "Expected" IDs 
        // Or better, BookingService now handles it during processing.
        // For demonstration, let's submit a request and then add services if we knew the ID.
        // However, UC7 says "Guest selects one or more add-on services... List is mapped to reservation ID."
        // In our implementation, we'll simulate this by adding to a known ID for demo.

        // UC5 & UC6: Regular Booking
        System.out.println("\n[UC5/6] Submitting standard booking requests...");
        bookingService.submitRequest(new Reservation("Alice", "Single Room"));
        bookingService.submitRequest(new Reservation("Bob", "Suite Room")); // Only 1 available
        
        // UC9: Validation & Error Handling
        System.out.println("\n[UC9] Testing validation (Invalid Room Type)...");
        bookingService.submitRequest(new Reservation("InvalidGuest", "Penthouse"));
        System.out.println("[UC9] Testing validation (Insufficient Inventory)...");
        bookingService.submitRequest(new Reservation("Eve", "Suite Room")); // Should fail later during process

        // Process these allocations
        bookingService.processAllocations();

        // UC8: Reporting
        reportService.generateFullReport();

        // UC10: Cancellation & Rollback
        if (!history.getHistory().isEmpty()) {
            String idToCancel = history.getHistory().get(0).getReservationId();
            System.out.println("\n[UC10] Cancelling Booking ID: " + idToCancel);
            cancellationService.cancelBooking(idToCancel);
            cancellationService.displayReleasedRooms();
        }

        // UC11: Concurrent Simulation
        System.out.println("\n[UC11] Simulating 5 concurrent guests for Double Rooms...");
        concurrentProcessor.simulateConcurrentBookings(5, "Double Room"); // Only 3 available

        // UC8: Final Report
        reportService.generateSummaryReport();
        inventory.displayInventory();

        // UC12: Persistence (Save state before exit)
        persistenceService.saveSystemState(inventory, history);
        
        System.out.println("\n============================================");
        System.out.println("   Simulation Complete. State Persisted.");
        System.out.println("============================================");
    }
}
