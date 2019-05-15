package io.internhub.application.repositories;

import io.internhub.application.models.EmployerProfile;
import org.springframework.data.repository.CrudRepository;

public interface EmployerProfiles extends CrudRepository <EmployerProfile, Long> {
}
