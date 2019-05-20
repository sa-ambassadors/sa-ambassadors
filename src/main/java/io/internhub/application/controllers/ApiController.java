package io.internhub.application.controllers;

import io.internhub.application.models.*;
import io.internhub.application.repositories.EmployerProfiles;
import io.internhub.application.repositories.Jobs;
import io.internhub.application.repositories.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

    private Users users;
    private Jobs jobs;
    private EmployerProfiles employerProfiles;

    public ApiController(Users users, Jobs jobs, EmployerProfiles employerProfiles) {
        this.users = users;
        this.jobs = jobs;
        this.employerProfiles = employerProfiles;
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
        InternProfile internProfile = foundUser.getInternProfile();
        foundJob.addToInternProfile(internProfile);
        jobs.save(foundJob);
        return ResponseEntity.ok("Intern profile added to job");
    }

    @PatchMapping("/{user}/edit")
    public ResponseEntity<?> updateEmployerProfileWithEditedData(@PathVariable String user, @RequestBody EmployerProfile updatingEmployerProfile) {
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
        return ResponseEntity.ok("Employer profile saved");

    }
}
