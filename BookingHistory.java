import java.util.ArrayList;
import java.util.List;

/**
 * Maintains a record of confirmed reservations.
 */
public class BookingHistory implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private List<BookingRecord> history;

    public BookingHistory() {
        this.history = new ArrayList<>();
    }

    /**
     * Adds a record to history in insertion order.
     */
    public void addRecord(BookingRecord record) {
        history.add(record);
    }

    /**
     * Retrieves the entire history of bookings.
     */
    public List<BookingRecord> getHistory() {
        return history;
    }

    /**
     * Finds a record by reservation ID.
     */
    public BookingRecord findRecord(String reservationId) {
        for (BookingRecord record : history) {
            if (record.getReservationId().equals(reservationId)) {
                return record;
            }
        }
        return null;
    }
}
