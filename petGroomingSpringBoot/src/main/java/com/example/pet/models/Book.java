package com.example.pet.models;
// import java.text.SimpleDateFormat;
// import java.util.Date;

public class Book {
    private Integer bookId;
    private Integer cusId;
    private Integer serviceKind;
    private Integer sId;
    private Integer petId;

   

    public Book(Integer bookId, Integer cusId, Integer serviceKind, Integer sId, Integer petId) {
        this.bookId = bookId;
        this.cusId = cusId;
        this.serviceKind = serviceKind;
        this.sId = sId;
        this.petId = petId;
    }

    public Book() {
       
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getCusId() {
        return cusId;
    }

    public void setCusId(Integer cusId) {
        this.cusId = cusId;
    }

    public Integer getServiceKind() {
        return serviceKind;
    }

    public void setServiceKind(Integer serviceKind) {
        this.serviceKind = serviceKind;
    }

    public Integer getsId() {
        return sId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    
    
}
