package com.example.pet.controllers;
import java.util.List;

import com.example.pet.dto.MessageDetails;
import com.example.pet.models.Employee;
import com.example.pet.repositories.EmployeeRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {
    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        return employeeRepository.getEmployees();
    }
    
    @PostMapping("/employees")
    public ResponseEntity<MessageDetails> addEmployees(@RequestBody Employee employee) {
        employeeRepository.addEmployee(employee);
        MessageDetails msg = new MessageDetails("The employee was added successfully.", true);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }

    @PutMapping("/employees")
    public ResponseEntity<MessageDetails> updateEmployee(@RequestBody Employee employee) {
        employeeRepository.updateEmployee(employee);
        MessageDetails msg = new MessageDetails("The employee was updated successfully.", true);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }

    @DeleteMapping("/employees/{empId}")
    public ResponseEntity<MessageDetails> deleteEmployee(@PathVariable  int empId) {
        employeeRepository.deleteEmployee(empId);
        MessageDetails msg = new MessageDetails("The employee was delete successfully.", true);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }
}
