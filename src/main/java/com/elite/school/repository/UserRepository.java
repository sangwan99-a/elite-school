package com.elite.school.repository;

import com.elite.school.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserRepository, Long> {
}
