package com.example.pet.controllers.advice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.pet.dto.MessageDetails;
import com.example.pet.exceptions.CoundNotFoundException;
import com.example.pet.exceptions.DeleteErrorException;
import com.example.pet.exceptions.UpdateErrorException;
import com.example.pet.exceptions.InsertErrorException;

// import org.springframework.http.HttpStatus;

@RestControllerAdvice
public class ExceptionControlAdvice {
    @ExceptionHandler(CoundNotFoundException.class)
    public ResponseEntity<MessageDetails> handleNotFoundException(CoundNotFoundException ex) {
        MessageDetails msg = new MessageDetails(ex.getMessage(), false);
        return ResponseEntity.badRequest().body(msg);
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<MessageDetails> handleRuntimeException(RuntimeException ex) {
        MessageDetails msg = new MessageDetails(ex.getMessage(), false);
        return ResponseEntity.internalServerError().body(msg);
    }

    @ExceptionHandler(DeleteErrorException.class)
    public ResponseEntity<MessageDetails> handleDeleteErrorException(DeleteErrorException ex) {
        MessageDetails msg = new MessageDetails(ex.getMessage(), false);
        return ResponseEntity.internalServerError().body(msg);
    }

    @ExceptionHandler(UpdateErrorException.class)
    public ResponseEntity<MessageDetails> handleUpdateErrorException(UpdateErrorException ex) {
        MessageDetails msg = new MessageDetails(ex.getMessage(), false);
        return ResponseEntity.internalServerError().body(msg);
    }

    @ExceptionHandler(InsertErrorException.class)
    public ResponseEntity<MessageDetails> handleInsertErrorException(InsertErrorException ex) {
        MessageDetails msg = new MessageDetails(ex.getMessage(), false);
        return ResponseEntity.internalServerError().body(msg);
    }
}


