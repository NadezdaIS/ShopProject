package controllers;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import entities.User;

import services.ProductServices;
import services.UserServices;

import java.util.Scanner;

public class MenuController {
    private static Scanner scanner = new Scanner(System.in);
    static User currentUser;
    private SessionFactory sessionFactory;
    static ProductServices productServices;
    private static UserServices userServices;

    public MenuController() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        productServices = new ProductServices();
        productServices.setSessionFactory(sessionFactory);
        userServices = new UserServices();
        userServices.setSessionFactory(sessionFactory);
    }

    public static void start() {
        System.out.println("Welcome to the shop! Choose an option:");
        System.out.println("1. Sign up:");
        System.out.println("2. Log in");
        int choice = Integer.parseInt(UserController.getInput("Enter your choice: "));
        switch (choice) {
            case 1:
                UserController.collectUserDataAndCreateUser();
            case 2:
                while (currentUser == null) {
                    System.out.println("Please log in to continue:");
                    String email = UserController.getInput("Email: ");
                    String password = UserController.getInput("Password: ");
                    currentUser = userServices.getUserByEmailAndPassword(email, password);
                    if (currentUser == null) {
                        System.out.println("Invalid email or password. Please try again.");
                    }
                }

                System.out.println("Hello, " + currentUser.getName() + "!");
                System.out.println("Please choose an option:");
                System.out.println("1. Buy products");
                System.out.println("2. View purchase history");
                if (currentUser.getRole().equals("sales_representative")) {
                    System.out.println("3. Add new product");
                    System.out.println("4. View sales report");
                }
                System.out.println("5. Log out");

                int choiceMenu = Integer.parseInt(UserController.getInput("Enter your choice: "));
                switch (choiceMenu) {
                    case 1:
                        ProductController.buyProducts(productServices);
                        break;
                    case 2:
                        ProductController.viewPurchaseHistory();
                        break;
                    case 3:
                        if (currentUser.getRole().equals("sales_representative")) {
                            ProductController.addNewProduct(productServices);
                        } else {
                            System.out.println("Invalid choice.");
                        }
                        break;
                    case 4:
                        if (currentUser.getRole().equals("sales_representative")) {
                            ProductController.viewSalesReport();
                        } else {
                            System.out.println("Invalid choice.");
                        }
                        break;
                    case 0:
                        currentUser = null;
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
        }
    }
}
