package io.internhub.application.controllers;

import io.internhub.application.models.InternProfile;
import io.internhub.application.models.Job;
import io.internhub.application.models.User;
import io.internhub.application.models.UserWithRoles;
import io.internhub.application.repositories.InternRepository;
import io.internhub.application.repositories.Jobs;
import io.internhub.application.repositories.Users;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    private Users users;
    private Jobs jobs;
    private InternRepository internRepository;

    public TestController (Users users, Jobs jobs, InternRepository internRepository) {
        this.users = users;
        this.jobs = jobs;
        this.internRepository = internRepository;
    }

    //test successfully adds job to applied_jobs on user object and stores association in db
    // need to build api to do this now

    @GetMapping("/test")
    public String testerMethod () {
        UserWithRoles userWithRoles = (UserWithRoles) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = users.findByUsername(userWithRoles.getUsername());
        InternProfile internProfile = user.getInternProfile();
        Job job = jobs.findOne(1L);
        internProfile.addJobToAppliedList(job);
        internRepository.save(internProfile);
        return "redirect:/dashboard";
    }
}
