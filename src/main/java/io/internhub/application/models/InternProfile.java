package io.internhub.application.models;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="intern_profile")
public class InternProfile {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String first_name;

    @Column(nullable = false)
    private String last_name;

    @Column
    private String major;

    @Column
    private String minor;

    @Column
    private int field_1;

    @Column
    private int field_2;

    @Column
    private int field_3;

    @Column
    private String transcript_link;

    @Column
    private String essay_link;

    @Column
    private boolean sa_highschool;

    @Column
    private boolean sa_college;

    @Column
    private boolean first_to_college;

    @Column
    private boolean sa_educational_program;

    @Column
    private boolean isPriority;

    @Column
    private boolean isComplete;

    @Column
    private boolean isApproved;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "applied_jobs", joinColumns = @JoinColumn(name = "intern_id"),
            inverseJoinColumns = @JoinColumn(name = "job_id"))
    private List<Job> appliedJobs = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public int getField_1() {
        return field_1;
    }

    public void setField_1(int field_1) {
        this.field_1 = field_1;
    }

    public int getField_2() {
        return field_2;
    }

    public void setField_2(int field_2) {
        this.field_2 = field_2;
    }

    public int getField_3() {
        return field_3;
    }

    public void setField_3(int field_3) {
        this.field_3 = field_3;
    }

    public String getTranscipt_link() {
        return transcript_link;
    }

    public void setTranscipt_link(String transcipt_link) {
        this.transcript_link = transcipt_link;
    }

    public String getEssay_link() {
        return essay_link;
    }

    public void setEssay_link(String essay) {
        this.essay_link = essay_link;
    }

    public boolean isSa_highschool() {
        return sa_highschool;
    }

    public void setSa_highschool(boolean sa_highschool) {
        this.sa_highschool = sa_highschool;
    }

    public boolean isSa_college() {
        return sa_college;
    }

    public void setSa_college(boolean sa_college) {
        this.sa_college = sa_college;
    }

    public boolean isFirst_to_college() {
        return first_to_college;
    }

    public void setFirst_to_college(boolean first_to_college) {
        this.first_to_college = first_to_college;
    }

    public boolean isSa_educational_program() {
        return sa_educational_program;
    }

    public void setSa_educational_program(boolean sa_educational_program) {
        this.sa_educational_program = sa_educational_program;
    }

    public boolean isPriority() {
        return isPriority;
    }

    public void setPriority(boolean priority) {
        this.isPriority = isPriority;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addJobToAppliedList (Job job) {
        appliedJobs.add(job);
    }

    public List<Job> getAppliedJobs() {
        return appliedJobs;
    }

    public void setAppliedJobs(List<Job> appliedJobs) {
        this.appliedJobs = appliedJobs;
    }
}