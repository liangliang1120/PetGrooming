package com.example.pet.models;
// import java.util.Date;

public class Schedule {
    private Integer sId;
    private Integer empId;
    private String workTime;
    private Boolean avaliable;

    public Schedule(Integer sId, Integer empId, String workTime, Boolean avaliable) {
        this.sId = sId;
        this.empId = empId;
        this.workTime = workTime;
        this.avaliable = avaliable;
    }

    public Schedule() {
    }

    public Integer getsId() {
        return sId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }
    public Integer getEmpId() {
        return empId;
    }
    public void setEmpId(Integer empId) {
        this.empId = empId;
    }
    public String getWorkTime() {
        return workTime;
    }
    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public Boolean isAvaliable() {
        return avaliable;
    }

    public void setAvaliable(Boolean avaliable) {
        this.avaliable = avaliable;
    }


    
}
