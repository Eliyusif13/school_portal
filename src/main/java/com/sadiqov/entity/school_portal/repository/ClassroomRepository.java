package com.sadiqov.entity.school_portal.repository;


import com.sadiqov.entity.school_portal.entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    List<Classroom> findByTeacherId(Long teacherId);

    boolean existsByTeacherId(Long teacherId);

}
