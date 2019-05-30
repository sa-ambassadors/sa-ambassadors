package io.internhub.application.controllers;

import io.internhub.application.models.User;
import io.internhub.application.repositories.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;

@RestController
@RequestMapping("/interns/api/v1")
public class ApiRegistrationController {

        private Users users;

        public ApiRegistrationController(Users users) {
            this.users = users;
        }

        @GetMapping("/user/{username}")
        public ResponseEntity<Boolean> checkIfUsernameIsPresentInDatabase(@PathVariable String username) {
            Boolean userExistsInDataBase = false;
            Iterable<User> allUsers = users.findAll();
            Iterator<User> totalUsers = allUsers.iterator();
            while (totalUsers.hasNext()) {
                if (totalUsers.next().getUsername().equals(username)) {
                    userExistsInDataBase = true;
                }
            }
            return ResponseEntity.ok(userExistsInDataBase);
        }

        @GetMapping("/email/{email}")
        public ResponseEntity<Boolean> checkIfEmailIsPresentInDatabase(@PathVariable String email) {
            Boolean emailExistsInDataBase = false;
            Iterable<User> allUsers = users.findAll();
            Iterator<User> totalUsers = allUsers.iterator();
            while (totalUsers.hasNext()) {
                if (totalUsers.next().getEmail().equals(email)) {
                    emailExistsInDataBase = true;
                }
            }
            return ResponseEntity.ok(emailExistsInDataBase);
        }
    }

