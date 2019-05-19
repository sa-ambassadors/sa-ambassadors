package io.internhub.application.controllers;

import io.internhub.application.models.EmployerProfile;
import io.internhub.application.models.Job;
import io.internhub.application.models.User;
import io.internhub.application.models.UserWithRoles;
import io.internhub.application.repositories.Users;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    private Users users;

    public DashboardController (Users users) {
        this.users = users;
    }

    @GetMapping("dashboard")
    public String getDashboardPage(Model model) {
        UserWithRoles userWithRoles = (UserWithRoles) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = users.findByUsername(userWithRoles.getUsername());
        if (user.getEmployerProfile()!= null) {
            EmployerProfile employerProfile = user.getEmployerProfile();
            List<Job> jobs = employerProfile.getJobs();
            model.addAttribute("jobs", jobs);
        }
        return "dashboard";
    }
}
