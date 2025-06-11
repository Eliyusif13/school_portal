package com.sadiqov.entity.school_portal.repository;


import com.sadiqov.entity.school_portal.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByEmail(String email);

    boolean existsByEmail(String email);
}
