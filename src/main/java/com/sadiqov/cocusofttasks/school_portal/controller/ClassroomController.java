package com.sadiqov.cocusofttasks.school_portal.controller;
import com.sadiqov.cocusofttasks.school_portal.dto.ClassroomDTO;
import com.sadiqov.cocusofttasks.school_portal.entity.Classroom;
import com.sadiqov.cocusofttasks.school_portal.servise.ClassroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classrooms")
@RequiredArgsConstructor
public class ClassroomController {
    private final ClassroomService classroomService;

    @PostMapping("registerClassRoom")
    public ResponseEntity<Classroom> createClassroom(@RequestBody ClassroomDTO classroomDTO) {
        return ResponseEntity.ok(classroomService.createClassroom(classroomDTO));
    }

    @GetMapping("/getClassroom")
    public ResponseEntity<List<Classroom>> getAllClassrooms() {
        return ResponseEntity.ok(classroomService.getAllClassrooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Classroom> getClassroomById(@PathVariable Long id) {
        return ResponseEntity.ok(classroomService.getClassroomById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Classroom> updateClassroom(@PathVariable Long id, @RequestBody ClassroomDTO classroomDTO) {
        return ResponseEntity.ok(classroomService.updateClassroom(id, classroomDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassroom(@PathVariable Long id) {
        classroomService.deleteClassroom(id);
        return ResponseEntity.noContent().build();
    }
}