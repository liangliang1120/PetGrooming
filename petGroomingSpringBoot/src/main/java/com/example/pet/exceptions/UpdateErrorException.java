package com.example.pet.exceptions;
import com.example.pet.dto.MessageDetails;

public class UpdateErrorException extends RuntimeException {
    public UpdateErrorException(MessageDetails msg) {
        super("Update Error:" +msg.getMessage());
    }
}
