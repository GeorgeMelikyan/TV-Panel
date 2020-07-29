package com.george.tv.controllers;

import com.george.tv.models.Ads;
import com.george.tv.repos.AdsRepo;
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
public class AdsController {
    @Autowired
    private AdsRepo adsRepo;

    @GetMapping("/ads")
    private String main(Model model) {
        Iterable<Ads> ads = adsRepo.findAll();
        model.addAttribute("ads", ads);
        return "ads";
    }

    @PostMapping("/ads/add")
    public String add(@RequestParam String company_name, @RequestParam String product,
                      @RequestParam String description, @RequestParam String agent,
                      @RequestParam String duration, @RequestParam String url, @RequestParam String type_of_conversion,
                      Model model) {

        Ads ad = new Ads(company_name, agent, product, description, duration, url, type_of_conversion, null);
        adsRepo.save(ad);
        return "redirect:/ads";
    }

    @GetMapping("/ads/edit/{id}")
    public String edit(@PathVariable(value = "id") long id, Model model) {

        Ads ads = adsRepo.findById(id).orElseThrow();
        model.addAttribute("ads", ads);

        return "ads-edit";
    }

    @PostMapping("/ads/edit/{id}")
    public String editPost(@PathVariable(value = "id") long id, @RequestParam String company_name,
                           @RequestParam String product, @RequestParam String description,
                           @RequestParam String agent, @RequestParam String duration,
                           @RequestParam String url, @RequestParam String type_of_conversion, Model model) {

        Ads ad = adsRepo.findById(id).orElseThrow();

        ad.setCompany_name(company_name);
        ad.setAgent(agent);
        ad.setProduct(product);
        ad.setDescription(description);
        ad.setDuration(duration);
        ad.setUrl(url);
        ad.setType_of_conversion(type_of_conversion);
        adsRepo.save(ad);

        return "redirect:/ads";
    }

    @PostMapping("/ads/delete/{id}")
    public String remove(@PathVariable(value = "id") long id, Model model) {

        Ads ad = adsRepo.findById(id).orElseThrow();
        adsRepo.delete(ad);

        return "redirect:/ads";
    }

}
