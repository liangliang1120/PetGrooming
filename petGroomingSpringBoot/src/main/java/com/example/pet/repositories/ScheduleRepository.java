package com.example.pet.repositories;
import java.util.List;
// import java.util.Date;
// import com.example.pet.utils.TimeDeltaAdd;

import com.example.pet.dto.MessageDetails;
import com.example.pet.exceptions.CoundNotFoundException;
import com.example.pet.exceptions.UpdateErrorException;
import com.example.pet.exceptions.InsertErrorException;
import com.example.pet.exceptions.DeleteErrorException;
import com.example.pet.models.Schedule;
import com.example.pet.dto.ScheduleInfo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ScheduleRepository {
    private final JdbcTemplate jdbc;

    public ScheduleRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<ScheduleInfo> getSchedules() {
        String sql = "SELECT s.s_id, s.emp_id, e.emp_name, s.work_time, available FROM schedule AS s LEFT JOIN employee AS e on s.emp_id = e.emp_id ORDER BY work_time";
        // TimeDeltaAdd td = new TimeDeltaAdd();
        RowMapper<ScheduleInfo> rowMapper = (r, i) -> {
            ScheduleInfo schedule = new ScheduleInfo();
            schedule.setsId(r.getInt("s_id"));
            schedule.setEmpId(r.getInt("emp_id"));
            schedule.setEmpName(r.getString("emp_name"));
            schedule.setWorkTime(r.getString("work_time"));
            schedule.setAvaliable(r.getBoolean("available"));
            return schedule;
        };

        return jdbc.query(sql, rowMapper);
    }
    
    public List<ScheduleInfo> getSchedulesOfEmployee() {
        String sql = "SELECT s.s_id, s.emp_id, e.emp_name, s.work_time, available FROM schedule AS s LEFT JOIN employee AS e on s.emp_id = e.emp_id ORDER BY work_time";
        // TimeDeltaAdd td = new TimeDeltaAdd();
        RowMapper<ScheduleInfo> rowMapper = (r, i) -> {
            ScheduleInfo scheduleinfo = new ScheduleInfo();
            scheduleinfo.setsId(r.getInt("s_id"));
            scheduleinfo.setEmpId(r.getInt("emp_id"));
            scheduleinfo.setEmpName(r.getString("emp_name"));
            scheduleinfo.setWorkTime(r.getString("work_time"));
            scheduleinfo.setAvaliable(r.getBoolean("available"));
            return scheduleinfo;
        };

        return jdbc.query(sql, rowMapper);
    }
    
    public List<ScheduleInfo> getSchedulesOfEmployeeOne(Integer empId) {
        String sql = "SELECT s.s_id, s.emp_id, e.emp_name, s.work_time, available FROM schedule AS s LEFT JOIN employee AS e on s.emp_id = e.emp_id where s.emp_id = ?  ORDER BY  work_time";
        // TimeDeltaAdd td = new TimeDeltaAdd();
        RowMapper<ScheduleInfo> rowMapper = (r, i) -> {
            ScheduleInfo scheduleinfo = new ScheduleInfo();
            scheduleinfo.setsId(r.getInt("s_id"));
            scheduleinfo.setEmpId(r.getInt("emp_id"));
            scheduleinfo.setEmpName(r.getString("emp_name"));
            scheduleinfo.setWorkTime(r.getString("work_time"));
            scheduleinfo.setAvaliable(r.getBoolean("available"));
            return scheduleinfo;
        };

        return jdbc.query(sql, rowMapper, empId);
    }

    public List<ScheduleInfo> getSchedulesBySId(Integer sId) {
        String sql = "SELECT s.s_id, s.emp_id, e.emp_name, s.work_time, available FROM schedule AS s LEFT JOIN employee AS e on s.emp_id = e.emp_id  WHERE s_id = ? ORDER BY work_time";
        // TimeDeltaAdd td = new TimeDeltaAdd();
        RowMapper<ScheduleInfo> rowMapper = (r, i) -> {
            ScheduleInfo schedule = new ScheduleInfo();
            schedule.setsId(r.getInt("s_id"));
            schedule.setEmpId(r.getInt("emp_id"));
            schedule.setEmpName(r.getString("emp_name"));
            schedule.setWorkTime(r.getString("work_time"));
            schedule.setAvaliable(r.getBoolean("available"));
            return schedule;
        };
        List<ScheduleInfo> scheduleBySId = jdbc.query(sql, rowMapper, sId);
        if (scheduleBySId.size() == 0) {
            MessageDetails msg = new MessageDetails("Schedule sId = " + sId, false);
            throw new CoundNotFoundException(msg);
        }

        return scheduleBySId;
    }
    
    public void addSchedule(Schedule schedule) {
        String sql = "INSERT INTO schedule VALUES(s_id, ?, ?, ?)";
        // TimeDeltaAdd td = new TimeDeltaAdd();
        int insertNum = jdbc.update(sql, schedule.getEmpId(), schedule.getWorkTime(), schedule.isAvaliable());

        if (insertNum == 0){
            MessageDetails msg = new MessageDetails("Schedule could not be inserted. empId = " + schedule.getEmpId()+ ", time = " +schedule.getWorkTime(), false);
            throw new InsertErrorException(msg);
        }
    }
    
    public void updateSchedule(Schedule schedule) {
        int sId = schedule.getsId();
        int empId = schedule.getEmpId();
        String workTime = schedule.getWorkTime();
        boolean available = schedule.isAvaliable();

        // TimeDeltaAdd td = new TimeDeltaAdd();
        // workTime = td.AddHour(workTime);

        String sql = "UPDATE schedule SET emp_id = ?, work_time = ?, available = ? WHERE s_id = ?";
        int updateNum = jdbc.update(sql, empId, workTime, available, sId);

        if (updateNum == 0){
            MessageDetails msg = new MessageDetails("Schedule could not be updated. s_id = " + sId, false);
            throw new UpdateErrorException(msg);
        }
    }
    
    public void updateScheduleAvailableTrueBySId(Integer sId) {
        String sql = "UPDATE schedule SET available = TRUE WHERE s_id = ?";
        int updateNum = jdbc.update(sql, sId);

        if (updateNum == 0){
            MessageDetails msg = new MessageDetails("Schedule could not be updated. s_id = " + sId, false);
            throw new UpdateErrorException(msg);
        }
    }

    public void updateScheduleAvailableFalseBySId(Integer sId) {
        String sql = "UPDATE schedule SET available = FALSE WHERE s_id = ?";
        int updateNum = jdbc.update(sql, sId);

        if (updateNum == 0){
            MessageDetails msg = new MessageDetails("Schedule could not be updated. s_id = " + sId, false);
            throw new UpdateErrorException(msg);
        }
    }
    
    public void deleteSchedule(Integer sId) {
        String sql = "DELETE FROM schedule WHERE s_id = ?";
        int deleteNum = jdbc.update(sql, sId);

        if (deleteNum == 0){
            MessageDetails msg = new MessageDetails("Schedule could not be deleted. sId = " + sId, false);
            throw new DeleteErrorException(msg);
        }
    }

}
