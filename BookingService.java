import java.util.LinkedList;
import java.util.Queue;

/**
 * Manages incoming booking requests using a queue to ensure fairness
 * according to the FIFO (First-Come-First-Served) principle.
 */
public class BookingService {
    private Queue<Reservation> requestQueue;

    public BookingService() {
        this.requestQueue = new LinkedList<>();
    }

    /**
     * Accepts a new booking request and adds it to the waiting queue.
     * Preserves the order of arrival.
     * 
     * @param request The reservation intent from the guest.
     */
    public void submitRequest(Reservation request) {
        requestQueue.offer(request);
        System.out.println("Queued: " + request);
    }
    
    /**
     * Returns the current number of queued requests.
     */
    public int getQueueSize() {
        return requestQueue.size();
    }
}
