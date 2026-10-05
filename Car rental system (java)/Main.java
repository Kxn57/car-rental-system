import java.util.Scanner;
import model.*;
import service.RentalSystem;

public class Main {

    public static void printHeader() {
        System.out.println("===============================================================");
        System.out.println("    ██████     ███████╗    CONCEPT");
        System.out.println("   ██          ╚════██║      CAR");
        System.out.println("   ██████         ██╔╝         RENTAL");
        System.out.println("   ██   ██       ██╔╝            SHOP");
        System.out.println("   ██████       ██╔╝");
        System.out.println("                ╚═╝");
        System.out.println("");
        System.out.println("        67 CONCEPT CAR RENTAL SHOP ");
        System.out.println("---------------------------------------------------------------");
        System.out.println("     \"Sell yourself before you rent the car\"");
        System.out.println("===============================================================");
    }

    public static void main(String[] args) {

        RentalSystem system = new RentalSystem();
        Scanner sc = new Scanner(System.in);

        //  MAIN LOOP
        while (true) {

            printHeader();

            System.out.println("=== LOGIN AS ===");
            System.out.println("1. Admin");
            System.out.println("2. Customer");

            int role = readMenuChoice(sc, "Enter your choice > ", 1, 2);

            // ================= ADMIN =================
            if (role == 1) {
                System.out.print("Enter admin password (or type 0 to go back): ");
                String pass = sc.next();

                while (true) {
                    if (pass.equals("0")) {
                        break;
                    }

                    if (pass.equals("admin123")) {
                        System.out.println("Login successful!");
                        break;
                    } else {
                        System.out.println(" Wrong password!");
                        System.out.print("Enter admin password (or 0 to go back): ");
                        pass = sc.next();
                    }
                }

                //  if user chose back → skip admin menu
                if (!pass.equals("admin123")) {
                    continue; // go back to login menu
                }
                boolean adminRunning = true;

                while (adminRunning) {
                    System.out.println("\n=== ADMIN MENU ===");
                    System.out.println("1. Add Vehicle");
                    System.out.println("2. Show Cars");
                    System.out.println("3. Update Mileage");
                    System.out.println("4. Set Availability");
                    System.out.println("5. Exit");

                    int choice = readMenuChoice(sc, "Enter your choice > ", 1, 5);

                    switch (choice) {
                        case 1:
                            sc.nextLine();

                            String id;

                            while (true) {
                                system.showAvailableVehicles();
                                System.out.print("Enter Vehicle ID (format V001): ");
                                id = sc.nextLine();

                                if (id.matches("V\\d{3}")) {
                                    break; // valid
                                } else {
                                    System.out.println(" Invalid ID format! Use V001, V002...");
                                }
                            }

                            System.out.print("Enter Brand: ");
                            String brand = sc.nextLine();

                            System.out.print("Enter Model: ");
                            String model = sc.nextLine();

                            System.out.print("Enter Type: ");
                            String type = sc.nextLine();

                            System.out.print("Enter Daily Rate: ");
                            double rate = sc.nextDouble();

                            Vehicle v = new Vehicle(id, brand, model, type, rate);

                            if (system.addVehicle(v)) {
                                System.out.println(" Vehicle added successfully!");
                            } else {
                                System.out.println(" Vehicle ID already exists!");
                            }
                            break;

                        case 2:
                            system.showAvailableVehicles();
                            break;

                        case 3:
                            sc.nextLine();
                            system.showAvailableVehicles();



                            while (true) {
                                System.out.print("Enter Vehicle ID (format V001): ");
                                id = sc.nextLine();

                                if (id.matches("V\\d{3}")) {
                                    break; // valid
                                } else {
                                    System.out.println(" Invalid ID format! Use V001, V002...");
                                }
                            }

                            System.out.print("Enter new mileage: ");
                            int mileage = sc.nextInt();

                            system.updateMileage(id, mileage);
                            break;

                        case 4:
                            sc.nextLine();

                            system.showAvailableVehicles();

                            System.out.print("Enter Vehicle ID: ");
                            String vid = sc.nextLine();

                            System.out.print("Set Available? (true/false): ");
                            boolean status = sc.nextBoolean();

                            system.setAvailability(vid, status);
                            break;

                        case 5:
                            adminRunning = false; //back to login
                            break;
                    }
                }
            }

            // ================= CUSTOMER =================
            else if (role == 2) {

                boolean customerRunning = true;

                while (customerRunning) {
                    System.out.println("\n=== CUSTOMER MENU ===");
                    System.out.println("1. Show Cars");
                    System.out.println("2. Book Car");
                    System.out.println("3. Return Car");
                    System.out.println("4. Exit");

                    int choice = readMenuChoice(sc, "Enter your choice > ", 1, 4);

                    switch (choice) {
                        //show car
                        case 1:
                            system.showAvailableVehicles();
                            break;
                        //customer detail
                        case 2:
                            system.showAvailableVehicles();
                            sc.nextLine();

                            String name;
                            while (true) {
                                name = readNonEmptyInput(sc, "Enter Name: ");

                                if (name.matches("[A-Za-z ]+")) {
                                    break;
                                }

                                System.out.println(" Invalid name! Format: letters and spaces only. Example: Louis Tan");
                            }

                            String contact;
                            while (true) {
                                contact = readNonEmptyInput(sc, "Enter Contact: ");

                                if (contact.matches("\\d{10,11}")) {
                                    break;
                                }

                                System.out.println(" Invalid contact! Format: 10 or 11 digits only. Example: 0123456789");
                            }

                            Customer customer = new Customer("C1", name,  contact);

                            int index;


                            //only can number and in range
                            while (true) {
                                System.out.print("Enter vehicle index: ");

                                if (sc.hasNextInt()) {
                                    index = sc.nextInt();

                                    // check valid range
                                    if (index >= 0 && index < system.getVehicles().size()) {
                                        if (system.getVehicles().get(index).isAvailable()) {
                                            break;
                                        } else {
                                            System.out.println(" This car is unavailable! Please choose another car.");
                                            system.showAvailableVehicles();
                                        }
                                    } else {
                                        System.out.println(" Invalid index! Try again.");
                                    }
                                } else {
                                    System.out.println(" Please enter numbers only!");
                                    sc.next(); // clear wrong input
                                }
                            }

                            int days;
                            while (true) {
                                System.out.print("Enter days: ");

                                if (sc.hasNextInt()) {
                                    days = sc.nextInt();

                                    if (days > 0 && days <= 365) {
                                        break;
                                    }
                                } else {
                                    sc.next();
                                }

                                System.out.println(" Invalid days! Enter 1 to 365 only.");
                            }

                            Vehicle vehicle = system.getVehicles().get(index);

                            double total = days * vehicle.getDailyRate();

                            Booking b = system.createBooking(customer, vehicle, days, total);

                            if (b != null) {
                                System.out.println(" \nBooking successful!");

                                // 🎁 ADD POINTS HERE
                                int earnedPoints = (int) total / 10; // example: RM10 = 1 point
                                customer.addPoints(earnedPoints);
                                System.out.println("========================================");
                                System.out.println(" You earned " + earnedPoints + " points!");
                                System.out.println("========================================");

                                System.out.println(" \nTotal points: " + customer.getPoints() + "\n");

                                Invoice inv = b.getInvoice();

                                sc.nextLine();

                                String pay ;

                                while (true) {
                                    System.out.print("Pay now? (yes/no): ");
                                    pay = sc.nextLine();

                                    if (pay.equalsIgnoreCase("yes") || pay.equalsIgnoreCase("no")) {
                                        break;
                                    }

                                    System.out.println(" Invalid input! Please enter only 'yes' or 'no'.");
                                }



                                if (pay.equalsIgnoreCase("yes")) {

                                    String method = readPaymentMethod(sc);
                                    double amount = 0;

                                    if (method.equalsIgnoreCase("cash")) {
                                        amount = readPositiveDouble(sc, "Enter amount: ");
                                    }
                                    else if (method.equalsIgnoreCase("credit card")) {
                                        System.out.print("Enter credit card number: ");
                                        String cardNumber = sc.next();
                                        amount = readPositiveDouble(sc, "Enter amount: ");
                                    }


                                    system.makePayment(inv, amount, method);
                                }
                            }

                            break;


                        case 3:

                            //  Show bookings FIRST
                            System.out.println("=== BOOKING LIST ===");

                            if (system.getBookings().isEmpty()) {
                                System.out.println(" No bookings found!");
                                break;
                            }

                            for (int i = 0; i < system.getBookings().size(); i++) {
                                System.out.println(i + " - " + system.getBookings().get(i));
                            }

                            int bookingIndex;

                            //  Now user can choose correctly
                            while (true) {
                                System.out.print("Enter booking index: ");

                                if (sc.hasNextInt()) {
                                    bookingIndex = sc.nextInt();

                                    if (bookingIndex >= 0 && bookingIndex < system.getBookings().size()) {
                                        break;
                                    } else {
                                        System.out.println(" Invalid index! Try again.");
                                    }
                                } else {
                                    System.out.println(" Please enter numbers only!");
                                    sc.next();
                                }
                            }

                            System.out.print("Enter late days (0 if no late): ");
                            int lateDays = sc.nextInt();

                            Booking booking = system.getBookings().get(bookingIndex);
                            Invoice lateInvoice = system.returnCar(booking, lateDays);

                            if (lateInvoice != null) {
                                sc.nextLine();
                                boolean paid = false;

                                while (!paid) {
                                    String method = readPaymentMethod(sc);
                                    double amount = 0;

                                    if (method.equalsIgnoreCase("cash")) {
                                        amount = readPositiveDouble(sc, "Enter amount: ");
                                    } else if (method.equalsIgnoreCase("credit card")) {
                                        System.out.print("Enter credit card number: ");
                                        String cardNumber = sc.next();
                                        amount = readPositiveDouble(sc, "Enter amount: ");
                                    }

                                    paid = system.makePayment(lateInvoice, amount, method);

                                    if (!paid) {
                                        sc.nextLine();
                                        System.out.println(" Payment failed. Please try again.");
                                    }
                                }
                            }

                            system.getBookings().remove(booking);

                            break;

                        case 4:
                            customerRunning = false; // back to login
                            break;
                    }
                }
            }
        }
    }

