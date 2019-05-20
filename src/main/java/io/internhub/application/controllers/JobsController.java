package io.internhub.application.controllers;
import io.internhub.application.models.EmployerProfile;
import io.internhub.application.models.Job;
import io.internhub.application.models.User;
import io.internhub.application.models.UserWithRoles;
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

    @GetMapping("jobs/{jobId}")
    public String getJobPage (Model model, @PathVariable String jobId) {
        Long jobLongId = Long.parseLong(jobId);
        Job job = jobs.findOne(jobLongId);
        model.addAttribute("job", job);
        return "jobs/id";
    }

}