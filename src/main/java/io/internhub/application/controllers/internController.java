package io.internhub.application.controllers;

import io.internhub.application.models.User;
import io.internhub.application.repositories.InternRepository;
import io.internhub.application.repositories.Users;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class internController {

    private final InternRepository internDao;
    private final Users userDao;
    private PasswordEncoder passwordEncoder;

    public internController(InternRepository internDao, Users userDao, PasswordEncoder passwordEncoder){
        this.internDao = internDao;
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;

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
        userDao.save(user);
        return "redirect:/login";
    }

}
