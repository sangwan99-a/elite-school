package com.elite.school.controller;

import com.elite.school.model.Student;
import com.elite.school.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
public class UserController {

    @Autowired
    private StudentService studentService;

    // Endpoint for registering a student
    @PostMapping("/register")
    public ResponseEntity<Student> registerStudent(@RequestBody Student student) {
        Student registeredStudent = studentService.registerStudent(student);
        return new ResponseEntity<>(registeredStudent, HttpStatus.CREATED);
    }
}
