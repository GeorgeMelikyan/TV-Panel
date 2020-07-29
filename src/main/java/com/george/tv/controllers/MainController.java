package com.george.tv.controllers;

import com.george.tv.models.*;
import com.george.tv.repos.*;
import org.apache.tomcat.jni.Proc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.security.PermitAll;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MainController {
    @Autowired
    private ScheduleRepo scheduleRepo;

    @Autowired
    private AdsRepo adsRepo;

    @Autowired
    private ProgramRepo programRepo;

    @Autowired
    private WorkGroupRepo workGroupRepo;

    @Autowired
    private EquipRepo equipRepo;

    @GetMapping("/login")
    private String login(Model model) {
        return "login";
    }

    @GetMapping("/register")
    private String register(Model model) {
        return "register";
    }

    @GetMapping("/")
    private String main(Model model) {
        Iterable<Schedule> schedule = scheduleRepo.SelectOnDay();
        model.addAttribute("schedule", schedule);
        return "main";
    }

    @PostMapping("/")
    private String mainSearch(@RequestParam String name, Model model) {
        String new_name = '%' + name + '%';
        Iterable<Program> programs = programRepo.findByNameLike(new_name);

        Optional<Program> pr = programRepo.findById(programs.iterator().next().getId());
        ArrayList<Program> res = new ArrayList<>();
        pr.ifPresent(res::add);


        List<Schedule> schedule = scheduleRepo.findAllByProgramIn(res);


        System.out.println(programs.iterator().next().getName());
        model.addAttribute("programs", programs);
        model.addAttribute("schedule", schedule);
        return "main-search";
    }

    @GetMapping("/program")
    private String Program(Model model) {
        Iterable<Program> programs = programRepo.findAll();
        Iterable<WorkGroup> wg = workGroupRepo.findAll();
        Iterable<Equip> eq = equipRepo.findAll();
        model.addAttribute("programs", programs);
        model.addAttribute("wg", wg);
        model.addAttribute("eq", eq);
        return "programs";
    }

    @PostMapping("/program")
    private String addProgram(@RequestParam String name, @RequestParam String description, @RequestParam String duration, @RequestParam String url, @RequestParam Long wg_id, @RequestParam Long eq_id,  Model model) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date dura = formatter.parse(duration);


        WorkGroup wg = workGroupRepo.findById(wg_id).orElseThrow();
        Equip eq = equipRepo.findById(eq_id).orElseThrow();
        Program program = new Program(name, description, dura, url, wg, eq, null);
        programRepo.save(program);

        return Program(model);
    }



    @GetMapping("/program/edit/{id}")
    private String editProgram(@PathVariable(name="id") Long id, Model model) {
        Program program = programRepo.findById(id).orElseThrow();
        Iterable<WorkGroup> wg = workGroupRepo.findAll();
        Iterable<Equip> eq = equipRepo.findAll();
        Iterable<Ads> ads = adsRepo.findAll();
        model.addAttribute("programs", program);
        model.addAttribute("wg", wg);
        model.addAttribute("eq", eq);
        model.addAttribute("ads", ads);
        return "programs-edit";
    }

    @PostMapping("/program/edit/{id}")
    public String editProgramConfirm(@PathVariable(value = "id") long id, @RequestParam String name,
            @RequestParam String duration, @RequestParam String description, @RequestParam String url,
                                     @RequestParam Long ads_id,
                                     @RequestParam Long wg_id, @RequestParam Long eq_id, Model model) throws ParseException {
        WorkGroup wg = workGroupRepo.findById(wg_id).orElseThrow();
        Equip eq = equipRepo.findById(eq_id).orElseThrow();
        Program program = programRepo.findById(id).orElseThrow();

        Set<Program> program_set = new LinkedHashSet<>();
        program_set.add(program);

        if(ads_id != null) {
            Ads ads = adsRepo.findById(ads_id).orElseThrow();
            Set<Ads> ads_set = new LinkedHashSet<>();
            ads_set.add(ads);

            if(!(program.getAds().isEmpty())) {
                Ads old_ads_id = adsRepo.findById(program.getAds().iterator().next().getId()).orElseThrow();
                old_ads_id.setProgram(null);
                ads.setProgram(program_set);
                adsRepo.save(ads);
            }
            else {
                ads.setProgram(program_set);
                adsRepo.save(ads);
            }
        }




        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date dura = formatter.parse(duration);


        program.setName(name);
        program.setDescription(description);
        program.setDuration(dura);
        program.setUrl(url);
        program.setEquip(eq);
        program.setWg(wg);


        programRepo.save(program);
        return "redirect:/program";
    }

    @PostMapping("/program/delete/{id}")
    private String deleteProgram(@PathVariable(name = "id") Long id, Model model) {
        Program deleted = programRepo.findById(id).orElseThrow();
        programRepo.delete(deleted);
        return "redirect:/program";
    }

}

