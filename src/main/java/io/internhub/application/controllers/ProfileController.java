package io.internhub.application.controllers;

import io.internhub.application.models.EmployerProfile;
import io.internhub.application.models.InternProfile;
import io.internhub.application.models.User;
import io.internhub.application.models.UserWithRoles;
import io.internhub.application.repositories.Users;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    private Users users;

    public ProfileController (Users users) {
        this.users = users;
    }

    @GetMapping("/profile")
    public String getProfilePage(Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof UserWithRoles)) {
            return "redirect: /login";
        }

        UserWithRoles userWithRoles = (UserWithRoles) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = users.findByUsername(userWithRoles.getUsername());
        if (user.getEmployerProfile()!= null) {
            EmployerProfile employerProfile = user.getEmployerProfile();
            model.addAttribute("employerProfile", employerProfile);
        }
        if (user.getInternProfile()!= null) {
            InternProfile internProfile = user.getInternProfile();
            model.addAttribute("internProfile", internProfile);
        }
        return "profile";
    }
}
