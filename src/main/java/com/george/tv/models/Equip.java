package com.george.tv.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Equip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String cameras;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "equip")
    private Set<Program> program;

    public Equip() {
    }

    public Equip(String cameras, Set<Program> program) {
        this.cameras = cameras;
        this.program = program;
    }

    public Equip(String cameras) {
        this.cameras = cameras;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCameras() {
        return cameras;
    }

    public void setCameras(String cameras) {
        this.cameras = cameras;
    }

    public Set<Program> getProgram() {
        return program;
    }

    public void setProgram(Set<Program> program) {
        this.program = program;
    }
}
