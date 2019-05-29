package io.internhub.application.controllers;
import io.internhub.application.models.*;
import io.internhub.application.repositories.EmployerProfiles;
import io.internhub.application.repositories.Jobs;
import io.internhub.application.repositories.Users;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class JobsController {

    private EmployerProfiles employerProfiles;
    private Users users;
    private Jobs jobs;
  
    public JobsController(EmployerProfiles employerProfiles, Users users, Jobs jobs) {
        this.employerProfiles = employerProfiles;
        this.users = users;
        this.jobs = jobs;
    }

    @GetMapping("employers/add-job")
    public String getAddJobPage(Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        model.addAttribute("job", new Job());
        return "employers/add-job";
    }

    @PostMapping("employers/add-job")
    public String postAddJobPage(@ModelAttribute Job job) {
        UserWithRoles userWithRoles = (UserWithRoles) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = users.findByUsername(userWithRoles.getUsername());
        EmployerProfile employerProfile = user.getEmployerProfile();
        employerProfile.addJob(job);
        job.setEmployerProfile(employerProfile);
        employerProfiles.save(employerProfile);
        return "redirect:/dashboard";
    }

    @GetMapping("employers/index")
    public String showAllJobs(Model model){
        UserWithRoles userWithRoles = (UserWithRoles) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = users.findByUsername(userWithRoles.getUsername());
        EmployerProfile employerProfile = user.getEmployerProfile();
        List<Job> jobs = employerProfile.getJobs();
        model.addAttribute("jobs", jobs);
        return("employers/index");

    }

//    @GetMapping("interns/index")
//    public String showAllInternJobs(Model model){
//        UserWithRoles userWithRoles = (UserWithRoles) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = users.findByUsername(userWithRoles.getUsername());
//        InternProfile userProfile = user.getInternProfile();
//        if(userProfile.isApproved()){
//        List<Job> relevantJobs = jobs.findByIndustry(userProfile.getField_1(), userProfile.getField_2(), userProfile.getField_3());
//        if(relevantJobs != null) {
//            model.addAttribute("jobsList", relevantJobs);
//        }else{
//            Iterable<Job> allJobs = jobs.findAll();
//            boolean isRelevant = false;
//            model.addAttribute("isRelevant", isRelevant);
//            model.addAttribute("jobsList",((List<Job>) allJobs));
//        }
//        return("interns/index");}
//        else{
//            return("redirect:/interns/profile-register");
//        }
//
//    }
//
//    @GetMapping("interns/applied_index")
//    public String showAllAppliedJobs(Model model){
//        UserWithRoles userWithRoles = (UserWithRoles) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = users.findByUsername(userWithRoles.getUsername());
//        InternProfile userProfile = user.getInternProfile();
//        if(userProfile.isApproved()){
//            if(userProfile.getAppliedJobs().isEmpty()) {
//                String message = "Positions you have applied for will appear here";
//                model.addAttribute("message", message);
//                return ("applied-index");
//            }
//        }
//        return("redirect:/interns/profile-register");
//    }

    @GetMapping("jobs/{jobId}")
    public String getJobPage (Model model, @PathVariable String jobId) {
        boolean userHasAppliedToJob = false;
        boolean userOwnsJob = false;
        UserWithRoles userWithRoles = (UserWithRoles) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = users.findByUsername(userWithRoles.getUsername());
        if (user.getInternProfile() != null) {
            InternProfile internProfile = user.getInternProfile();
            List<Job> appliedJobs = internProfile.getAppliedJobs();
            for (Job job : appliedJobs) {
                if (job.getId() == Long.parseLong(jobId)) {
                    userHasAppliedToJob = true;
                }
            }
            model.addAttribute("userHasApplied", userHasAppliedToJob);
        }
        if (user.getEmployerProfile() != null) {
            EmployerProfile employerProfile = user.getEmployerProfile();
            List<Job> ownedJobs = employerProfile.getJobs();
            for (Job job : ownedJobs) {
                if (job.getId() == Long.parseLong(jobId)) {
                    userOwnsJob = true;
                }
            }
            model.addAttribute("userOwnsJob", userOwnsJob);
        }
        Long jobLongId = Long.parseLong(jobId);
        Job job = jobs.findOne(jobLongId);
        model.addAttribute("job", job);
        return "jobs/post";
    }

    @GetMapping("jobs/{jobId}/applicants")
    public String getJobApplicants (Model model, @PathVariable String jobId) {
        boolean userOwnJob = false;
        UserWithRoles userWithRoles = (UserWithRoles) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = users.findByUsername(userWithRoles.getUsername());
        EmployerProfile employerProfile = user.getEmployerProfile();
        List<Job> jobList = employerProfile.getJobs();
        for (Job job : jobList) {
            if (job.getId() == Long.parseLong(jobId)) {
                userOwnJob = true;
            }
        }
        Job job = jobs.findOne(Long.parseLong(jobId));
        List<InternProfile> internProfiles = job.getInternProfiles();
        model.addAttribute("internProfiles", internProfiles);
        model.addAttribute("userOwnsJob", userOwnJob);
        return "jobs/applicants";
    }

    @GetMapping("jobs/search/{query}")
    public String getSearchedJobs (Model model, @PathVariable String query) {
        List<Job> searchedJobs = new ArrayList<>();
        Iterable<Job> allJobs = jobs.findAll();
        List<Job> allJobsList = ((List<Job>) allJobs);
        for (Job job : allJobsList) {
            System.out.println(job.getTitle());
            if (job.getTitle().contains(query)) {
                searchedJobs.add(job);
            }
        }
        model.addAttribute("searchedJobs", searchedJobs);
        return "jobs/searched-jobs";
    }

}