    public static int readMenuChoice(Scanner sc, String message, int min, int max) {
        int choice;

        while (true) {
            System.out.print(message);

            if (sc.hasNextInt()) {
                choice = sc.nextInt();

                if (choice >= min && choice <= max) {
                    return choice;
                }
            } else {
                sc.next();
            }

            System.out.println(" Invalid choice! Please try again.");
        }
    }

    public static String readYesNoChoice(Scanner sc, String message) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim().toLowerCase();

            if (input.equals("yes") || input.equals("no")) {
                return input;
            }

            System.out.println(" Invalid input! Please enter only 'yes' or 'no'.");
        }
    }

    public static double readPositiveDouble(Scanner sc, String message) {
        while (true) {
            System.out.print(message);

            if (sc.hasNextDouble()) {
                double value = sc.nextDouble();
                if (value > 0) {
                    return value;
                }
                System.out.println(" Amount must be more than 0.");
            } else {
                System.out.println(" Please enter numbers only!");
                sc.next();
            }
        }
    }

    public static String readNonEmptyInput(Scanner sc, String message) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println(" Input cannot be empty. Please try again.");
        }
    }

    public static String readPaymentMethod(Scanner sc) {
        while (true) {
            System.out.println("Select payment method:");
            System.out.println("1. Cash");
            System.out.println("2. Credit Card");
            System.out.println("3. Online Banking");
            System.out.print("Enter choice (1-3): ");

            if (sc.hasNextInt()) {
                int choice = sc.nextInt();
                if (choice == 1) return "Cash";
                if (choice == 2) return "Credit Card";
                if (choice == 3) return "Online Banking";
                System.out.println(" Invalid choice! Please select 1, 2, or 3.");
            } else {
                System.out.println(" Invalid input! Please enter numbers only.");
                sc.next();
            }
        }
    }
}
