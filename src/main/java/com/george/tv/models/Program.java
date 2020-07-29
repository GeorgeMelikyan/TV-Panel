package com.george.tv.models;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity

@NamedQuery(name = "Program.findByNameLike", query = "SELECT p FROM Program p WHERE p.name LIKE ?1 ")

public class Program {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    @Temporal(TemporalType.TIME)
    private Date duration;
    private String url;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private WorkGroup wg;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Equip equip;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL, mappedBy = "program_ads")
    private Set<Ads> ads;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL, mappedBy = "program")
    private Set<Schedule> schedule;

    public Set<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(Set<Schedule> schedule) {
        this.schedule = schedule;
    }

    public Program() {
    }

    public Program(String name, String description, Date duration, String url, WorkGroup wg, Equip equip, Set<Ads> ads) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.url = url;
        this.wg = wg;
        this.equip = equip;
        this.ads = ads;
    }

    public Set<Ads> getAds() {
        return ads;
    }

    public void setAds(Set<Ads> ads) {
        this.ads = ads;
    }

    public WorkGroup getWg() {
        return wg;
    }

    public void setWg(WorkGroup wg) {
        this.wg = wg;
    }

    public Equip getEquip() {
        return equip;
    }

    public void setEquip(Equip equip) {
        this.equip = equip;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDuration() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String new_duration = formatter.format(duration);
        return new_duration;
    }

    public void setDuration(Date duration) {
        this.duration = duration;
    }
}
