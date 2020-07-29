package com.george.tv.models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="work_group")
public class WorkGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "groups")
    private List<Personal> personals;

    public List<Personal> getPersonals() {
        return personals;
    }

    public void setPersonals(List<Personal> personals) {
        this.personals = personals;
    }


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "wg")
    private Set<Program> program;

    public Set<Program> getProgram() {
        return program;
    }

    public void setProgram(Set<Program> program) {
        this.program = program;
    }

    public WorkGroup(){}

    public WorkGroup(String name) {
        this.name = name;
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
}
