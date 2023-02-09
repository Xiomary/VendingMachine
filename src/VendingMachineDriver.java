import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class VendingMachineDriver {
    public static String filename = "/Users/xio/Desktop/vending.txt"; // input filename

    public static void main(String[] args) throws IOException {

        Scanner input = new Scanner(System.in);
        VendingMachine vendingMachine = initVendingMachine();

        //creating text file
        File file = new File("/Users/xio/Desktop/vending.txt");
        System.out.println("Welcome to our vending machine!\n" + "Menu:");

        System.out.println(vendingMachine);
        System.out.println("Please select product or (x) to quit");
        String userInput = input.nextLine();
        while (!userInput.equalsIgnoreCase("x")) {
            vendingMachine.selectProduct(userInput);
            saveToFile(vendingMachine);
            System.out.println("Please select product or (x) to quit");
            userInput = input.nextLine();
        }
        System.out.println("Thank you. Come again!");
        //ending the vending machine and saving our products
        saveToFile(vendingMachine);
    }

    public static VendingMachine initVendingMachine() throws IOException {
        Scanner input = new Scanner(System.in);

        VendingMachine vendingMachine = new VendingMachine();
        //read products from file here
        Scanner filesc = new Scanner(new File(filename));
        int index = 0;

        while (filesc.hasNext()) {
            String[] split = filesc.nextLine().split(":");
            Product newProduct = new Product(split[1], Double.parseDouble(split[2]), Integer.parseInt(split[3]));
            //Add the newProduct to the vending machine
            vendingMachine.getProducts().put(split[0], newProduct);
            index++;
        }
        System.out.println();
        return vendingMachine;
    }

    public static void saveToFile(VendingMachine vendingMachine) throws IOException {
        FileWriter writer = new FileWriter(filename);
        // Iterate through hash map
        for (Map.Entry<String, Product> product : vendingMachine.getProducts().entrySet()) {
            // Printing mark corresponding to string entries
            // Write product to FileWriter writer
            Product productValue = product.getValue();
            String key = product.getKey();
            String data = (key + ":" + productValue.getName() + ":" +
                    +productValue.getPrice() + ":" +
                    +productValue.getQuantity() + "\n")
                    .toLowerCase(Locale.ROOT);

            writer.write(data);
        }
        writer.close();
    }
}
