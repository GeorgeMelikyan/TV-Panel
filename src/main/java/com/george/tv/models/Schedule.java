package com.george.tv.models;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String day;

    @Temporal(TemporalType.TIME)
    public Date program_time;


    @ManyToMany
    @JoinTable(name="schedule_final",
            joinColumns = @JoinColumn(name="day_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="program_id", referencedColumnName="id")
    )
    private Set<Program> program;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Schedule() {
    }

    public Schedule(String day, Date program_time, Set<Program> program) {
        this.day = day;
        this.program_time = program_time;
        this.program = program;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getprogram_time() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String pr_time = formatter.format(program_time);

        return pr_time;
    }

    public void setprogram_time(Date date) {
        this.program_time = date;
    }

    public Set<Program> getProgram() {
        return program;
    }

    public void setProgram(Set<Program> program) {
        this.program = program;
    }
}
