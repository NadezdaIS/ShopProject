package controllers;

import entities.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import services.HibernateUtil;

import javax.transaction.*;
import java.util.Scanner;

public class UserController {

    private static Scanner scanner = new Scanner(System.in);

    public static void collectUserDataAndCreateUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter balance: ");
        double balance = scanner.nextDouble();
        System.out.print("Enter role: ");
        String role = scanner.nextLine();
        createUser(name, email, password, balance, role);
    }
    public static void createUser(String name, String email, String password, double balance, String role) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            session.getTransaction().begin();
            User user = new User(name, email, password, balance, role);
            session.save(user);
            session.getTransaction().commit();
            System.out.println("User created successfully");
        } catch (Exception exception) {
            session.getTransaction().rollback();
            exception.printStackTrace();
        }
    }

    public static String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}

