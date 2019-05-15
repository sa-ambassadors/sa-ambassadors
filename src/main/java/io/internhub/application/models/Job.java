package io.internhub.application.models;


import javax.persistence.*;


@Entity
@Table(name = "jobs")
public class Job {

    @Id @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;




    public Job() {
    }



    public String getTitle() {
        return getTitle();
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDescription() {
        return getDescription();
    }

    public void setDescription(String description) {
        this.description = description;
    }



}  // Jobs class