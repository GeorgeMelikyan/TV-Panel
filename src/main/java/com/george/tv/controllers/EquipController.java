package com.george.tv.controllers;

import com.george.tv.models.Equip;
import com.george.tv.models.Personal;
import com.george.tv.models.WorkGroup;
import com.george.tv.repos.EquipRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class EquipController {

    @Autowired
    private EquipRepo equipRepo;

    @GetMapping("/equip")
    private String main(Model model) {
        Iterable<Equip> eq = equipRepo.findAll();
        model.addAttribute("equip", eq);
        return "equip";
    }

    @PostMapping("/equip/add")
    public String addEquip(@RequestParam String cameras, Model model) {
        Equip eq = new Equip(cameras);
        equipRepo.save(eq);
        return "redirect:/equip";
    }

    @GetMapping("/equip/edit/{id}")
    public String editEquip(@PathVariable(value = "id") long id, Model model) {

        Equip eq = equipRepo.findById(id).orElseThrow();

        model.addAttribute("equip", eq);
        return "equip-edit";
    }

    @PostMapping("/equip/edit/{id}")
    public String editEquipPost(@PathVariable(value = "id") long id, @RequestParam String cameras, Model model) {
        Equip eq = equipRepo.findById(id).orElseThrow();
        eq.setCameras(cameras);
        equipRepo.save(eq);
        return "redirect:/equip";
    }

    @PostMapping("/equip/delete/{id}")
    public String deleteEquip(@PathVariable(value = "id") long id, Model model) {
        Equip eq = equipRepo.findById(id).orElseThrow();
        equipRepo.delete(eq);
        return "redirect:/equip";
    }


}
