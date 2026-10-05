package model;
public class Booking {
    private Customer customer;
    private Vehicle vehicle;
    private int days;
    private double totalCost;
    private boolean returned;
    public double getTotalCost() {return totalCost;}
    private Invoice invoice;

   public Booking(Customer customer, Vehicle vehicle, int days, double totalCost) {
    this.customer = customer;
    this.vehicle = vehicle;
    this.days = days;
    this.totalCost = totalCost;
    this.returned = false;
    }

    public void setInvoice(Invoice invoice) {
    this.invoice = invoice;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }

    public boolean isReturned() {
        return returned;
    }

    public void returnCar() {
        this.returned = true;
        vehicle.setAvailable(true);
    }

    public Invoice getInvoice() {
        return invoice;
    }

    @Override
    public String toString() {
    return String.format(
        "%s (C:%s) | %s %s | %d day(s) | RM%.2f",
        customer.getName(),
        customer.getContact(),
        vehicle.getBrand(),
        vehicle.getModel(),
        days,
        totalCost
    );
}
}
