package io.internhub.application.services;

import io.internhub.application.repositories.Jobs;

public class JobService {

    private Jobs jobs;

    public JobService(Jobs jobs) {
        this.jobs = jobs;
    }
}
