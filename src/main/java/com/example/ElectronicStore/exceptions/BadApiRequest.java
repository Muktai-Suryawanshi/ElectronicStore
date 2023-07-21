package com.example.ElectronicStore.exceptions;

public class BadApiRequest extends RuntimeException{

    public BadApiRequest(String message){
        super(message);
    }

    private BadApiRequest(){
        super("Bad Request");
    }

}
