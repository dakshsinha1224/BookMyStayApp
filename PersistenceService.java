import java.io.*;

/**
 * Handles storing and retrieving system state from persistent storage.
 */
public class PersistenceService {
    private static final String INVENTORY_FILE = "inventory.ser";
    private static final String HISTORY_FILE = "history.ser";

    /**
     * Serializes inventory and history to files.
     */
    public void saveSystemState(RoomInventory inventory, BookingHistory history) {
        System.out.println("\n--- Persisting System State ---");
        try (ObjectOutputStream outInv = new ObjectOutputStream(new FileOutputStream(INVENTORY_FILE));
             ObjectOutputStream outHist = new ObjectOutputStream(new FileOutputStream(HISTORY_FILE))) {
            
            outInv.writeObject(inventory);
            outHist.writeObject(history);
            
            System.out.println("SUCCESS: Inventory and History saved to disk.");
        } catch (IOException e) {
            System.out.println("ERROR: Failed to save system state: " + e.getMessage());
        }
    }

    /**
     * Deserializes inventory and history from files.
     */
    public void loadSystemState(RoomInventory[] inventoryRef, BookingHistory[] historyRef) {
        System.out.println("\n--- Recovering System State ---");
        File invFile = new File(INVENTORY_FILE);
        File histFile = new File(HISTORY_FILE);

        if (!invFile.exists() || !histFile.exists()) {
            System.out.println("INFO: No persistence files found. Starting with fresh state.");
            return;
        }

        try (ObjectInputStream inInv = new ObjectInputStream(new FileInputStream(INVENTORY_FILE));
             ObjectInputStream inHist = new ObjectInputStream(new FileInputStream(HISTORY_FILE))) {
            
            inventoryRef[0] = (RoomInventory) inInv.readObject();
            historyRef[0] = (BookingHistory) inHist.readObject();
            
            System.out.println("SUCCESS: System state recovered from disk.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("WARNING: Failed to recover state (corrupted or incompatible). " + e.getMessage());
        }
    }
}
