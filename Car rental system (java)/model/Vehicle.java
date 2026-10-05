package model;

public class Vehicle {
    private String id;
    private String brand;
    private String model;
    private String type;
    private double dailyRate;
    private int mileage;
    private boolean available;

    public Vehicle(String id, String brand, String model, String type, double dailyRate) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.dailyRate = dailyRate;
        this.available = true; // default to available
        

        this.mileage = 0;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getMileage() {
        return mileage;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getType() {
        return type;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public double getDailyRate() {
        return dailyRate;
    }



    @Override
    public String toString() {
        String vehicleName = "[" + id + "] " + brand + " " + model;
        String vehicleType = "(" + type + ")";
        String availability = isAvailable() ? "Yes" : "No";

        return String.format("%-26s %-13s RM%-7.1f | Mileage: %-5d | Available: %-3s",
                vehicleName, vehicleType, dailyRate, mileage, availability);
    }
}
