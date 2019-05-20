package io.internhub.application.controllers;

import io.internhub.application.models.InternProfile;
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
import org.springframework.web.bind.annotation.PostMapping;




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
        System.out.println(password);
        String hash = passwordEncoder.encode(password);
        user.setPassword(hash);
        user.setRole(roles.findOne(3L));
        userDao.save(user);
        return "redirect:/login";
    }

    @GetMapping("interns/profile")
    public String getInternProfileForm(Model model) {
        model.addAttribute("internProfile", new InternProfile());
        return "interns/profile";
    }

    @PostMapping("interns/profile")
    public String postInternProfileForm(@ModelAttribute InternProfile internProfile){
        UserWithRoles userWithRoles = (UserWithRoles) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.findByUsername(userWithRoles.getUsername());
        user.setInternProfile(internProfile);
        internProfile.setUser(user);
        userDao.save(user);
        return "redirect:/profile";
    }

}
