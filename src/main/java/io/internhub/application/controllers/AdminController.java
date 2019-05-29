package io.internhub.application.controllers;

import io.internhub.application.models.*;
import io.internhub.application.repositories.*;
import io.internhub.application.services.UserDetailsLoader;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {
    private Users users;
    private PasswordEncoder passwordEncoder;
    private Roles roles;
    private InternRepository internProfiles;
    private Jobs jobs;
    private UserDetailsLoader userDetailsLoader;
    private EmployerProfiles employerProfiles;

    public AdminController(Users users, PasswordEncoder passwordEncoder, Roles roles, InternRepository internProfiles, Jobs jobs, UserDetailsLoader userDetailsLoader, EmployerProfiles employerProfiles){
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        this.roles = roles;
        this.internProfiles = internProfiles;
        this.jobs = jobs;
        this.userDetailsLoader = userDetailsLoader;
        this.employerProfiles = employerProfiles;
    }

    @GetMapping("admin/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/register";
    }

    @PostMapping("admin/register")
    public String registerAdmin(@ModelAttribute User user){
        String password = user.getPassword();
        String hash = passwordEncoder.encode(password);
        user.setPassword(hash);
        user.setRole(roles.findOne(5L));
        users.save(user);

        return"redirect:/login";
    }

    @GetMapping("admin/applicant-index")
    public String applicantIndex (Model model) {
        Iterable<InternProfile> allInternProfiles = internProfiles.findAll();
        model.addAttribute("allInternProfiles", allInternProfiles);
        return"admin/applicant-index";
    }

    @GetMapping("admin/applicant/{profileId}")
    public String getIndividualApplicantPage (Model model, @PathVariable String profileId) {
        InternProfile internProfile = internProfiles.findOne(Long.parseLong(profileId));
        model.addAttribute("internProfile", internProfile);
        return "admin/approve-applicant";
    }

    @PostMapping("admin/applicant/{profileId}")
    public String postIndividualApplicantPage(Model model, @PathVariable String profileId) {
        InternProfile internProfile = internProfiles.findOne(Long.parseLong(profileId));
        UserWithRoles userWithRoles = userDetailsLoader.loadWithUserName(internProfile.getUser().getUsername());
        userWithRoles.setEnabled(true);
        User user = internProfile.getUser();
        user.setEnabled(true);
        users.save(user);
        return "redirect:/admin/applicant-index";
    }

    @GetMapping("admin/employer-index")
    public String employerIndex (Model model) {
        Iterable<EmployerProfile> allEmployerProfiles = employerProfiles.findAll();
        model.addAttribute("allEmployerProfiles", allEmployerProfiles);
        return"admin/employer-index";
    }

    @GetMapping("admin/employer/{employerId}")
    public String getIndividualEmployerPage (Model model, @PathVariable String employerId) {
        EmployerProfile employerProfile = employerProfiles.findOne(Long.parseLong(employerId));
        model.addAttribute("employerProfile", employerProfile);
        return "admin/approve-employer";
    }

    @PostMapping("admin/employer/{employerId}")
    public String postIndividualEmployerPage(Model model, @PathVariable String employerId) {
        EmployerProfile employerProfile = employerProfiles.findOne(Long.parseLong(employerId));
        UserWithRoles userWithRoles = userDetailsLoader.loadWithUserName(employerProfile.getUser().getUsername());
        userWithRoles.setEnabled(true);
        User user = employerProfile.getUser();
        user.setEnabled(true);
        users.save(user);
        return "redirect:/admin/employer-index";
    }
}

