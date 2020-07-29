package com.george.tv.repos;

import com.george.tv.models.Program;
import com.george.tv.models.Schedule;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProgramRepo extends CrudRepository<Program, Long> {
    List<Program> findAllByScheduleIn(List<Schedule> schedule);
    List<Program> findByNameLike(String name);
}
