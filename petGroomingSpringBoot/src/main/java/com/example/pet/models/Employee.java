package com.example.pet.models;

public class Employee {
    private Integer empId;
    private String empName;
    private boolean isSupervisor;

    public Employee(Integer empId, String empName, boolean isSupervisor) {
        this.empId = empId;
        this.empName = empName;
        this.isSupervisor = isSupervisor;
    }

    public Employee() {
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

    public boolean isSupervisor() {
        return isSupervisor;
    }

    public void setSupervisor(boolean isSupervisor) {
        this.isSupervisor = isSupervisor;
    }

    
}
