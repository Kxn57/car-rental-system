package model;


public class Payment {
    private Invoice invoice;
    private double amount;
    private String status;
    private Booking booking;
    private String invoiceId;
    private String issueDate;

    public Payment(String paymentId, Invoice invoice, double amount, String method,
                   String paymentDate, String status, Booking booking,
                   String invoiceId, String issueDate) {
        this.invoice = invoice;
        this.amount = amount;
        this.status = status;
        this.booking = booking;
        this.invoiceId = invoiceId;
        this.issueDate = issueDate;
    }

    // Use invoice total instead
    public void processPayment() {
        double totalDue = invoice.getTotalAmount();

        if (amount >= totalDue) {
            status = "PAID";
            invoice.setPaymentStatus("PAID");
            System.out.println("Payment successful!");
        } else {
            status = "FAILED";
            System.out.println("Payment failed. Insufficient amount.");
        }
    }


    public String getStatus() {
        return status;
    }

    private void printDivider() {
        System.out.println("--------------------------------------------------");
    }

    private String formatCurrency(double amount) {
        return String.format("RM%.2f", amount);
    }

    //  FIXED: Delegate to invoice
    public double getTotalAmount() {
        return invoice.getTotalAmount();
    }

    public void printReceipt(double amountPaid, String method) {

        Customer customer = booking.getCustomer();
        Vehicle vehicle = booking.getVehicle();

        double totalCost = invoice.getBaseAmount() + invoice.getPenalty() + invoice.getTax();
        double balance = amountPaid - totalCost;

        System.out.println();
        System.out.println("============== 67 Concept Car Rental ==============");
        System.out.println("                PAYMENT RECEIPT");
        printDivider();

        System.out.println("Receipt No : " + invoiceId);
        System.out.println("Date       : " + issueDate);
        System.out.println("Method     : " + method);
        printDivider();

        System.out.println("Paid By    : " + customer.getName() + " (" + customer.getId() + ")");
        System.out.println("Contact    : " + customer.getContact());
        System.out.println("Vehicle    : " + vehicle.getId() + " - "
                + vehicle.getBrand() + " "
                + vehicle.getModel() + " ("
                + vehicle.getType() + ")");
        System.out.println("Rental     : " + booking.getDays() + " day(s)");
        printDivider();

        System.out.println(String.format("%-35s %14s", "Description", "Amount"));
        System.out.println(String.format("%-35s %14s", "Total Cost", formatCurrency(totalCost)));
        System.out.println(String.format("%-35s %14s", "Amount Paid", formatCurrency(amountPaid)));
        System.out.println(String.format("%-35s %14s", "Balance", formatCurrency(balance)));
        printDivider();

        System.out.println("Payment Status : " + status);
        System.out.println("Thank you for your payment!");
        printDivider();
    }
}