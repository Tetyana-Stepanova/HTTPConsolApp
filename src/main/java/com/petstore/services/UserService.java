package com.petstore.services;

import com.google.gson.Gson;
import com.petstore.models.User;
import com.petstore.models.UserResponse;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class UserService extends PetsStoreApiService{
    PetsStoreApiService petsStoreApiService = new PetsStoreApiService();
    private final String baseUrl = "https://petstore.swagger.io/v2/user";
    String urlCreateWithArray = "%s/createWithArray";
    String urlCreateWithList = "%s/createWithList";
    String urlGetUserByUsername = "%s/%s";//for update and for delete
    String urlLogout = "%s/logout";
    String urlLogsInto = "%s/login?username=%s&password=%s";

    public UserResponse createUser(User newUser) throws IOException {
        String url = baseUrl;
        String json = new Gson().toJson(newUser);
        return petsStoreApiService.postForUser(json, url);
    }

    public User getUserByUsername(String username) throws IOException {
        String url = String.format(urlGetUserByUsername, baseUrl, username);
        return (User) petsStoreApiService.getModel(url, new User());
    }

    public UserResponse getLog(String username, String password) throws IOException {
        String url = String.format(urlLogsInto, baseUrl, username, password);
        return (UserResponse) petsStoreApiService.getModel(url, new UserResponse());
    }

    public UserResponse getLogOut() throws IOException {
        String url = String.format(urlLogout, baseUrl);
        return (UserResponse) petsStoreApiService.getModel(url,new UserResponse());
    }

    public UserResponse updateUser(String username, User updateUser) throws IOException {
        String url = String.format(urlGetUserByUsername, baseUrl, username);
        String json = new Gson().toJson(updateUser);
        return petsStoreApiService.putUser(json, url);
    }

    public boolean deleteUser(String username) throws IOException {
        String url = String.format(urlGetUserByUsername, baseUrl, username);
        return  petsStoreApiService.deleteEntity(url);
    }

    public UserResponse createUsersWithArray(User[] users) throws IOException {
        String url = String.format(urlCreateWithArray, baseUrl);
        String json = new Gson().toJson(users);
        return petsStoreApiService.postForUser(json, url);
    }

    public UserResponse createUsersWithList(List<User> users) throws IOException {
        String url = String.format(urlCreateWithList, baseUrl);
        String json = new Gson().toJson(users);
        return petsStoreApiService.postForUser(json, url);
    }

    public User setUser(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter user's Id (or it will be generated automatically): ");
        long userId;
        String userIdString = scanner.nextLine();
            if(userIdString.equals(" ")){
                userId = 0L;
            }else {
                userId = Long.parseLong(userIdString);
            }
        System.out.println("Enter username : ");
        String username = scanner.nextLine();
        System.out.println("Enter first name :");
        String firstName = scanner.nextLine();
        System.out.println("Enter last name : ");
        String lastName = scanner.nextLine();
        System.out.println("Enter email : ");
        String email = scanner.nextLine();
        System.out.println("Enter password : ");
        String password = scanner.nextLine();
        System.out.println("Enter phone number : ");
        String phone = scanner.nextLine();
        System.out.println("Enter user's status (integer number) :");
        int status = 0;
        try{
            status = Integer.parseInt(scanner.nextLine());
        }catch (NumberFormatException ex){
            System.out.println("Status can't be parsed to integer");
        }

        User newUser = new User();
        newUser.setId(userId);
        newUser.setUsername(username);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setPhone(phone);
        newUser.setUserStatus(status);

        return newUser;
    }

}
