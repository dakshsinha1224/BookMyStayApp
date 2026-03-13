/**
 * The Main class serves as the entry point for the Hotel Booking Management System.
 * It demonstrates the fundamental structure of a Java application and basic console output.
 * 
 * @author Antigravity
 * @version 1.0
 */
public class Main {
    /**
     * The main method is the entry point of the standalone Java application.
     * 
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Booking System v1.0");
        System.out.println("Initializing system components...\n");

        // UC2: Basic Room Types & Static Availability
        Room singleRoomType = new SingleRoom();
        Room doubleRoomType = new DoubleRoom();
        Room suiteRoomType = new SuiteRoom();

        // Hardcoded static availability (Scattered State)
        int singleRoomAvailability = 5;
        int doubleRoomAvailability = 3;
        int suiteRoomAvailability = 1;

        System.out.println("--- Current Room Availability ---");
        System.out.println(singleRoomType + " | Available: " + singleRoomAvailability);
        System.out.println(doubleRoomType + " | Available: " + doubleRoomAvailability);
        System.out.println(suiteRoomType + " | Available: " + suiteRoomAvailability);
    }
}
