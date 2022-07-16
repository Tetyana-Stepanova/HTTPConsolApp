package com.petstore.cli;

import lombok.Getter;

import java.util.List;
import java.util.Scanner;

public class CliFSM {
    @Getter
    private CliState state;
    @Getter
    private Scanner scanner;

    List<String> commands = List.of(
            "1 : Operations with Pet",
            "2 : Operations with Store",
            "3 : Operations with User",
            "4 : Exit"
    );

    public CliFSM(){
        state = new IdleState(this);
        scanner =  new Scanner(System.in);
        inputLoop();
    }

    public void inputLoop(){
        int inputCommand;

        while (true) {
            commands.forEach(System.out::println);

            System.out.println("Choose a command number");
            inputCommand = Integer.parseInt(scanner.nextLine());

            if(inputCommand >= 1 && inputCommand <=4){
                if (inputCommand == 4) {
                    System.exit(0);
                } else {
                    break;
                }
            } else {
                unknownCommand(inputCommand);
            }
        }


        switch (inputCommand){
            case 1: {
                pet();
                break;
            }
            case 2: {
                store();
                break;
            }
            case 3: {
                user();
                break;
            }
        }
    }

    public void pet() {
        state.pet();
    }

    public void store(){
        state.store();
    }

    public void user(){
        state.user();
    }

    public void unknownCommand(int cmd) {

        state.unknownCommand(cmd);
    }

    public void setState(CliState state) {
        this.state = state;
        state.init();
    }
}
