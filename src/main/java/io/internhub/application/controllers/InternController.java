package io.internhub.application.controllers;

import io.internhub.application.models.InternProfile;
import io.internhub.application.models.Job;
import io.internhub.application.models.User;
import io.internhub.application.models.UserWithRoles;
import io.internhub.application.repositories.InternRepository;
import io.internhub.application.repositories.Roles;
import io.internhub.application.repositories.Users;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class InternController {

    private final InternRepository internDao;
    private final Users userDao;
    private PasswordEncoder passwordEncoder;
    private Roles roles;

    public InternController(InternRepository internDao, Users userDao, PasswordEncoder passwordEncoder, Roles roles){
        this.internDao = internDao;
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.roles = roles;

    }

    @GetMapping("interns/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "interns/register";
    }

    @PostMapping("interns/register")
    public String internRegisterPost(@ModelAttribute User user){
        String password = user.getPassword();
        String hash = passwordEncoder.encode(password);
        user.setPassword(hash);
        user.setRole(roles.findOne(3L));
        userDao.save(user);
        User newUser = userDao.findByUsername(user.getUsername());
        InternProfile newProfile = new InternProfile();
        newProfile.setComplete(false);
        newProfile.setApproved(false);
        newProfile.setHired(false);
        newProfile.setUser(newUser);
        internDao.save(newProfile);

        return "redirect:/login";
    }

    @GetMapping("interns/profile-register")
    public String getInternProfileForm(Model model) {

        // security
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof UserWithRoles)) {
            return "redirect: /login";
        }

        model.addAttribute("internProfile", new InternProfile());
        return "interns/register-profile";
    }

    @PostMapping("interns/profile-register")
    public String postInternProfileForm(@ModelAttribute InternProfile internProfile){
        UserWithRoles userWithRoles = (UserWithRoles) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.findByUsername(userWithRoles.getUsername());
        user.setInternProfile(internProfile);
        userDao.save(user);
        return "redirect:/dashboard";
    }

    @GetMapping("/interns/applied-index")
    public String getInternAppliedJobs(Model model) {
        UserWithRoles userWithRoles = (UserWithRoles) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.findByUsername(userWithRoles.getUsername());
        InternProfile internProfile = user.getInternProfile();
        List<Job> appliedJobs = internProfile.getAppliedJobs();
        model.addAttribute("jobs", appliedJobs);
        return "interns/applied-index";
    }

    @GetMapping("/interns/profile/{internId}")
    public String getInternProfile(Model model, @PathVariable String internId) {
        InternProfile internProfile = internDao.findOne(Long.parseLong(internId));
        model.addAttribute("internProfile", internProfile);
        return "interns/profile";
    }

}
