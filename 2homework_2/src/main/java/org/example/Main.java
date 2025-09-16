package org.example;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws DAOException {
        Scanner scanner = new Scanner(System.in);
        UserDAOImpl usr = new UserDAOImpl();
        long id;
        boolean exit = false;

        while (!exit) {
            System.out.println("\nCommands:");
            System.out.println("1. get user by id");
            System.out.println("2. get all users");
            System.out.println("3. save user");
            System.out.println("4. update user");
            System.out.println("5. delete user");
            System.out.println("6. exit");

            System.out.print("Enter necessary number of a command: ");
            int command = Integer.parseInt(scanner.nextLine());

            switch (command) {
                case 1:
                    System.out.print("Enter the id: ");
                    id = Long.parseLong(scanner.nextLine());
                    System.out.println(usr.getUserById(id));
                    break;
                case 2:
                    System.out.println(usr.getAllUsers().toString());
                    break;
                case 3:
                    System.out.print("Enter name: ");
                    String username = scanner.nextLine();

                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();

                    System.out.print("Enter age: ");
                    Integer age = Integer.valueOf(scanner.nextLine());

                    User userSave = new User();
                    userSave.setName(username);
                    userSave.setEmail(email);
                    userSave.setAge(age);
                    userSave.setCreatedAt(LocalDateTime.now());
                    usr.saveUser(userSave);
                    System.out.println("User was created!");
                    break;
                case 4:
                    System.out.print("Enter id of wanted user: ");
                    User userUpdate = usr.getUserById(Long.parseLong(scanner.nextLine()));

                    System.out.print("Enter parameter you need to change with the naming (e.g. age 33): ");
                    String input = scanner.nextLine().toLowerCase();

                    String[] params = input.split(" ");
                    if (params.length != 2) {
                        System.out.println("Wrong number of arguments!");
                        break;
                    }

                    switch(params[0]){
                        case "name":
                            userUpdate.setName(params[1]);
                            break;
                        case "email":
                            userUpdate.setEmail(params[1]);
                            break;
                        case "age":
                            userUpdate.setAge(Integer.parseInt(params[1]));
                            break;
                        default:
                            System.out.println("Unknown parameter!");
                    }

                    usr.updateUser(userUpdate);
                    System.out.println("User was updated!");
                    break;
                case 5:
                    System.out.print("Enter the id: ");
                    id = Long.parseLong(scanner.nextLine());
                    usr.deleteUser(id);
                    System.out.println("Picked user was deleted!");
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
