/**
 * Represents a guest's intent to book a specific room type.
 */
public class Reservation implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private String guestName;
    private String requestedRoomType;

    public Reservation(String guestName, String requestedRoomType) {
        this.guestName = guestName;
        this.requestedRoomType = requestedRoomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRequestedRoomType() {
        return requestedRoomType;
    }

    @Override
    public String toString() {
        return "Reservation Request [Guest: " + guestName + ", Room Type: " + requestedRoomType + "]";
    }
}
