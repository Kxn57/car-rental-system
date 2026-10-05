package service;
import model.*;
import java.util.*;
public class RentalSystem {
    private List<Vehicle> vehicles = new ArrayList<>(); //Make a storage to store vehicle
    private List<Customer> customers = new ArrayList<>(); //Store customer details
    private List<Booking> bookings = new ArrayList<>(); //Store booking details
    private List<Payment> payments = new ArrayList<>();
    private List<Invoice> invoices = new ArrayList<>();
    public List<Vehicle> getVehicles() {return vehicles;}
    public List<Booking> getBookings() {return bookings;}
    public List<Customer> getCustomers() {return customers;}

    //Add vehicle to the system
    public boolean addVehicle(Vehicle vehicle) {

        for (Vehicle v : vehicles) {
            if (v.getId().equals(vehicle.getId())) {
                return false;
            }
        }

        vehicles.add(vehicle);
        return true;
    }

    // Return car
    public Invoice returnCar(Booking b, int lateDays) {

        b.returnCar(); // mark returned + make vehicle available

        if (lateDays == 0) {
            //  no late
            System.out.println(" Car returned successfully. Thank you!");
            return null;

        } else {

            double penalty = lateDays * b.getVehicle().getDailyRate();

            System.out.println(" Late return! Penalty: RM " + penalty);

            Invoice invoice = new Invoice(
                    "INV" + (invoices.size() + 1),
                    b,
                    0,
                    penalty,
                    0,
                    java.time.LocalDate.now().toString(),
                    "UNPAID"
            );

            invoices.add(invoice);
            invoice.lateReturnInvoice();
            return invoice;
        }

    }


    public void updateMileage(String vehicleId, int mileage) {
        for (Vehicle v : vehicles) {
            if (v.getId().equals(vehicleId)) {
                v.setMileage(mileage);
                System.out.println("Mileage updated!");
                return;
            }
        }
        System.out.println("Vehicle not found!");
    }

    //Show available car
    public void showAvailableVehicles() {
        System.out.println("\n=========== VEHICLE LIST ===========");

        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle v = vehicles.get(i);

            String status = v.isAvailable() ? "Available" : "Not Available";

            System.out.println(i + " - " + v );
        }

        System.out.println("====================================");
    }

    public Booking createBooking(Customer customer, Vehicle v, int days, double total) {

        if (!v.isAvailable()) {
            System.out.println("Car not available!");
            return null;
        }

        // DISCOUNT
        if (days >= 30) total *= 0.7;
        else if (days >= 5) total *= 0.85;
        else if (days >= 3) total *= 0.9;

        // POINT SYSTEM
        int points = (int)(total / 50) * 500;
        customer.addPoints(points);

        Booking b = new Booking(customer, v, days, total);

        bookings.add(b);

        Invoice inv = generateInvoice(b, 0);
        b.setInvoice(inv);

        v.setAvailable(false);

        return b;
    }

    //invoice
    public Invoice generateInvoice(Booking b, double penalty) {

        double base = b.getTotalCost();
        double tax = base * 0.06; // 6% tax

        Invoice invoice = new Invoice(
                "INV" + (invoices.size() + 1),
                b,
                base,
                penalty,
                tax,
                java.time.LocalDate.now().toString(),
                "UNPAID"
        );

        invoices.add(invoice);

        System.out.println("Invoice generated!");
        invoice.printInvoice();

        return invoice;
    }

    //make payment
    public boolean makePayment(Invoice invoice, double amount, String method) {

        Payment payment = new Payment(
                "PAY" + (payments.size() + 1),
                invoice,
                amount,
                method,
                java.time.LocalDate.now().toString(),
                "PENDING",
                invoice.getBooking(),
                invoice.getInvoiceId(),
                invoice.getIssueDate()
        );

        payment.processPayment();

        if (payment.getStatus().equalsIgnoreCase("PAID")) {
            invoice.setPaymentStatus("PAID");
            payment.printReceipt(amount, method);
            System.out.println("\nPress ENTER to return to menu...");
            new java.util.Scanner(System.in).nextLine();
            payments.add(payment);
            return true;
        }

        return false;
    }

    public void addPayment(Payment payment) {
        payments.add(payment);
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void showAllPayments() {
        for (Payment payment : payments) {
            System.out.println(payment);
        }
    }


//list vehicle



    public static List<Vehicle> getDefaultVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();

        vehicles.add(new Vehicle("V001", "Toyota"  , "Vios"  , "Sedan"    , 240.0));
        vehicles.add(new Vehicle("V002", "Toyota"  , "Camry" , "Sedan"    , 450.0));
        vehicles.add(new Vehicle("V003", "Honda"   , "City"  , "Sedan"    , 250.0));
        vehicles.add(new Vehicle("V004", "Honda"   , "Civic" , "Sedan"    , 350.0));
        vehicles.add(new Vehicle("V005", "Perodua" , "Axia"  , "Hatchback", 120.0));
        vehicles.add(new Vehicle("V006", "Perodua" , "Myvi"  , "Hatchback", 160.0));
        vehicles.add(new Vehicle("V007", "BMW"     , "320i"  , "Sedan"    , 300.0));
        vehicles.add(new Vehicle("V008", "BMW"     , "530i"  , "Sedan"    , 750.0));
        vehicles.add(new Vehicle("V009", "Mercedes", "A200"  , "Sedan"    , 240.0));
        vehicles.add(new Vehicle("V010", "Mercedes", "C200"  , "Sedan"    , 650.0));

        return vehicles;
    }

    //Set available car
    public void setAvailability(String vehicleId, boolean status) {
        for (Vehicle v : vehicles) {
            if (v.getId().equals(vehicleId)) {
                v.setAvailable(status);
                System.out.println(" Availability updated!");
                return;
            }
        }
        System.out.println(" Vehicle not found!");
    }



    public RentalSystem() {
        vehicles = getDefaultVehicles(); // 🔥 load default cars

    }}
