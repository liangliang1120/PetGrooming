package com.example.pet.dto;
// import java.util.Date;

public class ScheduleInfo {
    private Integer sId;
    private Integer empId;
    private String empName;
    private String workTime;
    private Boolean avaliable;

    public ScheduleInfo(Integer sId, Integer empId, String empName, String workTime, Boolean avaliable) {
        this.sId = sId;
        this.empId = empId;
        this.empName = empName;
        this.workTime = workTime;
        this.avaliable = avaliable;
    }
    public ScheduleInfo() {
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
    public String getEmpName() {
        return empName;
    }
    public void setEmpName(String empName) {
        this.empName = empName;
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
