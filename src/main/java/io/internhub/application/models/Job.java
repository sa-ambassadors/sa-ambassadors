package io.internhub.application.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "jobs")
public class Job {

    @Id @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String industry;

    @Column(nullable = false)
    private String location;
  
    @Column(nullable = false)
    private String jobDescription;

    @Column(nullable = false)
    private String responsibilities;

    @Column(nullable = false)
    private String basicQualifications;

    @Column(nullable = false)
    private String aboutUs;

    @ManyToOne
    @JoinColumn(name = "employer_id")
    private EmployerProfile employerProfile;

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



    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
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

    public List<InternProfile> getInternProfiles() {
        return internProfiles;
    }

    public void setInternProfiles(List<InternProfile> internProfiles) {
        this.internProfiles = internProfiles;
    }
}