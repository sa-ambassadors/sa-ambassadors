package io.internhub.application.repositories;

import io.internhub.application.models.Role;
import org.springframework.data.repository.CrudRepository;

public interface Roles extends CrudRepository<Role, Long> {
}
