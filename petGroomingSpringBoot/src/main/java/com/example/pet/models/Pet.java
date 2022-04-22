package com.example.pet.models;
import java.util.Date;

public class Pet {
    private Integer petId;
    private String petName;
    private boolean gender;
    private Date birthday;
    private Integer cusId;

    public Pet(Integer petId, String petName, boolean gender, Date birthday, Integer cusId) {
        this.petId = petId;
        this.petName = petName;
        this.gender = gender;
        this.birthday = birthday;
        this.cusId = cusId;
    }

    public Pet() {
        
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

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getCusId() {
        return cusId;
    }

    public void setCusId(Integer cusId) {
        this.cusId = cusId;
    }

    
    
}
