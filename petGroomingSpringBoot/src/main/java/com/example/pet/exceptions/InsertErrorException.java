package com.example.pet.exceptions;
import com.example.pet.dto.MessageDetails;

public class InsertErrorException extends RuntimeException {
    public InsertErrorException(MessageDetails msg) {
        super("Insert Error:" +msg.getMessage());
    }
}
