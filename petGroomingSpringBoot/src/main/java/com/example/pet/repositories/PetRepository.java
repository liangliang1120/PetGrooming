package com.example.pet.repositories;
import java.util.List;
import java.util.Date;

import com.example.pet.dto.MessageDetails;
import com.example.pet.exceptions.CoundNotFoundException;
import com.example.pet.exceptions.UpdateErrorException;
import com.example.pet.exceptions.InsertErrorException;
import com.example.pet.exceptions.DeleteErrorException;
import com.example.pet.models.Pet;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class PetRepository {
    private final JdbcTemplate jdbc;

    public PetRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Pet> getPets() {
        String sql = "SELECT * FROM pet";

        RowMapper<Pet> rowMapper = (r, i) -> {
            Pet pet = new Pet();
            pet.setPetId(r.getInt("pet_id"));
            pet.setPetName(r.getString("pet_name"));
            pet.setGender(r.getBoolean("gender"));
            pet.setBirthday(r.getDate("birthday"));
            pet.setCusId(r.getInt("cus_id"));
            return pet;
        };

        return jdbc.query(sql, rowMapper);
    }

    public List<Pet> getPetByPetId(Integer petId) {
        String sql = "SELECT * FROM pet WHERE pet_id = ?";

        RowMapper<Pet> rowMapper = (r, i) -> {
            Pet pet = new Pet();
            pet.setPetId(r.getInt("pet_id"));
            pet.setPetName(r.getString("pet_name"));
            pet.setGender(r.getBoolean("gender"));
            pet.setBirthday(r.getDate("birthday"));
            pet.setCusId(r.getInt("cus_id"));
            return pet;
        };
        List<Pet> petsByPetId = jdbc.query(sql, rowMapper, petId);
        if (petsByPetId.size() == 0) {
            MessageDetails msg = new MessageDetails("Pet petId = " + petId, false);
            throw new CoundNotFoundException(msg);
        }
        return petsByPetId;
    }
    
    public List<Pet> getPetsByCusId(Integer cusId) {
        String sql = "SELECT * FROM pet WHERE cus_id = ?";

        RowMapper<Pet> rowMapper = (r, i) -> {
            Pet pet = new Pet();
            pet.setPetId(r.getInt("pet_id"));
            pet.setPetName(r.getString("pet_name"));
            pet.setGender(r.getBoolean("gender"));
            pet.setBirthday(r.getDate("birthday"));
            pet.setCusId(r.getInt("cus_id"));
            return pet;
        };
        List<Pet> petsByPetId = jdbc.query(sql, rowMapper, cusId);
        if (petsByPetId.size() == 0) {
            MessageDetails msg = new MessageDetails("Pet cusId = " + cusId, false);
            throw new CoundNotFoundException(msg);
        }
        return petsByPetId;
    }

    public void addPet(Pet pet) {
        String sql = "INSERT INTO pet VALUES(pet_id, ?, ?, ?, ?)";
        int insertNum = jdbc.update(sql, pet.getPetName(), pet.isGender(), pet.getBirthday(),
        pet.getCusId());

        if (insertNum == 0){
            MessageDetails msg = new MessageDetails("Pet could not be inserted. petName = " + pet.getPetName(), false);
            throw new InsertErrorException(msg);
        }
    }
    
    public void updatePet(Pet pet) {
        int petId = pet.getPetId();
        String petName = pet.getPetName();
        Boolean gender = pet.isGender();
        Date birthDay = pet.getBirthday();
        int cusId = pet.getCusId();

        String sql = "UPDATE pet SET pet_name = ?, gender = ?, birthday = ?, cus_id = ? WHERE pet_id = ?";
        int updateNum = jdbc.update(sql, petName, gender, birthDay, cusId, petId);

        if (updateNum == 0){
            MessageDetails msg = new MessageDetails("Pet could not be updated. petName = " + pet.getPetName(), false);
            throw new UpdateErrorException(msg);
        }
    }

    public void deletePet(int petId) {
        String sql = "DELETE FROM pet WHERE pet_id = ?";
        int deleteNum = jdbc.update(sql, petId);

        if (deleteNum == 0){
            MessageDetails msg = new MessageDetails("Pet could not be deleted. petId = " + petId, false);
            throw new DeleteErrorException(msg);
        }
    }
}
