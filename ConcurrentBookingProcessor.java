import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Processes booking requests in a multi-threaded environment.
 */
public class ConcurrentBookingProcessor {
    private BookingService bookingService;

    public ConcurrentBookingProcessor(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * Simulates concurrent booking requests from multiple guests.
     */
    public void simulateConcurrentBookings(int numberOfGuests, String roomType) {
        System.out.println("\n--- Starting Concurrent Booking Simulation (" + numberOfGuests + " guests) ---");
        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 1; i <= numberOfGuests; i++) {
            final String guestName = "ConcurrentGuest-" + i;
            executor.submit(() -> {
                Reservation request = new Reservation(guestName, roomType);
                bookingService.submitRequest(request);
            });
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        
        System.out.println("--- Requests Added to Queue. Processing Allocations... ---");
        bookingService.processAllocations();
        System.out.println("--- Concurrent Simulation Finished ---");
    }
}
