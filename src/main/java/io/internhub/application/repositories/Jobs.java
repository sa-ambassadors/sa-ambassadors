package io.internhub.application.repositories;
import io.internhub.application.models.Job;
import org.springframework.data.repository.CrudRepository;


public interface Jobs extends CrudRepository <Job, Long> {

    Job findByTitle(String title);

}