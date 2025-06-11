package com.sadiqov.entity.school_portal.repository;


import com.sadiqov.entity.school_portal.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);

    boolean existsByEmail(String email);

    long countByClassroomId(Long classroomId);
}