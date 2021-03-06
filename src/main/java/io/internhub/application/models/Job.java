package io.internhub.application.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private int industry;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false, length = 1000)
    private String jobDescription;

    @Column(nullable = false)
    private String responsibilities;

    @Column(nullable = false)
    private String basicQualifications;

    @Column(nullable = false)
    private String aboutUs;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "employer_id")
    private EmployerProfile employerProfile;

    @JsonIgnore
    @ManyToMany(mappedBy = "appliedJobs")
    List<InternProfile> internProfiles = new ArrayList<>();

    public Job() {
    }


    // Getters & Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    public int getIndustry() {
        return industry;
    }

    public void setIndustry(int industry) {
        this.industry = industry;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getJobDescription() {
        return jobDescription;

    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }


    public String getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }


    public String getBasicQualifications() {
        return basicQualifications;
    }

    public void setBasicQualifications(String basicQualifications) {
        this.basicQualifications = basicQualifications;

    }


    public String getAboutUs() {
        return aboutUs;
    }

    public void setAboutUs(String aboutUs) {
        this.aboutUs = aboutUs;
    }


    public EmployerProfile getEmployerProfile() {
        return employerProfile;
    }

    public void setEmployerProfile(EmployerProfile employerProfile) {
        this.employerProfile = employerProfile;
    }


    public void addToInternProfile(InternProfile internProfile) {
        internProfiles.add(internProfile);
    }

    public List<InternProfile> getInternProfiles() {
        return internProfiles;
    }

    public void setInternProfiles(List<InternProfile> internProfiles) {
        this.internProfiles = internProfiles;

    }
}