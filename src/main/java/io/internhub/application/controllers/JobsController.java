package io.internhub.application.controllers;
import io.internhub.application.repositories.Jobs;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class JobsController {
    private Jobs jobs;

    public JobsController(Jobs jobs) {
        this.jobs = jobs;
    }






}