package com.sadiqov.entity.school_portal.servise;

import com.sadiqov.entity.school_portal.dto.StudentDTO;
import com.sadiqov.entity.school_portal.entity.Classroom;
import com.sadiqov.entity.school_portal.entity.Student;
import com.sadiqov.entity.school_portal.exception.ResourceNotFoundException;
import com.sadiqov.entity.school_portal.repository.ClassroomRepository;
import com.sadiqov.entity.school_portal.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final ClassroomRepository classroomRepository;

    @Transactional
    public Student createStudent(StudentDTO studentDTO) {
        if (studentRepository.existsByEmail(studentDTO.getEmail())) {
            throw new ValidationException("Email already exists");
        }

        Classroom classroom = classroomRepository.findById(studentDTO.getClassroomId())
                .orElseThrow(() -> new ResourceNotFoundException("Classroom not found"));

        Student student = new Student();
        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        student.setAge(studentDTO.getAge());
        student.setClassroom(classroom);

        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
    }

    @Transactional
    public Student updateStudent(Long id, StudentDTO studentDTO) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        if (!existingStudent.getEmail().equals(studentDTO.getEmail()) &&
                studentRepository.existsByEmail(studentDTO.getEmail())) {
            throw new ValidationException("Email already exists");
        }

        Classroom classroom = classroomRepository.findById(studentDTO.getClassroomId())
                .orElseThrow(() -> new ResourceNotFoundException("Classroom not found"));

        existingStudent.setName(studentDTO.getName());
        existingStudent.setEmail(studentDTO.getEmail());
        existingStudent.setAge(studentDTO.getAge());
        existingStudent.setClassroom(classroom);

        return studentRepository.save(existingStudent);
    }

    @Transactional
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        studentRepository.delete(student);
    }
}