package com.example.pet.exceptions;
import com.example.pet.dto.MessageDetails;

public class DeleteErrorException extends RuntimeException {
    public DeleteErrorException(MessageDetails msg) {
        super("Delete Error:" + msg.getMessage());
    }
}
