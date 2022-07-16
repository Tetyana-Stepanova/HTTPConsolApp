package com.petstore.cli;

import com.petstore.models.User;
import com.petstore.models.UserResponse;
import com.petstore.services.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserState extends CliState{
    private Scanner scanner;
    private CliState state;
    private UserService userService;

    public UserState(CliFSM fsm){
        super(fsm);
        scanner = fsm.getScanner();
        state = fsm.getState();
        userService = new UserService();
    }

    List<String> commands = List.of(
            "1 : Create new user",
            "2 : Create array of users",
            "3 : Create list of users",
            "4 : Get user by username",
            "5 : Logs user into the system",
            "6 : Update user by username",
            "7 : Delete a user",
            "8 : Logs out user",
            "9 : Back",
            "10 : Exit"
    );

    @Override
    public void init() {
        userInputLoop();
    }

    public void userInputLoop(){
        int inputCommand;
        boolean status = true;
        while (status){
            commands.forEach(System.out::println);

            System.out.println("Choose a command number");
            inputCommand = Integer.parseInt(scanner.nextLine());

            if(inputCommand >= 1 && inputCommand <=10){
                switch (inputCommand){
                    case 10:{
                        System.exit(0);
                        status = false;
                        break;
                    }
                    default:{
                        status = false;
                        break;
                    }
                }
            } else {
                unknownCommand(inputCommand);
            }

            switch (inputCommand){
                case 9:{
                    idleState();
                    break;
                }
                case 1:{
                    createNewUser();
                    break;
                }
                case 2:{
                    createArrayUsers();
                    break;
                }
                case 3:{
                    createListUsers();
                    break;
                }
                case 4:{
                    getByUsername();
                    break;
                }
                case 5:{
                    logs();
                    break;
                }
                case 6:{
                    updateByUsername();
                    break;
                }
                case 7:{
                    delete();
                    break;
                }
                case 8:{
                    logsOut();
                    break;
                }
            }
        }
    }

    public void unknownCommand(int cmdNumber){
        state.unknownCommand(cmdNumber);
    }

    @Override
    public void idleState(){
        new CliFSM();
    }

    public void createNewUser(){
        User newUser = userService.setUser();

        try {
            UserResponse response = userService.createUser(newUser);
            System.out.println(response);
            System.out.println("User created with Id = :" + response.getMessage());
            System.out.println("The operation is successful!");
        }catch (IOException e) {
            System.out.println("There is error in operation: " + e);
        }
        userInputLoop();
    }

    public void createArrayUsers(){
        System.out.println("How many users do you want to create? :");
        int quantity = 0;
        try {
            quantity = Integer.parseInt(scanner.nextLine());
        }catch (NumberFormatException ex){
            System.out.println("Status can't be parsed to integer");
        }
        User[] users = new User[quantity];
        for (int i = 0; i < quantity; i++){
            users[i] = userService.setUser();
        }

        try {
            UserResponse response = userService.createUsersWithArray(users);
            System.out.println(response);
            System.out.println("The operation is successful!");
        }catch (IOException e) {
            System.out.println("There is error in operation: " + e);
        }
        userInputLoop();
    }

    public void createListUsers(){
        System.out.println("How many users do you want to create? :");
        int quantity = 0;
        try {
            quantity = Integer.parseInt(scanner.nextLine());
        }catch (NumberFormatException ex){
            System.out.println("Status can't be parsed to integer");
        }
        List<User> users = new ArrayList<>();
        for (int i = 0; i < quantity; i++){
            User newUser = userService.setUser();
            users.add(newUser);
        }

        try {
            UserResponse response = userService.createUsersWithList(users);
            System.out.println(response);
            System.out.println("The operation is successful!");
        }catch (IOException e) {
            System.out.println("There is error in operation: " + e);
        }
        userInputLoop();
    }

    public void getByUsername(){
        System.out.println("Enter username : ");
        String username = scanner.nextLine();
        try {
            User userByUsername = userService.getUserByUsername(username);
            System.out.println(userByUsername);
            System.out.println("The operation is successful!");
        }catch (IOException e) {
            System.out.println("There is error in operation: " + e);
        }
        userInputLoop();
    }

    public void logs(){
        System.out.println("Enter username : ");
        String username = scanner.nextLine();
        System.out.println("Enter password : ");
        String password = scanner.nextLine();
        try {
            UserResponse response = userService.getLog(username, password);
            System.out.println(response);
            System.out.println("The operation is successful!");
        }catch (IOException e) {
            System.out.println("There is error in operation: " + e);
        }
        userInputLoop();
    }

    public void updateByUsername(){
        System.out.println("Enter username : ");
        String username = scanner.nextLine();
        User updateUser = userService.setUser();
        try {
            UserResponse response = userService.updateUser(username, updateUser);
            System.out.println(response);
            System.out.println("The operation is successful!");
        }catch (IOException e) {
            System.out.println("There is error in operation: " + e);
        }
        userInputLoop();
    }

    public void delete(){
        System.out.println("Enter username : ");
        String username = scanner.nextLine();
        try {
            boolean deleteByUsername = userService.deleteUser(username);
            if(deleteByUsername){
                System.out.println("The operation is successful!");
            }
        }catch (IOException e) {
            System.out.println("There is error in operation: " + e);
        }
        userInputLoop();
    }

    public void logsOut(){
        try{
            UserResponse logOut = userService.getLogOut();
            System.out.println(logOut);
            System.out.println("The operation is successful!");
        }catch (IOException e) {
            System.out.println("There is error in operation: " + e);
        }
        userInputLoop();
    }
}
