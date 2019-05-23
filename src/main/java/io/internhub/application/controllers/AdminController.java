package io.internhub.application.controllers;

import io.internhub.application.models.InternProfile;
import io.internhub.application.models.Job;
import io.internhub.application.models.User;
import io.internhub.application.repositories.InternRepository;
import io.internhub.application.repositories.Jobs;
import io.internhub.application.repositories.Roles;
import io.internhub.application.repositories.Users;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {
    private Users users;
    private PasswordEncoder passwordEncoder;
    private Roles roles;
    private InternRepository internProfiles;
    private Jobs jobs;

    public AdminController(Users users, PasswordEncoder passwordEncoder, Roles roles, InternRepository internProfiles, Jobs jobs){
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        this.roles = roles;
        this.internProfiles = internProfiles;
        this.jobs = jobs;


    }

    @GetMapping("admin/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/register";
    }

    @PostMapping
    public String registerAdmin(@ModelAttribute User user){
        String password = user.getPassword();
        String hash = passwordEncoder.encode(password);
        user.setPassword(hash);
        user.setRole(roles.findOne(5L));
        users.save(user);

        return"redirect:/login";
    }

    @GetMapping("admin/applicant-index")
    public String applicantIndex(Model model)
    {
        Iterable<InternProfile> allProfiles = internProfiles.findAll();

        model.addAttribute("allProfiles",allProfiles);
        return"admin/applicant-index";
    }

    @GetMapping("admin/job-index")
    public String jobIndex(Model model)
    {
        Iterable<Job> allJobs = jobs.findAll();

        model.addAttribute("allJobs",allJobs);
        return"admin/job-index";
    }
}

