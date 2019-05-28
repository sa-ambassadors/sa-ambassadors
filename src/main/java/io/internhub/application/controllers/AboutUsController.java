package io.internhub.application.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AboutUsController {

        @GetMapping(value = "/about-us")
        public String aboutUs(){
            return "pages/about-us";
        }
    }

