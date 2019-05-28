package io.internhub.application.controllers;

import io.internhub.application.models.InternProfile;
import io.internhub.application.models.Job;
import io.internhub.application.models.User;
import io.internhub.application.models.UserWithRoles;
import io.internhub.application.repositories.InternRepository;
import io.internhub.application.repositories.Jobs;
import io.internhub.application.repositories.Users;
import io.internhub.application.services.UserDetailsLoader;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    private Users users;
    private Jobs jobs;
    private InternRepository internRepository;
    private UserDetailsLoader userDetailsLoader;

    public TestController (Users users, Jobs jobs, InternRepository internRepository, UserDetailsLoader userDetailsLoader) {

        this.users = users;
        this.jobs = jobs;
        this.internRepository = internRepository;
        this.userDetailsLoader = userDetailsLoader;
    }

    //test successfully adds job to applied_jobs on user object and stores association in db
    // need to build api to do this now

    @GetMapping("/test")
    public String testerMethod () {
        UserWithRoles userWithRoles = userDetailsLoader.loadWithUserName("testy");
        User user = users.findByUsername(userWithRoles.getUsername());
        user.setEnabled(true);
        userWithRoles.setEnabled(user.isEnabled());
        users.save(user);
        return "redirect:/dashboard";
    }
}
