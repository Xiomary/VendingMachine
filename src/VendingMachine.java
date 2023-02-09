import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class VendingMachine {


    //HashMap
    private HashMap<String, Product> products = new HashMap<>();

    public HashMap<String, Product> getProducts() {
        return this.products;
    }

    public void setProducts(HashMap<String, Product> products) {
        this.products = products;
    }

    public Product selectProduct(String selection) {
        int choice = 0;
        Scanner input = new Scanner(System.in);
        if (this.products.containsKey(selection)) {
            Product product = (Product) this.products.get(selection);
            if (product.getQuantity() > 0) {
                System.out.printf("\nYour item %s costs $%.2f.  please enter the amount of money: $", product.getName(), product.getPrice());
                try {
                    double amount = input.nextDouble();
                    if (amount < 5.0) {
                        changeCalculator(selection, amount);
                        System.out.println(this);
                    }
                } catch (Exception e) {
                    System.out.println("Wrong input"); // Checks for invalid input
                    input.nextLine();
                }

            } else {
                //restock
                final int RESTOCK = 10;
                // Item is restocked when re-selecting
                System.out.printf("Item %s is out-of-stock\n", product.getName());
                // RESTOCK ITEM TO 10 HERE
                product.setQuantity(RESTOCK);
                System.out.println(this);
            }
        }
        return null;
    }

    public String toString() {
        Iterator var2 = this.products.entrySet().iterator();
        String result = "";
        System.out.printf("%-1s %5s %10s %5s", "Item#", "Item", "Price", "QTY\n");
        while (var2.hasNext()) {
            Map.Entry<String, Product> product = (Map.Entry) var2.next();
            result += String.format("%-5s %-10s %.2f    %.0f\n", (product.getKey()), (product.getValue().getName()), product.getValue().getPrice(), Float.valueOf(product.getValue().getQuantity()));
        }
        return result;
    }

    public void changeCalculator(String selection, double amount) {
        double price;
        double paid;
        int ones;
        int pennies, nickels, dimes, quarters;
        Product product = products.get(selection);
        if (amount >= product.getPrice() && amount <= 5.0) {
            double change = amount - product.getPrice();
            DecimalFormat df1 = new DecimalFormat("0.00");
            //deduct the quantity of item only when purchase is successful
            product.setQuantity(product.getQuantity() - 1);
            System.out.println("Your change is $" + df1.format(change) + ".");
            System.out.println("You will get back:");
            // cover change into cent and round off all change
            change = (int) Math.round(change * 100);
            // coverting into dollar
            ones = (int) (change / 100);
            change = change - 100 * ones;

            quarters = (int) (change / 25);
            change = change - 25 * quarters;

            dimes = (int) (change / 10);
            change = change - 10 * dimes;

            nickels = (int) (change / 5);
            change = change - 5 * nickels;

            pennies = (int) (change / 1);
            change = change - 1 * pennies;

            System.out.println(" " + ones + " one-dollar bills");
            System.out.println(" " + quarters + " quarters");
            System.out.println(" " + dimes + " dimes");
            System.out.println(" " + nickels + " nickels");
            System.out.println(" " + pennies + " pennies\n");
        } else {
            System.out.println("Insufficient funds. Reselect item and insert cash again.\n");
        }
    }

}
