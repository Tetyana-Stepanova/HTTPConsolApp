package com.petstore.cli;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CliState {
    protected final CliFSM fsm;


    public void init(){}
    public void idleState(){}
    public void unknownCommand(int cmd){}
    public void pet(){}
    public void store(){}
    public void user(){}
}
