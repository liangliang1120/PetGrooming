package com.example.pet.models;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import java.util.Date;

@Component
@SessionScope
public class Customer {
    private Integer cusId;
    private String cusName;
    private boolean gender;
    private Date birthday;
    private String phoneNum;

    public Customer(Integer cusId, String cusName, boolean gender, Date birthday, String phoneNum) {
        this.cusId = cusId;
        this.cusName = cusName;
        this.gender = gender;
        this.birthday = birthday;
        this.phoneNum = phoneNum;
    }

    public Customer() {
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

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    

    
}
