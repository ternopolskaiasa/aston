package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean exit = false;
        while (!exit) {
            System.out.println("\nCommands:");
            System.out.println("1. get user by id");
            System.out.println("2. get all users");
            System.out.println("3. save user");
            System.out.println("4. update user");
            System.out.println("5. delete user");
            System.out.println("6. exit");

            System.out.print("Write necessary number of a command: ");
            int command = Integer.parseInt(scanner.nextLine());

            switch (command) {
                case 1:
                    //
                    break;
                case 2:
                    //
                    break;
                case 3:
                    //
                    break;
                case 4:
                    //
                    break;
                case 5:
                    //
                    break;
                case 6:
                    exit = true;
                    break;
                default:
                    System.out.println("Unknown command!");
            }
        }
    }
}
