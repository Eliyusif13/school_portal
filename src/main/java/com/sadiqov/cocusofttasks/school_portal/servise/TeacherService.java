package com.sadiqov.cocusofttasks.school_portal.servise;

import com.sadiqov.cocusofttasks.school_portal.dto.TeacherDTO;
import com.sadiqov.cocusofttasks.school_portal.entity.Classroom;
import com.sadiqov.cocusofttasks.school_portal.entity.Teacher;
import com.sadiqov.cocusofttasks.school_portal.exception.ResourceNotFoundException;
import com.sadiqov.cocusofttasks.school_portal.exception.ValidationException;
import com.sadiqov.cocusofttasks.school_portal.repository.ClassroomRepository;
import com.sadiqov.cocusofttasks.school_portal.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final ClassroomRepository classroomRepository;

    @Transactional
    public Teacher createTeacher(TeacherDTO teacherDTO) {
        if (teacherRepository.existsByEmail(teacherDTO.getEmail())) {
            throw new ValidationException("Email already exists");
        }

        Teacher teacher = new Teacher();
        teacher.setName(teacherDTO.getName());
        teacher.setEmail(teacherDTO.getEmail());
        teacher.setSpecialization(teacherDTO.getSpecialization());

        return teacherRepository.save(teacher);
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
    }

    @Transactional
    public Teacher updateTeacher(Long id, TeacherDTO teacherDTO) {
        Teacher existingTeacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));

        if (!existingTeacher.getEmail().equals(teacherDTO.getEmail()) &&
                teacherRepository.existsByEmail(teacherDTO.getEmail())) {
            throw new ValidationException("Email already exists");
        }

        existingTeacher.setName(teacherDTO.getName());
        existingTeacher.setEmail(teacherDTO.getEmail());
        existingTeacher.setSpecialization(teacherDTO.getSpecialization());

        return teacherRepository.save(existingTeacher);
    }

    @Transactional
    public void deleteTeacher(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));

        List<Classroom> teacherClassrooms = classroomRepository.findByTeacherId(id);

        if (!teacherClassrooms.isEmpty()) {
            String classroomNames = teacherClassrooms.stream()
                    .map(Classroom::getName)
                    .collect(Collectors.joining(", "));

            throw new ValidationException(
                    "Cannot delete teacher with assigned classrooms. " +
                            "Assigned classrooms: " + classroomNames + ". " +
                            "Please reassign or delete these classrooms first."
            );
        }

        teacherRepository.delete(teacher);
    }
}