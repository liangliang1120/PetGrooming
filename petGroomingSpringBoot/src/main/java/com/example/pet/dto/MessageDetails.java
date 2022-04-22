package com.example.pet.dto;

public class MessageDetails {
    private String message;
    private Boolean success;

    public MessageDetails(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    public MessageDetails() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    
}
