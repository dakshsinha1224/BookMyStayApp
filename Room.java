/**
 * The Room class provides a generalized structure for different room types.
 * It encapsulates common attributes like capacity, size, and price.
 */
public abstract class Room {
    private String roomType;
    private int numberOfBeds;
    private double sizeInSquareMeters;
    private double pricePerNight;

    public Room(String roomType, int numberOfBeds, double sizeInSquareMeters, double pricePerNight) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.sizeInSquareMeters = sizeInSquareMeters;
        this.pricePerNight = pricePerNight;
    }

    public String getRoomType() { return roomType; }
    public int getNumberOfBeds() { return numberOfBeds; }
    public double getSizeInSquareMeters() { return sizeInSquareMeters; }
    public double getPricePerNight() { return pricePerNight; }

    @Override
    public String toString() {
        return String.format("%s (Beds: %d, Size: %.1f sqm, Price: $%.2f)", 
            roomType, numberOfBeds, sizeInSquareMeters, pricePerNight);
    }
}
