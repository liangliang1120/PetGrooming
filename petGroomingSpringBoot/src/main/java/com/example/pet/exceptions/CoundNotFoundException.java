package com.example.pet.exceptions;
import com.example.pet.dto.MessageDetails;


public class CoundNotFoundException  extends RuntimeException {
    public CoundNotFoundException(MessageDetails message) {
        super("Could not find " + message.getMessage());
    }
}
