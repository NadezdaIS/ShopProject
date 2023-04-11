package controllers;

import java.util.Scanner;

public class UserController {
    private static Scanner scanner = new Scanner(System.in);
    public static String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
