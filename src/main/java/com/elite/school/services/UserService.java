package com.elite.school.services;

import com.elite.school.model.Student;
import com.elite.school.model.User;
import com.elite.school.repository.StudentRepository;
import com.elite.school.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User student) {
        return userRepository.save(student);
    }
}
