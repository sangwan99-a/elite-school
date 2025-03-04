package com.elite.school.controller;

import com.elite.school.model.Student;
import com.elite.school.model.User;
import com.elite.school.services.StudentService;
import com.elite.school.services.UserService;
import com.elite.school.util.APIConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(APIConstants.USER_CONTROLLER_BASE_URL)
public class UserController {

    @Autowired
    private UserService userService;

    // Endpoint for registering a user
    @PostMapping(APIConstants.USER_REGISTER)
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User registeredStudent = userService.registerUser(user);
        return new ResponseEntity<>(registeredStudent, HttpStatus.CREATED);
    }
}
