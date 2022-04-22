package com.example.pet.controllers;
import java.util.List;

import com.example.pet.dto.MessageDetails;
import com.example.pet.models.Pet;
import com.example.pet.repositories.PetRepository;

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
public class PetController {
    private final PetRepository petRepository;

    public PetController(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @GetMapping("/pets")
    public List<Pet> getPets() {
        return petRepository.getPets();
    }

    @GetMapping("/pets/customer/{cusId}")
    public List<Pet> getPetsByCusId(@PathVariable("cusId") Integer cusId) {
        return petRepository.getPetsByCusId(cusId);
    }

    @GetMapping("/pets/{petId}")
    public List<Pet> getPetsByPetId(@PathVariable("petId") Integer petId) {
        return petRepository.getPetByPetId(petId);
    }

    @PostMapping("/pets")
    public ResponseEntity<MessageDetails> addPet(@RequestBody Pet pet) {
        petRepository.addPet(pet);
        MessageDetails msg = new MessageDetails("The pet was added successfully.", true);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }

    @PutMapping("/pets")
    public ResponseEntity<MessageDetails> updatePet(@RequestBody Pet pet) {
        petRepository.updatePet(pet);
        MessageDetails msg = new MessageDetails("The pet was updated successfully.", true);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }

    @DeleteMapping("/pets/{petId}")
    public ResponseEntity<MessageDetails> deletePet(@PathVariable  int petId) {
        petRepository.deletePet(petId);
        MessageDetails msg = new MessageDetails("The pet was delete successfully.", true);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }

}
