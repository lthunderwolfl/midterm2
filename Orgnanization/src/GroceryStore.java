import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Product class
class Product {
    private String name;
    private double price;
    private int quantity;

    // Constructor
    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    // toString method for easy printing
    @Override
    public String toString() {
        return "Product{name='" + name + "', price=" + price + ", quantity=" + quantity + '}';
    }

    // Method to get the CSV format of the product
    public String toCSV() {
        return name + "," + price + "," + quantity;
    }

    // Method to create a Product from a CSV string
    public static Product fromCSV(String csv) {
        String[] parts = csv.split(",");
        String name = parts[0];
        double price = Double.parseDouble(parts[1]);
        int quantity = Integer.parseInt(parts[2]);
        return new Product(name, price, quantity);
    }
}

// GroceryStore class
class GroceryStore {
    private String name;
    private List<Product> products;

    // Constructor
    public GroceryStore(String name) {
        this.name = name;
        this.products = new ArrayList<>();
    }

    // Method to add product to the store
    public void addProduct(Product product) {
        products.add(product);
    }

    // Method to remove product from the store
    public void removeProduct(Product product) {
        products.remove(product);
    }

    // Method to remove product by name
    public void removeProductByName(String productName) {
        products.removeIf(product -> product.getName().equals(productName));
    }

    // Method to save the current state to a CSV file
    public void saveState() {
        try (FileWriter writer = new FileWriter("state.csv")) {
            for (Product product : products) {
                writer.write(product.toCSV() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to restore the state from a CSV file
    public void restoreState() {
        products.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("state.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                products.add(Product.fromCSV(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getters
    public String getName() {
        return name;
    }

    public List<Product> getProducts() {
        return products;
    }

    // toString method for easy printing
    @Override
    public String toString() {
        return "GroceryStore{name='" + name + "', products=" + products + '}';
    }

    // Main method for example usage
    public static void main(String[] args) {
        // Create some products
        Product apple = new Product("Apple", 0.5, 100);
        Product bread = new Product("Bread", 2.0, 50);
        Product banana = new Product("Banana", 1.5,25);

        // Create a grocery store
        GroceryStore store = new GroceryStore("My Grocery Store");

        // Add products to the grocery store
        store.addProduct(apple);
        store.addProduct(bread);
        store.addProduct(banana);

        // Print the grocery store's details
        System.out.println("Before saving state: " + store);

        // Save the state to a file
        store.saveState();

        // Remove products to demonstrate restoring state
        store.removeProduct(apple);
        store.removeProduct(bread);

        // Print the grocery store's details after removal
        System.out.println("After removing products: " + store);

        // Restore the state from the file
        store.restoreState();

        // Print the grocery store's details after restoring
        System.out.println("After restoring state: " + store);
    }
}
