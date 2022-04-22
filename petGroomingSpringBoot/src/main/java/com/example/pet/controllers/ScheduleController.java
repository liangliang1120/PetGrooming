package com.example.pet.controllers;
import java.util.List;

import com.example.pet.dto.MessageDetails;
import com.example.pet.models.Schedule;
import com.example.pet.dto.ScheduleInfo;
import com.example.pet.repositories.ScheduleRepository;

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
public class ScheduleController {
    private final ScheduleRepository scheduleRepository;

    public ScheduleController(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @GetMapping("/schedules")
    public List<ScheduleInfo> getSchedules() {
        return scheduleRepository.getSchedules();
    }
    
    @GetMapping("/schedules/{sId}")
    public List<ScheduleInfo> getSchedulesBySId(@PathVariable("sId") Integer sId) {
        return scheduleRepository.getSchedulesBySId(sId);
    }

    @PostMapping("/schedules")
    public ResponseEntity<MessageDetails> addSchedule(@RequestBody Schedule schedule) {
        List<ScheduleInfo> schedules = scheduleRepository.getSchedules();
        for(ScheduleInfo s: schedules) {
            if (s.getEmpId() == schedule.getEmpId() && s.getWorkTime().equals(schedule.getWorkTime()) ) {
                MessageDetails msg = new MessageDetails("Schedule has exist. Cannot insert the same one again. empId =" + schedule.getEmpId()+ ", time = " +schedule.getWorkTime(), false);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
            }
        }
        int hourValue = Integer.valueOf(schedule.getWorkTime().substring(11, 13)).intValue();
        if (hourValue < 9 || hourValue > 17 ) {
            MessageDetails msg = new MessageDetails("Can not insert before 9:00 or after 17:00. empId =" + schedule.getEmpId()+ ", time = " +schedule.getWorkTime(), false);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
        }
        scheduleRepository.addSchedule(schedule);
        MessageDetails msg = new MessageDetails("The schedule was added successfully.", true);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }

    @PutMapping("/schedules")
    public ResponseEntity<MessageDetails> updateSchedule(@RequestBody Schedule schedule) {
        List<ScheduleInfo> schedules = scheduleRepository.getSchedules();
        for(ScheduleInfo s: schedules) {
            if (s.getEmpId() == schedule.getEmpId() && s.getWorkTime().equals(schedule.getWorkTime()) ) {
                MessageDetails msg = new MessageDetails("Schedule has exist. Cannot insert the same one again. empId =" + schedule.getEmpId()+ ", time = " +schedule.getWorkTime(), false);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
            }
        }
        int hourValue = Integer.valueOf(schedule.getWorkTime().substring(11, 13)).intValue();
        if (hourValue < 9 || hourValue > 17 ) {
            MessageDetails msg = new MessageDetails("Can not insert before 9:00 or after 17:00. empId =" + schedule.getEmpId()+ ", time = " +schedule.getWorkTime(), false);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
        }
        scheduleRepository.updateSchedule(schedule);
        MessageDetails msg = new MessageDetails("The schedule was updated successfully.", true);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }

    @PutMapping("/schedules/true/{sId}")
    public ResponseEntity<MessageDetails> updateScheduleAvailableTrueBySId(@PathVariable("sId") Integer sId) {
        
        scheduleRepository.updateScheduleAvailableTrueBySId(sId);
        MessageDetails msg = new MessageDetails("The schedule was updated successfully.", true);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }
    
    @PutMapping("/schedules/false/{sId}")
    public ResponseEntity<MessageDetails> updateScheduleAvailableFalseBySId(@PathVariable("sId") Integer sId) {
        scheduleRepository.updateScheduleAvailableFalseBySId(sId);
        MessageDetails msg = new MessageDetails("The schedule was updated successfully.", true);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }

    @DeleteMapping("/schedules/{sId}")
    public ResponseEntity<MessageDetails> deleteSchedule(@PathVariable  Integer sId) {
        ScheduleInfo schedule = scheduleRepository.getSchedulesBySId(sId).get(0);
        if (schedule.isAvaliable() == false) {
            MessageDetails msg = new MessageDetails("The scedule was booked. Cannot delete.", false);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
        }
        scheduleRepository.deleteSchedule(sId);
        MessageDetails msg = new MessageDetails("The schedule was delete successfully.", true);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }

    @GetMapping("/schedules/employee")
    public List<ScheduleInfo> getSchedulesOfEmployee() {
        return scheduleRepository.getSchedulesOfEmployee();
    }

    @GetMapping("/schedules/employee/{empId}")
    public List<ScheduleInfo> getSchedulesOfEmployeeOne(@PathVariable  Integer empId) {
        return scheduleRepository.getSchedulesOfEmployeeOne(empId);
    }


}
