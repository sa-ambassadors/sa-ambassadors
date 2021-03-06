package io.internhub.application.controllers;

import io.internhub.application.models.*;
import io.internhub.application.repositories.EmployerProfiles;
import io.internhub.application.repositories.InternRepository;
import io.internhub.application.repositories.Jobs;
import io.internhub.application.repositories.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@SuppressWarnings("Duplicates")
@RestController
@RequestMapping("/api/v1")
public class ApiController {

    private Users users;
    private Jobs jobs;
    private EmployerProfiles employerProfiles;
    private InternRepository internRepository;

    public ApiController(Users users, Jobs jobs, EmployerProfiles employerProfiles, InternRepository internRepository) {
        this.users = users;
        this.jobs = jobs;
        this.employerProfiles = employerProfiles;
        this.internRepository = internRepository;
    }

    @GetMapping("/{user}/add/{job}")
    public ResponseEntity<?> addAppliedJobToUserObject(@PathVariable String user, @PathVariable String job) {
        User foundUser = users.findByUsername(user);
        if (foundUser == null) {
            ResponseEntity.badRequest().build();
        }
        Job foundJob = jobs.findOne(Long.parseLong(job));
        if (foundJob == null) {
            ResponseEntity.badRequest().build();
        }
        System.out.println(foundJob.getAboutUs());
        System.out.println(foundUser.getUsername());
        InternProfile internProfile = foundUser.getInternProfile();
        foundJob.addToInternProfile(internProfile);
        internProfile.addJobToAppliedList(foundJob);
        jobs.save(foundJob);
        return ResponseEntity.ok("Intern profile added to job");
    }

    @PatchMapping("/{user}/employer/profile/edit")
    public ResponseEntity<EmployerProfile> updateEmployerProfileWithEditedData(@PathVariable String user, @RequestBody EmployerProfile updatingEmployerProfile) {
        User foundUser = users.findByUsername(user);
        if (foundUser == null) {
            ResponseEntity.badRequest().build();
        }
        EmployerProfile foundEmployerProfile = foundUser.getEmployerProfile();
        if (updatingEmployerProfile.getEmail() != null) {
            foundEmployerProfile.setEmail(updatingEmployerProfile.getEmail());
        }
        if (updatingEmployerProfile.getDescription() != null) {
            foundEmployerProfile.setDescription(updatingEmployerProfile.getDescription());
        }
        if (updatingEmployerProfile.getIndustry() != null) {
            foundEmployerProfile.setIndustry(updatingEmployerProfile.getIndustry());
        }
        if (updatingEmployerProfile.getLocation() != null) {
            foundEmployerProfile.setLocation(updatingEmployerProfile.getLocation());
        }
        if (updatingEmployerProfile.getName() != null) {
            foundEmployerProfile.setName(updatingEmployerProfile.getName());
        }
        if (updatingEmployerProfile.getSupervisor() != null) {
            foundEmployerProfile.setSupervisor(updatingEmployerProfile.getSupervisor());
        }
        if (updatingEmployerProfile.getWebsiteUrl() != null) {
            foundEmployerProfile.setWebsiteUrl(updatingEmployerProfile.getWebsiteUrl());
        }
        employerProfiles.save(foundEmployerProfile);
        return ResponseEntity.ok(foundEmployerProfile);
    }

    @PatchMapping("{user}/intern/profile/edit")
    public ResponseEntity<InternProfile> updateInternProfileWithEditedData(@PathVariable String user, @RequestBody InternProfile updatingInternProfile) {
        User foundUser = users.findByUsername(user);
        if (foundUser == null) {
            ResponseEntity.badRequest().build();
        }
        InternProfile internProfile = foundUser.getInternProfile();
        if (updatingInternProfile.getEssay_link() != null) {
            internProfile.setEssay_link(updatingInternProfile.getEssay_link());
        }
        if (updatingInternProfile.getField_1() != null) {
            internProfile.setField_1(updatingInternProfile.getField_1());
        }
        if (updatingInternProfile.getField_2() != null) {
            internProfile.setField_2(updatingInternProfile.getField_2());
        }
        if (updatingInternProfile.getField_3() != null) {
            internProfile.setField_3(updatingInternProfile.getField_3());
        }
        if (updatingInternProfile.getFirst_name() != null) {
            internProfile.setFirst_name(updatingInternProfile.getFirst_name());
        }
        if (updatingInternProfile.getLast_name() != null) {
            internProfile.setLast_name(updatingInternProfile.getLast_name());
        }
        if (updatingInternProfile.getMajor() != null) {
            internProfile.setMajor(updatingInternProfile.getMajor());
        }
        if (updatingInternProfile.getMinor() != null) {
            internProfile.setMinor(updatingInternProfile.getMinor());
        }
        if (updatingInternProfile.getTranscript_link() != null) {
            internProfile.setTranscript_link(updatingInternProfile.getTranscript_link());
        }
        if (updatingInternProfile.isSa_highschool() != internProfile.isSa_highschool()) {
            internProfile.setSa_highschool(updatingInternProfile.isSa_highschool());
        }
        if (updatingInternProfile.isFirst_to_college() != internProfile.isFirst_to_college()) {
            internProfile.setFirst_to_college(updatingInternProfile.isFirst_to_college());
        }
        if (updatingInternProfile.isSa_educational_program() != internProfile.isSa_educational_program()) {
            internProfile.setSa_educational_program(updatingInternProfile.isSa_educational_program());
        }
        if (updatingInternProfile.isSa_college() != internProfile.isSa_college()) {
            internProfile.setSa_college(updatingInternProfile.isSa_college());
        }
        if (updatingInternProfile.isComplete() != internProfile.isComplete()) {
            internProfile.setComplete(updatingInternProfile.isComplete());
        }
        internRepository.save(internProfile);
        return ResponseEntity.ok(internProfile);
    }

