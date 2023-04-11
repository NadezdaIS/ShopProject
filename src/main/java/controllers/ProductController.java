package controllers;

import entities.Product;
import services.ProductServices;
import services.UserServices;

import java.util.List;
import java.util.Scanner;

import static controllers.MenuController.currentUser;
import static controllers.MenuController.productServices;

public class ProductController {
    private static Scanner scanner = new Scanner(System.in);

    static void buyProducts(ProductServices productServices) {
        String name = UserController.getInput("Enter the name of the product: ");
        int quantity = Integer.parseInt(UserController.getInput("Enter the quantity you want to buy: "));
        Product product = productServices.getProductByName(name);
        if (product != null && product.getQuantity() >= quantity) {
            double totalPrice = quantity * product.getPrice();
            if (currentUser.getBalance() >= totalPrice) {
                product.setQuantity(product.getQuantity() - quantity);
                productServices.updateProduct(product);
                currentUser.setBalance(currentUser.getBalance() - totalPrice);
                UserServices.updateUser(currentUser);

                System.out.println("Purchase successful. Total cost: " + totalPrice);
                System.out.println("Remaining balance: " + currentUser.getBalance());
            } else {
                System.out.println("Error: not enough money to buy product.");
            }
        } else {
            System.out.println("Error: item is sold out.");
        }
    }

    static void viewPurchaseHistory() {

    }

    static void addNewProduct(ProductServices productServices) {

            if (currentUser.getRole() != "sales_representative") {
                System.out.println("Error: Only sales representatives can add new products.");
                return;
            }

            String name = UserController.getInput("Enter product name: ");
            int quantity = Integer.parseInt(UserController.getInput("Enter quantity: "));
            double price = Double.parseDouble(UserController.getInput("Enter cost price: "));

            Product product = new Product(name, quantity, price);

            productServices.save(product);

            System.out.println("Product added successfully.");

    }

    static void viewSalesReport() {
        if (!currentUser.getRole().equals("sales_representative")) {
            System.out.println("Error: Only sales representatives can view sales report.");
            return;
        }

        List<Product> products = productServices.getAllProducts();
        double totalSales = 0.0;

        System.out.printf("%-20s %-15s %-15s %-15s %-15s\n",
                "Name", "Price", "Quantity sold", "Total sales");
        for (Product product : products) {
            double sales = product.getPrice() * product.getQuantitySold(product.getId());
            totalSales += sales;
            System.out.printf("%-20s $%-14.2f %-15d $%-14.2f\n",
                    product.getName(), product.getPrice(),
                    product.getQuantitySold(product.getId()), sales);
        }

        System.out.println("--------------------------------------------------------");
        System.out.printf("Total Sales: $%.2f\n", totalSales);
    }

}
