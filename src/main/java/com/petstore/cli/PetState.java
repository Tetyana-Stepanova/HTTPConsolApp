package com.petstore.cli;

import com.petstore.models.Pet;
import com.petstore.services.PetService;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class PetState extends CliState{
    private Scanner scanner;
    private CliState state;
    private PetService petService;

    public PetState(CliFSM fsm){
        super(fsm);
        scanner = fsm.getScanner();
        state = fsm.getState();
        petService = new PetService();
    }

    List<String> commands = List.of(
            "1 : Uploads an image",
            "2 : Add a new pet to the store",
            "3 : Update an existing pet",
            "4 : Find pets by status",
            "5 : Find pet by Id",
            "6 : Update a pet in the store",
            "7 : Delete a pet",
            "8 : Back",
            "9 : Exit"
    );

    @Override
    public void init() {
        petInputLoop();
    }

    public void petInputLoop(){
        int inputCommand;
        boolean status = true;
        while (status){
            System.out.println(" ");
            commands.forEach(System.out::println);

            System.out.println("Choose a command number");
            inputCommand = Integer.parseInt(scanner.nextLine());

            if(inputCommand >= 1 && inputCommand <=9){
                switch (inputCommand){
                    case 9:{
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
                case 8:{
                    idleState();
                    break;
                }
                case 1:{
                    uploadsImage();
                    break;
                }
                case 2:{
                    createNewPet();
                    break;
                }
                case 3:{
                    updatePet();
                    break;
                }
                case 4:{
                    getByStatus();
                    break;
                }
                case 5:{
                    getById();
                    break;
                }
                case 6:{
                    updatePetInTheStore();
                    break;
                }
                case 7:{
                    deletePet();
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

    private void uploadsImage(){
        System.out.println("Enter pet's Id : ");
        long id = 0;
        try {
            id = Long.parseLong(scanner.nextLine());
        }catch (NumberFormatException ex){
            System.out.println("Id can't be parsed to long");
        }
        System.out.println("Enter path to file : ");
        String path = scanner.nextLine().replaceAll("\\\\", "/");
        File file = new File(path);
        if(!file.exists()){
            System.out.println("File can not find");
        }
        try {
            boolean imageIsUploaded = petService.uploadImage(id, file);
            if(imageIsUploaded){
                System.out.println("Operation successes.");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
       petInputLoop();
    }

    private void createNewPet(){
        Pet newPet = petService.setPet();
        try {
            Pet savedPet = petService.addNewPet(newPet);
            System.out.println("Pet was saved: \n" + savedPet.toString());
        } catch (IOException e) {
            System.out.println("There is error in operation: " + e);
        }
        petInputLoop();
    }

    private void updatePet(){
        Pet updatePet = petService.setPet();

        try {
            Pet savedPet = petService.updatePet(updatePet);
            System.out.println("Pet was saved: \n" + savedPet.toString());
        } catch (IOException e) {
            System.out.println("There is error in operation: " + e);
        }
        petInputLoop();

    }

    private void getByStatus(){
        System.out.println("Choose and enter status : available, pending, sold ");
        String petStatus = scanner.nextLine();
        try {
            List<Pet> petsByStatus = petService.getAllPetsByStatus(petService.convertStringToStatus(petStatus));
            petsByStatus.forEach(System.out::println);
        }catch (IOException e) {
            System.out.println("There is error in operation: " + e);
        }
        petInputLoop();
    }

    private void getById(){
        System.out.println("Enter pet's Id : ");
        long petsId = 0L;
        try {
            petsId = Long.parseLong(scanner.nextLine());
        }catch (NumberFormatException ex){
            System.out.println("Id can't be parsed to long");
        }
        try {
            Pet petsById = petService.getPetById(petsId);
            System.out.println(petsById);
        }catch (IOException e) {
            System.out.println("There is error in operation: " + e);
        }
        petInputLoop();

    }

    private void updatePetInTheStore(){
        System.out.println("Enter pet's Id : ");
        long id = 0;
        try {
            id = Long.parseLong(scanner.nextLine());
        }catch (NumberFormatException ex){
            System.out.println("Id can't be parsed to long");
        }
        System.out.println("Enter pet's name :");
        String petsName = scanner.nextLine();
        System.out.println("Choose and enter status : available, pending, sold ");
        String status = scanner.nextLine();

        try{
            boolean petInStoreIsUpdate = petService.updateInStore(id, petsName, status);
            if(petInStoreIsUpdate){
                System.out.println("Operation successes.");
            }
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
        petInputLoop();
    }

    private void deletePet(){
        System.out.println("Enter pet's Id for delete : ");
        long petsId = 0L;
        try {
            petsId = Long.parseLong(scanner.nextLine());
        }catch (NumberFormatException ex){
            System.out.println("Id can't be parsed to long");
        }
        try {
            boolean deleteById = petService.deletePetById(petsId);
            if(deleteById){
                System.out.println("Operation successes.");
            }
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
        petInputLoop();

    }
}
