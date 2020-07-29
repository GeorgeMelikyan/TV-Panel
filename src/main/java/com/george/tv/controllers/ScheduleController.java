package com.george.tv.controllers;

import com.george.tv.models.Ads;
import com.george.tv.models.Program;
import com.george.tv.models.Schedule;
import com.george.tv.models.WorkGroup;
import com.george.tv.repos.ProgramRepo;
import com.george.tv.repos.ScheduleRepo;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ScheduleController {
    @Autowired
    private ScheduleRepo scheduleRepo;

    @Autowired
    private ProgramRepo programRepo;

    @GetMapping("/schedule")
    public String main(Model model){
        Iterable<Schedule> schedule = scheduleRepo.SelectPerDay();
        model.addAttribute("schedule", schedule);
        Iterable<Program> programs = programRepo.findAll();
        model.addAttribute("programs", programs);
        return "schedule";
    }

    @PostMapping("/schedule/add")
    public String add(@RequestParam String day, @RequestParam String time,
                      @RequestParam Long program_id, Model model) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date pr_time = formatter.parse(time);

        Program pr = programRepo.findById(program_id).orElseThrow();
        Set<Program> program_set = new LinkedHashSet<>();
        program_set.add(pr);

        Schedule schedule = new Schedule(day,pr_time,program_set);

        Program program = programRepo.findById(program_id).orElseThrow();
        Set<Schedule> schedule_set = new LinkedHashSet<>();
        schedule_set.add(schedule);

        program.setSchedule(schedule_set);

        scheduleRepo.save(schedule);
        programRepo.save(program);

        return "redirect:/schedule";
    }

    @GetMapping("/schedule/edit/{id}")
    public String edit(@PathVariable(name = "id") Long id, Model model){
        Schedule schedule = scheduleRepo.findById(id).orElseThrow();
        model.addAttribute("schedule", schedule);
        Iterable<Program> programs = programRepo.findAll();
        model.addAttribute("programs", programs);
        return "schedule-edit";
    }

    @PostMapping("/schedule/edit/{id}")
    public String editPost(@PathVariable(name = "id") Long id, @RequestParam String day, @RequestParam String program_time,
                      @RequestParam Long program_id, Model model) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date pr_time = formatter.parse(program_time);

        Program program = programRepo.findById(program_id).orElseThrow();
        Set<Program> program_set = new LinkedHashSet<>();
        program_set.add(program);

        Schedule schedule = scheduleRepo.findById(id).orElseThrow();
        schedule.setDay(day);
        schedule.setprogram_time(pr_time);
        schedule.setProgram(program_set);

        scheduleRepo.save(schedule);

        return "redirect:/schedule";
    }

    @PostMapping("/schedule/delete/{id}")
    public String deletePost(@PathVariable(name = "id") Long id, Model model) throws ParseException {

        Schedule schedule = scheduleRepo.findById(id).orElseThrow();

        scheduleRepo.delete(schedule);

        return "redirect:/schedule";
    }

}
