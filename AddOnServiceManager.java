import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages the association between Reservations and Selected Services.
 */
public class AddOnServiceManager {
    private Map<String, List<Service>> reservationServices;

    public AddOnServiceManager() {
        this.reservationServices = new HashMap<>();
    }

    /**
     * Adds a service to a specific reservation ID.
     */
    public void addService(String reservationId, Service service) {
        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).add(service);
        System.out.println("Service Added: " + service.getName() + " to Reservation ID: " + reservationId);
    }

    /**
     * Calculates the total cost of all services for a reservation.
     */
    public double calculateTotalServiceCost(String reservationId) {
        List<Service> services = reservationServices.get(reservationId);
        if (services == null) return 0.0;
        
        double total = 0.0;
        for (Service s : services) {
            total += s.getPrice();
        }
        return total;
    }

    /**
     * Retrieves the list of services for a reservation.
     */
    public List<Service> getServices(String reservationId) {
        return reservationServices.getOrDefault(reservationId, new ArrayList<>());
    }
}
