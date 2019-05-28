package io.internhub.application.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AboutController {

        @GetMapping(value = "/about")
        public String about(){
            return "pages/about";
        }
    }
