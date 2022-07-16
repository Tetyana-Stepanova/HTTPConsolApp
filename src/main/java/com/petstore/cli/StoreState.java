package com.petstore.cli;

import com.petstore.models.Order;
import com.petstore.services.StoreOrderService;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class StoreState extends CliState {
    private Scanner scanner;
    private CliState state;
    private StoreOrderService storeOrderService;

    public StoreState(CliFSM fsm) {
        super(fsm);
        scanner = fsm.getScanner();
        state = fsm.getState();
        storeOrderService = new StoreOrderService();
    }

    List<String> commands = List.of(
            "1 : Place an order for a pet",
            "2 : Find purchase order by ID",
            "3 : Delete purchase order by ID",
            "4 : Return pet inventories by status",
            "5 : Back",
            "6 : Exit"
    );

    @Override
    public void init() {
        storeInputLoop();
    }

    public void storeInputLoop(){
        int inputCommand;
        boolean status = true;
        while (status){
            System.out.println(" ");
            commands.forEach(System.out::println);

            System.out.println("Choose a command number");
            inputCommand = Integer.parseInt(scanner.nextLine());

            if(inputCommand >= 1 && inputCommand <=6){
                switch (inputCommand){
                    case 6:{
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
                case 5:{
                    idleState();
                    break;
                }
                case 1:{
                    createNewOrder();
                    break;
                }
                case 2:{
                    getById();
                    break;
                }
                case 3:{
                    deleteById();
                    break;
                }
                case 4:{
                    getPetInventories();
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

    private void createNewOrder(){
        System.out.println("Enter pet's Id : ");
        long petId = 0L;
        try{
            petId = Long.parseLong(scanner.nextLine());
        }catch (NumberFormatException ex){
            System.out.println("Id can't be parsed to long");
        }
        System.out.println("Enter quantity : ");
        long quantity = 0L;
        try {
            quantity = Long.parseLong(scanner.nextLine());
        }catch (NumberFormatException ex){
            System.out.println("Id can't be parsed to long");
        }
        System.out.println("Enter shipDate : ");
        String shipDate = scanner.nextLine();
        System.out.println("Enter status : placed, approved or delivered ");
        String status = scanner.nextLine();
        System.out.println("Enter complete : true or false");
        boolean complete = Boolean.parseBoolean(scanner.nextLine());

        Order newOrder = new Order();
        newOrder.setPetId(petId);
        newOrder.setQuantity(quantity);
        newOrder.setShipDate(shipDate);
        newOrder.setStatus(convertStringToStatus(status));
        newOrder.setComplete(complete);
        try {
            Order savedOrder = storeOrderService.addNewOrder(newOrder);
            System.out.println("Order for pet with ID=" + petId + " was saved: \n" + savedOrder.toString());
        }catch (IOException e) {
            System.out.println("There is error in operation: " + e);
        }
        storeInputLoop();
    }

    public void getById(){
        System.out.println("Enter pet's Id : integer number from 1 to 10 (Other values will generated exceptions) ");
        long petsId = 0L;
        try {
            petsId = Long.parseLong(scanner.nextLine());
        }catch (NumberFormatException ex){
            System.out.println("Id doesn't belong to integers from 1 to 10");
        }
        try {
            Order orderById = storeOrderService.getOrderById(petsId);
            System.out.println(orderById);
        }catch (IOException e) {
            System.out.println("There is error in operation: " + e);
        }
        storeInputLoop();
    }

    public void deleteById(){
        System.out.println("Enter integer IDs with positive integer value : ");
        long petsId = 0L;
        try {
            petsId = Long.parseLong(scanner.nextLine());
        }catch (NumberFormatException ex){
            System.out.println("ID is negative or non-integer values");
        }
        try {
            boolean deleteById = storeOrderService.deleteOrderById(petsId);
            if(deleteById){
                System.out.println("Operation successes.");
            }
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
         storeInputLoop();
    }

    public void getPetInventories(){
        try {
            String petInventories = storeOrderService.getPetInventory();
            System.out.println(petInventories);
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
        storeInputLoop();
    }

    private Order.Status convertStringToStatus(String statusString){
        switch (statusString){
            case "placed": return Order.Status.PLACED;
            case "approved" : return Order.Status.APPROVED;
            case "delivered" : return  Order.Status.DELIVERED;
            default:
                System.out.println("Status incorrect");
                createNewOrder();
                break;
        }
        return null;
    }

}

