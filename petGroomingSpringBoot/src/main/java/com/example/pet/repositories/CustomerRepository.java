package com.example.pet.repositories;
// import java.util.HashMap;
import java.util.List;
import java.util.Date;

import com.example.pet.dto.MessageDetails;
// import com.example.pet.exceptions.CoundNotFoundException;
import com.example.pet.exceptions.UpdateErrorException;
import com.example.pet.exceptions.InsertErrorException;
import com.example.pet.exceptions.DeleteErrorException;
import com.example.pet.models.Customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepository {
    private final JdbcTemplate jdbc;

    public CustomerRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Customer> getCustomers() {
        String sql = "SELECT * FROM customer";

        RowMapper<Customer> rowMapper = (r, i) -> {
            Customer customer = new Customer();
            customer.setCusId(r.getInt("cus_id"));
            customer.setCusName(r.getString("cus_name"));
            customer.setGender(r.getBoolean("gender"));
            customer.setBirthday(r.getDate("birthday"));
            customer.setPhoneNum(r.getString("phone_num"));
            return customer;
        };

        return jdbc.query(sql, rowMapper);
    }
    
    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO customer VALUES(cus_id, ?, ?, ?, ?)";
        int insertNum = jdbc.update(sql, customer.getCusName(), customer.isGender(), customer.getBirthday(),
        customer.getPhoneNum());

        if (insertNum == 0){
            MessageDetails msg = new MessageDetails("Customer could not be inserted. customerName = " + customer.getCusName(), false);
            throw new InsertErrorException(msg);
        }
    }

    public void updateCustomer(Customer customer) {
        String cusName = customer.getCusName();
        Boolean gender = customer.isGender();
        String phoneNum = customer.getPhoneNum();
        Date birthDay = customer.getBirthday();
        int cusId = customer.getCusId();

        String sql = "UPDATE customer SET cus_name = ?, gender = ?, phone_num = ?,birthday = ?  WHERE cus_id = ?";
        int updateNum = jdbc.update(sql, cusName, gender, phoneNum, birthDay, cusId);

        if (updateNum == 0){
            MessageDetails msg = new MessageDetails("Customer could not be updated. customerName = " + customer.getCusName(), false);
            throw new UpdateErrorException(msg);
        }
    }
    

    public void deleteCustomer(int cusId) {
        String sql = "DELETE FROM customer WHERE cus_id = ?";
        int deleteNum = jdbc.update(sql, cusId);

        if (deleteNum == 0){
            MessageDetails msg = new MessageDetails("Customer could not be deleted. customerId = " + cusId, false);
            throw new DeleteErrorException(msg);
        }
    }

    



}
