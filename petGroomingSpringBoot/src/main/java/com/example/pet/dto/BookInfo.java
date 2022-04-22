package com.example.pet.dto;

public class BookInfo {
    private Integer bookId;
    private Integer cusId;
    private String cusName;
    private Integer serviceKind;
    private Integer sId;
    private String bookTime;
    private Integer empId;
    private String empName;
    private Integer petId;
    private String petName;

    public BookInfo(Integer bookId, Integer cusId, String cusName, Integer serviceKind, Integer sId, String bookTime,
            Integer empId, String empName, Integer petId, String petName) {
        this.bookId = bookId;
        this.cusId = cusId;
        this.cusName = cusName;
        this.serviceKind = serviceKind;
        this.sId = sId;
        this.bookTime = bookTime;
        this.empId = empId;
        this.empName = empName;
        this.petId = petId;
        this.petName = petName;
    }

    public BookInfo() {
       
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

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
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

    public String getBookTime() {
        return bookTime;
    }

    public void setBookTime(String bookTime) {
        this.bookTime = bookTime;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    

    
}
