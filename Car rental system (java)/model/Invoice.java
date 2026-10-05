package model;

public class Invoice {
    private String invoiceId;
    private Booking booking;
    private double baseAmount;
    private double penalty;
    private double tax;
    private String issueDate;
    private String paymentStatus;

    public Invoice(String invoiceId, Booking booking, double baseAmount, double penalty, double tax, String issueDate, String paymentStatus) {
        this.invoiceId = invoiceId;
        this.booking = booking;
        this.baseAmount = baseAmount;
        this.penalty = penalty;
        this.tax = tax;
        this.issueDate = issueDate;
        this.paymentStatus = paymentStatus;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public Booking getBooking() {
        return booking;
    }

    public double getBaseAmount() {
         if (paymentStatus.equalsIgnoreCase("PAID")) {
        // ✅ already paid → ONLY penalty (no tax)
        return baseAmount;
    }

    // ❌ not paid → full amount with tax
    return baseAmount;
    }
    

    public double getPenalty() {
        return penalty;
    }

    public double getTax() {
        return tax;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPenalty(double penalty) {
        this.penalty = penalty;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public double getTotalAmount() {
        return baseAmount + penalty + tax;
    }

    private String formatCurrency(double amount) {
        return String.format("RM %,.2f", amount);
    }

    private void printDivider() {
        System.out.println("--------------------------------------------------");
    }


    public void printInvoice() {
        Customer customer = booking.getCustomer();
        Vehicle vehicle = booking.getVehicle();

        System.out.println();
        System.out.println("============== 67 Concept Car Rental ==============");
        System.out.println("                TAX INVOICE");
        printDivider();
        System.out.println("Invoice No : " + invoiceId);
        System.out.println("Issue Date : " + issueDate);
        System.out.println("Status     : " + paymentStatus);
        printDivider();
        System.out.println("Billed To  : " + customer.getName() + " (" + customer.getId() + ")");
        System.out.println("Contact    : " + customer.getContact());
        System.out.println("Vehicle    : " + vehicle.getId() + " - " + vehicle.getBrand() + " " + vehicle.getModel() + " (" + vehicle.getType() + ")");
        System.out.println("Rental     : " + booking.getDays() + " day(s)");
        printDivider();
        System.out.println(String.format("%-35s %14s", "Description", "Amount"));

        if (!paymentStatus.equalsIgnoreCase("PAID")) {
        System.out.println(String.format("%-35s %14s", "Base rental fee", formatCurrency(baseAmount)));
        System.out.println(String.format("%-35s %14s", "Late return penalty", formatCurrency(penalty)));
        System.out.println(String.format("%-35s %14s", "Tax (6%)", formatCurrency(tax)));
    } else {
        System.out.println(String.format("%-35s %14s", "Base rental fee", "PAID"));
        System.out.println(String.format("%-35s %14s", "Late return penalty", formatCurrency(penalty)));
        System.out.println(String.format("%-35s %14s", "Tax (6%)", "N/A"));
    }
        System.out.println(String.format("%-35s %14s", "TOTAL DUE", formatCurrency(getTotalAmount())));
        printDivider();
        System.out.println("Thank you for choosing 67 Concept Car Rental.");
        
    }

    public void lateReturnInvoice() {
        Customer customer = booking.getCustomer();
        Vehicle vehicle = booking.getVehicle();

        System.out.println();
        System.out.println("============== 67 Concept Car Rental ==============");
        System.out.println("             LATE RETURN INVOICE");
        printDivider();
        System.out.println("Invoice No : " + invoiceId);
        System.out.println("Issue Date : " + issueDate);
        System.out.println("Status     : " + paymentStatus);
        printDivider();
        System.out.println("Billed To  : " + customer.getName() + " (" + customer.getId() + ")");
        System.out.println("Contact    : " + customer.getContact());
        System.out.println("Vehicle    : " + vehicle.getId() + " - " + vehicle.getBrand() + " " + vehicle.getModel() + " (" + vehicle.getType() + ")");
        System.out.println("Rental     : " + booking.getDays() + " day(s)");
        printDivider();
        System.out.println(String.format("%-35s %14s", "Description", "Amount"));
        System.out.println(String.format("%-35s %14s", "Late return penalty", formatCurrency(penalty)));
        System.out.println(String.format("%-35s %14s", "TOTAL DUE", formatCurrency(penalty)));
        printDivider();
        System.out.println("Thank you for choosing 67 Concept Car Rental.");
    }

    
    @Override
    public String toString() {
        return "Invoice ID: " + invoiceId +
                ", Total Amount: RM" + getTotalAmount() +
                ", Payment Status: " + paymentStatus;
    }
}

