import java.util.Scanner;

class Dish {
    String name;
    double price;
    double rating;

    public Dish(String name, double price, double rating) {
        this.name = name;
        this.price = price;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return name + " - ₹" + price + " | Rating: " + rating;
    }
}

class Menu {
    String category;
    Dish[] dishes;

    public Menu(String category, Dish[] dishes) {
        this.category = category;
        this.dishes = dishes;
    }
}

public class CafePOS {
    private static Scanner scanner = new Scanner(System.in);
    private static Menu[] menus = {
        new Menu("Coffee", new Dish[]{
            new Dish("Instant Coffee", 30, 4.5),
            new Dish("Latte", 50, 4.4),
            new Dish("Americano", 70, 4.8),
            new Dish("Mocca", 40, 4.6)
        }),
        new Menu("Snacks", new Dish[]{
            new Dish("Mini Veg Pizza", 110, 4.7),
            new Dish("Veg Cheese Pizza", 170, 4.8),
            new Dish("Veg Burger", 75, 4.9)
        }),
        new Menu("Coffeee", new Dish[]{
            new Dish("Cappuccino", 45, 4.9),
            new Dish("Espresso", 60, 4.8),
            new Dish("Frappe", 55, 4.7)
        }),
        new Menu("Snackss", new Dish[]{
            new Dish("Veg Cheese Burger", 100, 4.9),
            new Dish("Veg Grilled Sandwich", 90, 4.8),
            new Dish("Paneer Grilled Sandwich", 150, 4.7)
        })
    };

    public static void main(String[] args) {
        System.out.println("Welcome to the Cafe!");

        // Collect customer name
        System.out.print("Enter your name: ");
        String customerName = scanner.nextLine();
        while (!customerName.matches("[a-zA-Z ]+")) {
            System.out.println("Invalid input. Name should only contain alphabets.");
            System.out.print("Enter your name: ");
            customerName = scanner.nextLine();
        }

        // Collect customer contact
        System.out.print("Enter your contact number: ");
        String contactNumber = scanner.nextLine();
        while (!contactNumber.matches("\\d{10}")) {
            System.out.println("Invalid input. Contact number should be exactly 10 digits.");
            System.out.print("Enter your contact number: ");
            contactNumber = scanner.nextLine();
        }

        // Ask for preference
        System.out.print("Do you prefer Coffee or Snacks? (Coffee/Snacks): ");
        String preference = scanner.nextLine().toLowerCase();
        while (!preference.equals("coffee") && !preference.equals("snacks")) {
            System.out.println("Invalid input. Please enter 'Coffee' or 'Snacks'.");
            System.out.print("Do you prefer Coffee or Snacks? (Coffee/Snacks): ");
            preference = scanner.nextLine().toLowerCase();
        }

        // Display menu
        System.out.println("\nMenu:");
        int serial = 1;
        Dish[] availableDishes = (preference.equals("coffee"))
            ? concatenateDishes(menus[0].dishes, menus[2].dishes)
            : concatenateDishes(menus[1].dishes, menus[3].dishes);

        for (Dish dish : availableDishes) {
            System.out.println(serial++ + ". " + dish.toString());
        }

        // Select dishes
        System.out.println("\nSelect dishes by serial number (comma-separated, e.g., 1,2): ");
        String[] selectedDishes = scanner.nextLine().split(",");
        double total = 0;
        StringBuilder orderDetails = new StringBuilder();

        for (String index : selectedDishes) {
            try {
                int dishIndex = Integer.parseInt(index.trim()) - 1;
                if (dishIndex >= 0 && dishIndex < availableDishes.length) {
                    Dish dish = availableDishes[dishIndex];
                    orderDetails.append(dish.name).append(" - ₹").append(dish.price).append("\n");
                    total += dish.price;
                } else {
                    System.out.println("Invalid dish number: " + index.trim());
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input for serial number: " + index.trim());
            }
        }

        // Generate receipt
        System.out.println("\n--- Receipt ---");
        System.out.println("Customer Name: " + customerName);
        System.out.println("Contact Number: " + contactNumber);
        System.out.println("\nYour Order:\n" + orderDetails);
        System.out.printf("Gross Total: ₹%.2f%n", total);
        double cgst = total * 0.06;
        double sgst = total * 0.06;
        double totalWithTax = total + cgst + sgst;
        System.out.printf("CGST (6%%): ₹%.2f%n", cgst);
        System.out.printf("SGST (6%%): ₹%.2f%n", sgst);
        System.out.printf("Total (with taxes): ₹%.2f%n", totalWithTax);
        double tip = totalWithTax * 0.15;
        double netTotal = totalWithTax + tip;
        System.out.printf("Tip (15%%): ₹%.2f%n", tip);
        System.out.printf("Net Total: ₹%.2f%n", netTotal);

        // Payment options
        System.out.println("\nPayment Options:");
        System.out.println("1. Cash");
        System.out.println("2. Debit/Credit Card");
        System.out.println("3. UPI");
        System.out.print("Select payment option: ");
        int paymentOption = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (paymentOption) {
            case 1:
                System.out.println("Activating cash recognition machine...");
                break;
            case 2:
                System.out.println("Activating card swiping machine...");
                break;
            case 3:
                System.out.println("Displaying cafe's QR code...");
                System.out.println("[QR CODE IMAGE]");
                break;
            default:
                System.out.println("Invalid payment option selected.");
        }

        System.out.println("\nThank you for visiting!");
    }

    private static Dish[] concatenateDishes(Dish[] menu1, Dish[] menu2) {
        Dish[] result = new Dish[menu1.length + menu2.length];
        System.arraycopy(menu1, 0, result, 0, menu1.length);
        System.arraycopy(menu2, 0, result, menu1.length, menu2.length);
        return result;
    }
}