package com.company;

public class FriendNotFoundException extends Exception {

    public FriendNotFoundException(){
        super();
    }
    public FriendNotFoundException(String message){
        super(message);
    }
}
