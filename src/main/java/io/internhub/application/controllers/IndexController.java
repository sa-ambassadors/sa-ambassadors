package io.internhub.application.controllers;

import io.internhub.application.models.User;
import io.internhub.application.models.UserWithRoles;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {


        @GetMapping("/")
        public String index(){
            return "index";
    }



}
