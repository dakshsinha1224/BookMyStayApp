import java.util.List;

/**
 * Generates summaries and reports from stored booking data.
 */
public class BookingReportService {
    private BookingHistory history;

    public BookingReportService(BookingHistory history) {
        this.history = history;
    }

    /**
     * Displays a full report of all bookings.
     */
    public void generateFullReport() {
        System.out.println("\n--- Booking History Report ---");
        List<BookingRecord> records = history.getHistory();
        if (records.isEmpty()) {
            System.out.println("No records found.");
        } else {
            for (BookingRecord record : records) {
                System.out.println(record);
            }
        }
        System.out.println("------------------------------");
    }

    /**
     * Displays a summary report including total revenue.
     */
    public void generateSummaryReport() {
        System.out.println("\n--- Operational Summary Report ---");
        List<BookingRecord> records = history.getHistory();
        int totalBookings = records.size();
        int activeBookings = 0;
        int cancelledBookings = 0;
        double totalRevenue = 0.0;

        for (BookingRecord record : records) {
            if (record.isCancelled()) {
                cancelledBookings++;
            } else {
                activeBookings++;
                totalRevenue += record.getTotalCost();
            }
        }

        System.out.println("Total Bookings: " + totalBookings);
        System.out.println("Active Bookings: " + activeBookings);
        System.out.println("Cancelled Bookings: " + cancelledBookings);
        System.out.format("Total Revenue (Active): $%.2f%n", totalRevenue);
        System.out.println("----------------------------------");
    }
}
