package com.george.tv.models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String company_name;
    private String agent;
    private String product;
    private String description;
    private String duration;
    private String url;
    private String type_of_conversion;

    @ManyToMany
    @JoinTable(name="ads_in_program",
            joinColumns = @JoinColumn(name="ads_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="program_id", referencedColumnName="id"))
    private Set<Program> program_ads;

    public Ads() {
    }

    public Ads(String company_name, String agent, String product, String description, String duration, String url, String type_of_conversion, Set<Program> program) {
        this.company_name = company_name;
        this.agent = agent;
        this.product = product;
        this.description = description;
        this.duration = duration;
        this.url = url;
        this.type_of_conversion = type_of_conversion;
        this.program_ads = program;
    }

    public Set<Program> getProgram() {
        return program_ads;
    }

    public void setProgram(Set<Program> program) {
        this.program_ads = program;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType_of_conversion() {
        return type_of_conversion;
    }

    public void setType_of_conversion(String type_of_conversion) {
        this.type_of_conversion = type_of_conversion;
    }
}
