package io.internhub.application.controllers;

import io.internhub.application.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FaqController {

    @GetMapping("/faq")
    public void getFaqPage(User user) {

    }
}


