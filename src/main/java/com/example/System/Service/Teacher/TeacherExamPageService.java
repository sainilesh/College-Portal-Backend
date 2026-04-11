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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherExamPageService {

    private final TeacherRepository teacherRepository;
    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;

    @Cacheable(value = "teacherExamPage", key = "#id")
    public TeacherExamPageDTO getTeacherExamPage(Long id){

        Teacher teacher = teacherRepository.findTeacherWithStudents(id)
                .orElseThrow(() -> new IllegalArgumentException("teacher not found!"));

        List<TeacherExamTableDTO> table = studentRepository.getExamTableDTOBySectionId(teacher.getSection().getId());

        return TeacherExamPageDTO.builder()
                .id(id)
                .teacherExamTableDTOList(table)
                .build();
    }

    @Transactional
    public void updateStudentResults(List<TeacherExamPageRequest> requests, Long id){

        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("teacher not found!"));

        List<String> rollNos = requests.stream()
                .map(TeacherExamPageRequest::getRollNo)
                .toList();

        List<Student> students = studentRepository.findAllByRollNoIn(rollNos);

        Map<String, Student> studentMap = students.stream()
                .collect(Collectors.toMap(Student::getRollNo, s -> s));

        List<Grade> grades = new ArrayList<>();

        for (TeacherExamPageRequest req : requests) {

            Student student = studentMap.get(req.getRollNo());

            if (student == null) {
                throw new IllegalArgumentException("Student not found: " + req.getRollNo());
            }

            double grade = (req.getEndTerm() + req.getMidTerm()) / 10.0;

            grades.add(
                    Grade.builder()
                            .student(student)
                            .grade(grade)
                            .status(GradeStatusEnum.GRADED)
                            .subject(teacher.getSubject())
                            .build()
            );
        }

        gradeRepository.saveAll(grades);
    }
}
