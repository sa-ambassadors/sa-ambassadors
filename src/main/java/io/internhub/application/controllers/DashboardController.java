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

    public DashboardController (Users users) {
        this.users = users;
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
        if(user.getInternProfile()!= null){
            //Get intern profile
            InternProfile internProfile =user.getInternProfile();
            //Get total number of all jobs
            Iterable<Job> totalJobsList = jobs.findAll();
            int totalJobs = 0;
            if (totalJobsList instanceof Collection) {
                totalJobs = ((Collection<?>) totalJobsList).size();
            }
            //Get total number of jobs intern has applied for
            int appliedTotal = 0;
            List<Job> appliedList = internProfile.getAppliedJobs();
            if(appliedList.size() != 0){
                appliedTotal = appliedList.size();
            }

            model.addAttribute("appliedTotal", appliedTotal);
            model.addAttribute("totalJobs", totalJobs);
            model.addAttribute("firstName", internProfile.getFirst_name());
        }
        return "dashboard";
    }
}
