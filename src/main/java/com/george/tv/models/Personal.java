package com.george.tv.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "personal")
public class Personal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String phone;
    private String birth;
    private String position;



    public Personal(){
    }

    public Personal(String name, String phone, String birth, String position) {
        this.name = name;
        this.phone = phone;
        this.position = position;
        this.birth = birth;
    }

    @ManyToMany
    @JoinTable(name="list_of_work_group",
            joinColumns = @JoinColumn(name="personal_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="work_group_id", referencedColumnName="id")
    )
    public List<WorkGroup> groups;

    public List<WorkGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<WorkGroup> groups) {
        this.groups = groups;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
