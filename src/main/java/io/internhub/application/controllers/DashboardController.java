package io.internhub.application.controllers;

import io.internhub.application.models.*;
import io.internhub.application.repositories.Jobs;
import io.internhub.application.repositories.Users;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.List;

@Controller
public class DashboardController {

    private Jobs jobs;
    private Users users;

    public DashboardController (Users users, Jobs jobs) {
        this.users = users;
        this.jobs = jobs;
    }

    @GetMapping("dashboard")
    public String getDashboardPage(Model model) {
        UserWithRoles userWithRoles = (UserWithRoles) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = users.findByUsername(userWithRoles.getUsername());


//        EMPLOYER DASHBOARD
        if (user.getEmployerProfile()!= null) {
            EmployerProfile employerProfile = user.getEmployerProfile();
            List<Job> jobs = employerProfile.getJobs();
            int totalApps = 0;
            for (Job job: jobs
                 ) {
                totalApps += job.getInternProfiles().size();
            }
            int numOfJobs;
            if(jobs.size() == 0){
                numOfJobs = 0;
            }else{
                numOfJobs = jobs.size();
            }

            model.addAttribute("jobs", jobs);
            model.addAttribute("numOfJobs", numOfJobs);
            model.addAttribute("totalApps", totalApps);
        }

//        INTERN DASHBOARD

        InternProfile userProfile = user.getInternProfile();
        //total number of jobs intern has applied for
        int appliedTotal = 0;
        int totalJobs = 0;
        int totalRelevantJobs = 0;
        if(!userProfile.isComplete()){
            boolean isComplete = false;
            if(jobs.findAll() == null) {
                return "There are no current job postings";
            }else{
                Iterable<Job> allJobs = jobs.findAll();
                totalJobs = ((List<Job>) allJobs).size();
            }

        }
        else{

            List<Job> appliedList = userProfile.getAppliedJobs();
                appliedTotal = appliedList.size();
                List<Job> relevantJobs = jobs.findByIndustry(userProfile.getField_1(), userProfile.getField_2(), userProfile.getField_3());
                totalRelevantJobs = relevantJobs.size();
            }


            model.addAttribute("userProfile", userProfile);
            model.addAttribute("totalRelevantJobs", totalRelevantJobs);
            model.addAttribute("appliedTotal", appliedTotal);
            model.addAttribute("totalJobs", totalJobs);


        return "dashboard";
    }
}
