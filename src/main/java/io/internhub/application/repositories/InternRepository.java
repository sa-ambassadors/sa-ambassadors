package io.internhub.application.repositories;

import io.internhub.application.models.InternProfile;
import org.springframework.data.repository.CrudRepository;

public interface InternRepository extends CrudRepository< InternProfile, Long> {

}