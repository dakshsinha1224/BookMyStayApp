import java.util.List;

/**
 * Data structure to hold confirmed booking details.
 */
public class BookingRecord implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private String reservationId;
    private Reservation reservation;
    private String allocatedRoomId;
    private List<Service> selectedServices;
    private double totalCost;
    private boolean isCancelled;

    public BookingRecord(String reservationId, Reservation reservation, String allocatedRoomId, List<Service> selectedServices, double totalCost) {
        this.reservationId = reservationId;
        this.reservation = reservation;
        this.allocatedRoomId = allocatedRoomId;
        this.selectedServices = selectedServices;
        this.totalCost = totalCost;
        this.isCancelled = false;
    }

    public String getReservationId() { return reservationId; }
    public Reservation getReservation() { return reservation; }
    public String getAllocatedRoomId() { return allocatedRoomId; }
    public List<Service> getSelectedServices() { return selectedServices; }
    public double getTotalCost() { return totalCost; }
    public boolean isCancelled() { return isCancelled; }
    public void setCancelled(boolean cancelled) { isCancelled = cancelled; }

    @Override
    public String toString() {
        return String.format("ID: %s | Guest: %s | Room: %s | Services: %s | Total: $%.2f | Status: %s",
            reservationId, reservation.getGuestName(), allocatedRoomId, selectedServices, totalCost, isCancelled ? "CANCELLED" : "CONFIRMED");
    }
}
