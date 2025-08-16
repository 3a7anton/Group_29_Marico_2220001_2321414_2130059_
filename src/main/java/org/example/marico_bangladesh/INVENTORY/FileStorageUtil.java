package org.example.marico_bangladesh.INVENTORY;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileStorageUtil {
    private static final String DATA_DIR = "data";
    private static final String INVENTORY_FILE = "inventory.bin";
    private static final String MOVEMENT_LOG_FILE = "movement_log.bin";
    
    private static Map<String, InventoryManager> inventoryMap = new HashMap<>();
    private static List<StockMovement> movementLog = new ArrayList<>();
    
    static {
        // Create data directory if it doesn't exist
        new File(DATA_DIR).mkdirs();
        loadData();
    }
    
    public static class StockMovement implements Serializable {
        private String productId;
        private String changeType;
        private int quantityChange;
        private String reason;
        private LocalDateTime timestamp;
        
        public StockMovement(String productId, String changeType, int quantityChange, String reason) {
            this.productId = productId;
            this.changeType = changeType;
            this.quantityChange = quantityChange;
            this.reason = reason;
            this.timestamp = LocalDateTime.now();
        }

        public String getProductId() { return productId; }
        public String getChangeType() { return changeType; }
        public int getQuantityChange() { return quantityChange; }
        public String getReason() { return reason; }
        public LocalDateTime getTimestamp() { return timestamp; }
    }
    
    public static void loadData() {
        loadInventory();
        loadMovementLog();
    }
    
    @SuppressWarnings("unchecked")
    private static void loadInventory() {
        File file = new File(DATA_DIR, INVENTORY_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                inventoryMap = (Map<String, InventoryManager>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                inventoryMap = new HashMap<>();
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    private static void loadMovementLog() {
        File file = new File(DATA_DIR, MOVEMENT_LOG_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                movementLog = (List<StockMovement>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                movementLog = new ArrayList<>();
            }
        }
    }
    
    private static void saveInventory() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(new File(DATA_DIR, INVENTORY_FILE)))) {
            oos.writeObject(inventoryMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void saveMovementLog() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(new File(DATA_DIR, MOVEMENT_LOG_FILE)))) {
            oos.writeObject(movementLog);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static InventoryManager findProduct(String productId) {
        return inventoryMap.get(productId);
    }
    
    public static void updateStock(String productId, int quantityChange, String changeType, String reason) {
        InventoryManager product = inventoryMap.get(productId);
        if (product != null) {
            if (changeType.equals("Remove Stock")) {
                quantityChange = -quantityChange;
            }
            product.setQuantity(product.getQuantity() + quantityChange);
            product.setLastUpdated(LocalDateTime.now().toString());
            
            // Log the change
            movementLog.add(new StockMovement(productId, changeType, quantityChange, reason));
            
            // Save changes
            saveInventory();
            saveMovementLog();
        }
    }
    
    public static List<InventoryManager> getLowStockItems() {
        List<InventoryManager> lowStockItems = new ArrayList<>();
        for (InventoryManager product : inventoryMap.values()) {
            if (product.getQuantity() <= product.getThreshold()) {
                lowStockItems.add(product);
            }
        }
        return lowStockItems;
    }
    
    public static List<InventoryManager> getLowStockItems(int threshold) {
        List<InventoryManager> lowStockItems = new ArrayList<>();
        for (InventoryManager product : inventoryMap.values()) {
            if (product.getQuantity() <= threshold) {
                lowStockItems.add(product);
            }
        }
        return lowStockItems;
    }
    
    public static void addProduct(InventoryManager product) {
        inventoryMap.put(product.getProductId(), product);
        saveInventory();
        
        // Log initial stock addition
        movementLog.add(new StockMovement(
            product.getProductId(),
            "Add Stock",
            product.getQuantity(),
            "Initial stock"
        ));
        saveMovementLog();
    }
    
    public static List<InventoryManager> getAllProducts() {
        return new ArrayList<>(inventoryMap.values());
    }
    
    public static List<StockMovement> getMovementLog() {
        return new ArrayList<>(movementLog);
    }
}
