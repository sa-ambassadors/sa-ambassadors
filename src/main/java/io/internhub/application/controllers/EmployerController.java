package io.internhub.application.controllers;

import io.internhub.application.models.EmployerProfile;
import io.internhub.application.models.Role;
import io.internhub.application.models.User;
import io.internhub.application.models.UserTransporter;
import io.internhub.application.repositories.EmployerProfiles;
import io.internhub.application.repositories.Roles;
import io.internhub.application.repositories.Users;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EmployerController {

    private Users users;
    private PasswordEncoder passwordEncoder;
    private EmployerProfiles employerProfiles;
    private Roles roles;
    UserTransporter userTransporter = new UserTransporter();

    public EmployerController(Users users, PasswordEncoder passwordEncoder, EmployerProfiles employerProfiles, Roles roles) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        this.employerProfiles = employerProfiles;
        this.roles = roles;
    }

    @GetMapping("employers/register")
    public String employerRegister(Model model){
        model.addAttribute("user", new User());
        return "employers/register";
    }

    @PostMapping("employers/register")
    public String employerRegisterPost(@ModelAttribute User user){
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        userTransporter.setUser(user);
        return "redirect:/employers/profile";
    }

    @GetMapping("employers/profile")
    public String employerProfile(Model model) {
        model.addAttribute("employer", new EmployerProfile());
        return "employers/register-profile";
    }

    @PostMapping("employers/profile")
    public String employerProfile(@ModelAttribute EmployerProfile employerProfile) {
        User user = userTransporter.getUser();
        user.setEmployerProfile(employerProfile);
        user.setRole(roles.findOne(4L));
        employerProfile.setUser(user);
        users.save(user);
        return "redirect:/login";
    }
}
