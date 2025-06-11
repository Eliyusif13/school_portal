package com.sadiqov.cocusofttasks.school_portal.controller;

import com.sadiqov.cocusofttasks.school_portal.dto.TeacherDTO;

import com.sadiqov.cocusofttasks.school_portal.entity.*;

import com.sadiqov.cocusofttasks.school_portal.exception.*;

import com.sadiqov.cocusofttasks.school_portal.repository.*;

import com.sadiqov.cocusofttasks.school_portal.servise.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;
    private final ClassroomRepository classroomRepository;
    private final TeacherRepository teacherRepository;


    @PostMapping
    public ResponseEntity<Teacher> createTeacher(@RequestBody TeacherDTO teacherDTO) {
        return ResponseEntity.ok(teacherService.createTeacher(teacherDTO));
    }

    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable Long id) {
        return ResponseEntity.ok(teacherService.getTeacherById(id));
    }

    @PutMapping("/{teacherId}/reassign-classrooms/{newTeacherId}")
    public ResponseEntity<String> reassignClassrooms(
            @PathVariable Long teacherId,
            @PathVariable Long newTeacherId) {

        Teacher oldTeacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Old teacher not found"));

        Teacher newTeacher = teacherRepository.findById(newTeacherId)
                .orElseThrow(() -> new ResourceNotFoundException("New teacher not found"));

        List<Classroom> classrooms = classroomRepository.findByTeacherId(teacherId);

        if (classrooms.isEmpty()) {
            return ResponseEntity.ok("No classrooms to reassign");
        }

        classrooms.forEach(classroom -> classroom.setTeacher(newTeacher));
        classroomRepository.saveAll(classrooms);

        return ResponseEntity.ok(
                "Successfully reassigned " + classrooms.size() +
                        " classroom(s) from teacher " + oldTeacher.getName() +
                        " to teacher " + newTeacher.getName()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable Long id) {
        try {
            teacherService.deleteTeacher(id);
            return ResponseEntity.noContent().build();
        } catch (ValidationException ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("timestamp", LocalDateTime.now().toString());
            errorResponse.put("message", ex.getMessage());
            errorResponse.put("status", HttpStatus.BAD_REQUEST.toString());

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorResponse);
        }
    }
}