package com.george.tv.repos;

import com.george.tv.models.Program;
import com.george.tv.models.Schedule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.awt.geom.Line2D;
import java.util.Date;
import java.util.List;

public interface ScheduleRepo extends CrudRepository<Schedule, Long> {
    @Query(value="SELECT * FROM schedule ORDER BY day", nativeQuery=true)
    public Iterable<Schedule> SelectPerDay();

    @Query(value="SELECT * FROM schedule WHERE day='Среда' ORDER BY day", nativeQuery=true)
    public Iterable<Schedule> SelectOnDay();

    List<Schedule> findAllByProgramIn(List<Program> program);
}
