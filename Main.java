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

        // UC2: Basic Room Types
        Room singleRoomType = new SingleRoom();
        Room doubleRoomType = new DoubleRoom();
        Room suiteRoomType = new SuiteRoom();

        // UC3: Centralized Inventory
        RoomInventory inventory = new RoomInventory();
        System.out.println("--- System Initialization Complete ---");
        System.out.println(singleRoomType.getRoomType() + " base availability: " + inventory.getAvailableRooms(singleRoomType.getRoomType()));
        System.out.println(doubleRoomType.getRoomType() + " base availability: " + inventory.getAvailableRooms(doubleRoomType.getRoomType()));
        System.out.println(suiteRoomType.getRoomType() + " base availability: " + inventory.getAvailableRooms(suiteRoomType.getRoomType()));
        
        inventory.displayInventory();
    }
}
