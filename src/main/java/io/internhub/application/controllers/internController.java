package io.internhub.application.controllers;

import io.internhub.application.models.User;
import io.internhub.application.repositories.InternRepository;
import io.internhub.application.repositories.Users;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class internController {

    private final InternRepository internDao;
    private final Users userDao;

    public internController(InternRepository internDao, Users userDao){
        this.internDao = internDao;
        this.userDao = userDao;
    }

    @GetMapping("intern/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "interns/sign-up";
    }
}
