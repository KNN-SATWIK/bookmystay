import java.util.*;

/**
 * UC7 - Add-On Services (Independent Module)
 * Version 7.0
 *
 * Demonstrates optional service attachment without modifying
 * booking or inventory logic.
 */

// ---------- Add-On Service ----------
class AddOnService {

    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    public void display() {
        System.out.println(serviceName + " | ₹" + cost);
    }
}

// ---------- Add-On Service Manager ----------
class AddOnServiceManager {

    // reservationId -> list of services
    private Map<String, List<AddOnService>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {

        serviceMap
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println("Service added to " + reservationId + ": "
                + service.getServiceName());
    }

    // View all services for a reservation
    public void viewServices(String reservationId) {

        List<AddOnService> services = serviceMap.get(reservationId);

        System.out.println("\nServices for Reservation: " + reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        for (AddOnService s : services) {
            s.display();
        }
    }

    // Calculate total cost
    public double calculateTotalCost(String reservationId) {

        List<AddOnService> services = serviceMap.get(reservationId);

        double total = 0;

        if (services != null) {
            for (AddOnService s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}

// ---------- MAIN CLASS ----------
public class uc7 {

    public static void main(String[] args) {

        System.out.println("Hotel Booking System v7.0 - Add-On Services\n");

        AddOnServiceManager manager = new AddOnServiceManager();

        // Simulated reservation IDs (external system reference)
        String res1 = "RES-101";
        String res2 = "RES-102";

        // Available services
        AddOnService wifi = new AddOnService("WiFi", 200);
        AddOnService breakfast = new AddOnService("Breakfast", 300);
        AddOnService spa = new AddOnService("Spa", 800);
        AddOnService pickup = new AddOnService("Airport Pickup", 1000);

        // Guest selects services
        manager.addService(res1, wifi);
        manager.addService(res1, breakfast);

        manager.addService(res2, spa);
        manager.addService(res2, pickup);

        // Display services
        manager.viewServices(res1);
        manager.viewServices(res2);

        // Cost calculation
        System.out.println("\nTotal Add-On Cost for " + res1 + ": ₹"
                + manager.calculateTotalCost(res1));

        System.out.println("Total Add-On Cost for " + res2 + ": ₹"
                + manager.calculateTotalCost(res2));
    }
}