package com.example.System.Service.Teacher;

import com.example.System.DTO.Teacher.TeacherExamPageDTO;
import com.example.System.DTO.Teacher.TeacherExamPageRequest;
import com.example.System.DTO.Teacher.TeacherExamTableDTO;
import com.example.System.Entity.Grade;
import com.example.System.Entity.Student;
import com.example.System.Entity.Teacher;
import com.example.System.Enum.GradeStatusEnum;
import com.example.System.Repository.GradeRepository;
import com.example.System.Repository.StudentRepository;
import com.example.System.Repository.TeacherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TeacherExamPageService {

    private final TeacherRepository teacherRepository;
    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;

    @Cacheable(value = "teacherExamPage")
    public TeacherExamPageDTO getTeacherExamPage(Long id){

        Teacher teacher = teacherRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("teacher not found!"));

        List<TeacherExamTableDTO> teacherExamTableDTOS = teacher.getSection().getStudents().stream()
                .map(this::mapToTeacherExamTableDTO)
                .toList();

        return TeacherExamPageDTO.builder()
                .id(id)
                .teacherExamTableDTOList(teacherExamTableDTOS)
                .build();
    }

    public void updateStudentResults(List<TeacherExamPageRequest> teacherExamPageRequests, Long id){

        Teacher teacher = teacherRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("teacher not found!"));

        for (TeacherExamPageRequest teacherExamPageRequest : teacherExamPageRequests) {
            Student student = studentRepository.findByRollNo(teacherExamPageRequest.getRollNo()).orElseThrow(
                    () -> new IllegalArgumentException("student not found!"));

            double grade = (teacherExamPageRequest.getEndTerm()+teacherExamPageRequest.getMidTerm())/10.0;
            Grade grade1 = Grade.builder()
                                .student(student)
                                .grade(grade)
                                .status(GradeStatusEnum.GRADED)
                                .subject(teacher.getSubject())
                                .build();

            gradeRepository.save(grade1);

        }

    }

    private TeacherExamTableDTO mapToTeacherExamTableDTO(Student student) {
        return TeacherExamTableDTO.builder()
                .id(student.getId())
                .name(student.getName())
                .rollNo(student.getRollNo())
                .email(null)
                .build();
    }
}
