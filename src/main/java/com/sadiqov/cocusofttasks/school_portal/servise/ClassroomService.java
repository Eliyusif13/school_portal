package com.sadiqov.cocusofttasks.school_portal.servise;


import com.sadiqov.cocusofttasks.school_portal.dto.ClassroomDTO;
import com.sadiqov.cocusofttasks.school_portal.entity.*;
import com.sadiqov.cocusofttasks.school_portal.exception.*;
import com.sadiqov.cocusofttasks.school_portal.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassroomService {
    private final ClassroomRepository classroomRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    @Transactional
    public Classroom createClassroom(ClassroomDTO classroomDTO) {
        Teacher teacher = teacherRepository.findById(classroomDTO.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));

        Classroom classroom = new Classroom();
        classroom.setName(classroomDTO.getName());
        classroom.setTeacher(teacher);

        return classroomRepository.save(classroom);
    }

    public List<Classroom> getAllClassrooms() {
        return classroomRepository.findAll();
    }

    public Classroom getClassroomById(Long id) {
        return classroomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom not found"));
    }

    @Transactional
    public Classroom updateClassroom(Long id, ClassroomDTO classroomDTO) {
        Classroom existingClassroom = classroomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom not found"));

        Teacher teacher = teacherRepository.findById(classroomDTO.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));

        existingClassroom.setName(classroomDTO.getName());
        existingClassroom.setTeacher(teacher);

        return classroomRepository.save(existingClassroom);
    }

    @Transactional
    public void deleteClassroom(Long id) {
        Classroom classroom = classroomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom not found"));

        if (studentRepository.countByClassroomId(id) > 0) {
            throw new ValidationException("Cannot delete classroom with students");
        }

        classroomRepository.delete(classroom);
    }

}