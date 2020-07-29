package com.george.tv.controllers;

import com.george.tv.models.Personal;
import com.george.tv.models.WorkGroup;
import com.george.tv.repos.PersonalRepo;
import com.george.tv.repos.WorkGroupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class TeamController {
    @Autowired
    private PersonalRepo personalRepo;

    @Autowired
    private WorkGroupRepo workGroupRepo;

    @GetMapping("/team")
    public String team(Model model) {
        Iterable<Personal> team = personalRepo.findAll();
        Iterable<WorkGroup> wg = workGroupRepo.findAll();

        model.addAttribute("team", team);
        model.addAttribute("wg", wg);
        return "team";
    }

    // РАБОЧАЯ ГРУППА

    @PostMapping("/team/add-wg")
    public String addWG(@RequestParam String name, Model model) {

        WorkGroup wg = new WorkGroup(name);
        workGroupRepo.save(wg);

        return team(model);
    }

    // Страница редактирования группы

    @GetMapping("/team/{id}/edit-wg")
    public String editWG(@PathVariable(value = "id") long id, Model model) {

        Optional<WorkGroup> wg = workGroupRepo.findById(id);
        ArrayList<WorkGroup> res = new ArrayList<>();
        wg.ifPresent(res::add);

        Iterable<Personal> team = personalRepo.findAllByGroupsIn(res);

        Iterable<Personal> team_all = personalRepo.findAll();

        model.addAttribute("wg", res);
        model.addAttribute("team_all", team_all);
        model.addAttribute("team", team);

        return "team-edit-wg";
    }

    @PostMapping("/team/{id}/edit-wg")
    public String editWGPost(@PathVariable(value = "id") long id, @RequestParam String name, Model model) {

        WorkGroup wg = workGroupRepo.findById(id).orElseThrow();
        wg.setName(name);
        workGroupRepo.save(wg);

        return team(model);
    }

    // Добавление сотрудника

    @PostMapping("/team/{group_id}/edit-wg/add-emp")
    public String addWGEmp(@PathVariable(value = "group_id") long group_id, @RequestParam(value = "personal_id")
            long personal_id, Model model) {

        Personal emp = personalRepo.findById(personal_id).orElseThrow();

        Optional<WorkGroup> wg = workGroupRepo.findById(group_id);
        ArrayList<WorkGroup> res = new ArrayList<>();
        wg.ifPresent(res::add);

        emp.setGroups(res);
        personalRepo.save(emp);
        return editWG(group_id, model);
    }

    // Удаление сотрудника из группы

    @PostMapping("/team/{group_id}/remove-emp/{user_id}")
    public String removeWGEmp(@PathVariable(value = "user_id")
            long personal_id, @PathVariable(value = "group_id") long group_id, Model model) {
            Personal emp = personalRepo.findById(personal_id).orElseThrow();
            emp.setGroups(null);
            personalRepo.save(emp);
        return editWG(group_id, model);
    }




    // ГЛАВНАЯ СТРАНИЦА

    @PostMapping("/team/{id}/remove-wg")
    public String removeWG(@PathVariable(value = "id") long id, Model model) {


        Optional<WorkGroup> wg = workGroupRepo.findById(id);
        ArrayList<WorkGroup> res = new ArrayList<>();
        wg.ifPresent(res::add);

        Iterable<Personal> team = personalRepo.findAllByGroupsIn(res);
        for(Personal person : team) {
            person.setGroups(null);
            personalRepo.save(person);
        }

        WorkGroup deleted = workGroupRepo.findById(id).orElseThrow();
        workGroupRepo.delete(deleted);

        return team(model);
    }


    // СОТРУДНИК

    @PostMapping("/team/add-employ")
    public String add(@RequestParam String name, @RequestParam String phone,
                          @RequestParam String birth, @RequestParam String position, Model model) {

        if (!name.isEmpty() & !phone.isEmpty() & !birth.isEmpty() & !position.isEmpty()) {
            Personal user = new Personal(name, phone, birth, position);
            personalRepo.save(user);
        } else {
            String error = "Не все поля заполнены верно";
            model.addAttribute("error", error);

            Iterable<Personal> team = personalRepo.findAll();
            model.addAttribute("team", team);

            return "team";
        }

        Iterable<Personal> team = personalRepo.findAll();
        model.addAttribute("team", team);

        return team(model);
    }

    @GetMapping("/team/{id}/edit-employ")
    public String edit(@PathVariable(value = "id") long id, Model model) {

        Optional<Personal> user = personalRepo.findById(id);
        ArrayList<Personal> res = new ArrayList<>();
        user.ifPresent(res::add);
        model.addAttribute("user", res);

        return "team-edit";
    }

    @PostMapping("/team/{id}/edit-employ")
    public String editUser(@PathVariable(value = "id") long id, @RequestParam String name, @RequestParam String phone,
                       @RequestParam String birth, @RequestParam String position, Model model) {

        Personal user = personalRepo.findById(id).orElseThrow();
        user.setName(name);
        user.setPhone(phone);
        user.setBirth(birth);
        user.setPosition(position);
        personalRepo.save(user);

        return team(model);
    }

    @PostMapping("/team/{id}/remove-employ")
    public String removeUser(@PathVariable(value = "id") long id, Model model) {

        Personal user = personalRepo.findById(id).orElseThrow();
        personalRepo.delete(user);

        return team(model);
    }



}