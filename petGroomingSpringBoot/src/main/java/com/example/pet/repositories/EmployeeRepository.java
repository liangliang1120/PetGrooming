package com.example.pet.repositories;
import java.util.List;
// import java.util.Date;

import com.example.pet.dto.MessageDetails;
// import com.example.pet.exceptions.CoundNotFoundException;
import com.example.pet.exceptions.UpdateErrorException;
import com.example.pet.exceptions.InsertErrorException;
import com.example.pet.exceptions.DeleteErrorException;
import com.example.pet.models.Employee;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepository {
    private final JdbcTemplate jdbc;

    public EmployeeRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Employee> getEmployees() {
        String sql = "SELECT * FROM employee";

        RowMapper<Employee> rowMapper = (r, i) -> {
            Employee employee = new Employee();
            employee.setEmpId(r.getInt("emp_id"));
            employee.setEmpName(r.getString("emp_name"));
            employee.setSupervisor(r.getBoolean("is_supervisor"));
            return employee;
        };

        return jdbc.query(sql, rowMapper);
    }

    public void addEmployee(Employee employee) {
        String sql = "INSERT INTO employee VALUES(emp_id, ?, ?)";
        int insertNum = jdbc.update(sql, employee.getEmpName(), employee.isSupervisor());

        if (insertNum == 0){
            MessageDetails msg = new MessageDetails("Employee could not be inserted. petName = " + employee.getEmpName(), false);
            throw new InsertErrorException(msg);
        }
    }
    
    public void updateEmployee(Employee employee) {
        int empId = employee.getEmpId();
        String empName = employee.getEmpName();
        Boolean isSupervisor = employee.isSupervisor();

        String sql = "UPDATE employee SET emp_name = ?, is_supervisor = ? WHERE emp_id = ?";
        int updateNum = jdbc.update(sql, empName, isSupervisor, empId);

        if (updateNum == 0){
            MessageDetails msg = new MessageDetails("Employee could not be updated. petName = " + empName, false);
            throw new UpdateErrorException(msg);
        }
    }

    public void deleteEmployee(int empId) {
        String sql = "DELETE FROM employee WHERE emp_id = ?";
        int deleteNum = jdbc.update(sql, empId);

        if (deleteNum == 0){
            MessageDetails msg = new MessageDetails("Employee could not be deleted. empId = " + empId, false);
            throw new DeleteErrorException(msg);
        }
    }

    
}
