package io.internhub.application.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FaqController {

    @GetMapping(value = "/faq")
    public String faq(){
        return "faq/faq";
    }
}