    @DeleteMapping("{user}/employer/delete/{job}")
    public ResponseEntity<?> deleteJobFromDatabase(@PathVariable String user, @PathVariable String job) {
        User foundUser = users.findByUsername(user);
        boolean userOwnsJob = false;
        if (foundUser == null) {
            ResponseEntity.badRequest().build();
        }
        EmployerProfile employerProfile = foundUser.getEmployerProfile();
        List<Job> employerJobs = employerProfile.getJobs();
        for (Job j : employerJobs) {
            if (j.getId() == Long.parseLong(job)) {
                userOwnsJob = true;
            }
        }
        if (userOwnsJob) {
            jobs.delete(Long.parseLong(job));
            return ResponseEntity.ok("Job deleted");
        }
        return ResponseEntity.ok("User does not own job");
    }

    @PatchMapping("{user}/employer/edit/{job}")
    public ResponseEntity<Job> editEmployerJobPost(@PathVariable String user, @PathVariable String job, @RequestBody Job updatedJob) {
        User foundUser = users.findByUsername(user);
        Job fakeJob = new Job();
        boolean userOwnsJob = false;
        if (foundUser == null) {
            ResponseEntity.badRequest().build();
        }
        EmployerProfile employerProfile = foundUser.getEmployerProfile();
        List<Job> employerJobs = employerProfile.getJobs();
        for (Job j : employerJobs) {
            if (j.getId() == Long.parseLong(job)) {
                userOwnsJob = true;
            }
        }
        if (userOwnsJob) {
            Job foundJob = jobs.findOne(Long.parseLong(job));
            if (updatedJob.getAboutUs() != null) {
                foundJob.setAboutUs(updatedJob.getAboutUs());
            }
            if (updatedJob.getBasicQualifications() != null) {
                foundJob.setBasicQualifications(updatedJob.getBasicQualifications());
            }
            if (updatedJob.getCompanyName() != null) {
                foundJob.setCompanyName(updatedJob.getCompanyName());
            }
            if (updatedJob.getIndustry() != 99) {
                foundJob.setIndustry(updatedJob.getIndustry());
            }
            if (updatedJob.getLocation() != null) {
                foundJob.setLocation(updatedJob.getLocation());
            }

            jobs.save(foundJob);
            return ResponseEntity.ok(foundJob);
        }
        return ResponseEntity.ok(fakeJob);
    }

//    @GetMapping("/register/user/{username}")
//    public ResponseEntity<Boolean> checkIfUsernameIsPresentInDatabase(@PathVariable String username) {
//        Boolean userExistsInDataBase = false;
//        Iterable<User> allUsers = users.findAll();
//        Iterator<User> totalUsers = allUsers.iterator();
//        while(totalUsers.hasNext()) {
//            if (totalUsers.next().getUsername().equals(username)) {
//                userExistsInDataBase = true;
//            }
//        }
//        return ResponseEntity.ok(userExistsInDataBase);
//    }
//
//    @GetMapping("/register/email/{email}")
//    public ResponseEntity<Boolean> checkIfEmailIsPresentInDatabase(@PathVariable String email) {
//        Boolean emailExistsInDataBase = false;
//        Iterable<User> allUsers = users.findAll();
//        Iterator<User> totalUsers = allUsers.iterator();
//        while(totalUsers.hasNext()) {
//            if (totalUsers.next().getEmail().equals(email)) {
//                emailExistsInDataBase = true;
//            }
//        }
//        return ResponseEntity.ok(emailExistsInDataBase);
//    }
}


