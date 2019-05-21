package io.internhub.application.repositories;
import io.internhub.application.models.Job;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface Jobs extends CrudRepository <Job, Long> {

    Job findByTitle(String title);

    List<Job> findByIndustry(String field_1, String field_2, String field_3);

}