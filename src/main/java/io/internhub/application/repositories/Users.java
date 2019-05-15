package io.internhub.application.repositories;

import io.internhub.application.models.User;
import org.springframework.data.repository.CrudRepository;

public interface Users extends CrudRepository <User, Long> {

    User findByUsername (String username);
}
