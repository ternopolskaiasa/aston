package org.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.Scanner;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        try {
            new Main().run();
        } catch (Exception e) {
            System.err.println("Error with data working!: " + e.getMessage());
        }
    }

    private final UserDAOImpl userDAO;
    private final Scanner scanner;

    public Main() {
        this.userDAO = new UserDAOImpl();
        this.scanner = new Scanner(System.in);
    }

    public void run() throws Exception {
        boolean exit = false;
        while (!exit) {
            printCommands();
            int command = readCommand();
            switch (command) {
                case 1:
                    handleGetUserById();
                    break;
                case 2:
                    handleGetAllUsers();
                    break;
                case 3:
                    handleSaveUser();
                    break;
                case 4:
                    handleUpdateUser();
                    break;
                case 5:
                    handleDeleteUser();
                    break;
                case 6:
                    exit = true;
                    break;
                default:
                    System.out.println("Unknown command!");
            }
        }
    }

    private void printCommands() {
        System.out.println("\nCommands:");
        System.out.println("1. get user by id");
        System.out.println("2. get all users");
        System.out.println("3. save user");
        System.out.println("4. update user");
        System.out.println("5. delete user");
        System.out.println("6. exit");
    }

    int readCommand() {
        System.out.print("Enter necessary number of a command: ");
        return Integer.parseInt(scanner.nextLine());
    }

    void handleGetUserById() throws Exception {
        System.out.print("Enter the id: ");
        long id = Long.parseLong(scanner.nextLine());
        System.out.println(userDAO.getUserById(id));
    }

    void handleGetAllUsers() throws Exception {
        System.out.println(userDAO.getAllUsers().toString());
    }

    void handleSaveUser() throws Exception {
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
        userDAO.saveUser(userSave);
        System.out.println("User was created!");
    }

    void handleUpdateUser() throws Exception {
        System.out.print("Enter id of wanted user: ");
        User userUpdate = userDAO.getUserById(Long.parseLong(scanner.nextLine()));

        System.out.print("Enter parameter you need to change with the naming (e.g. age;33): ");
        String input = scanner.nextLine().toLowerCase();

        String[] params = input.split(";");
        if (params.length != 2) {
            System.out.println("Wrong number of arguments!");
            return;
        }

        switch (params[0]) {
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

        userDAO.updateUser(userUpdate);
        System.out.println("User was updated!");
    }

    void handleDeleteUser() throws Exception {
        System.out.print("Enter the id: ");
        long id = Long.parseLong(scanner.nextLine());
        userDAO.deleteUser(id);
        System.out.println("Picked user was deleted!");
    }
}
