package com.example.System.Service.Student;

import com.example.System.DTO.Student.Results.ResultTableDTO;
import com.example.System.DTO.Student.Results.StudentResultsPageDTO;
import com.example.System.Entity.Grade;
import com.example.System.Repository.GradeRepository;
import com.example.System.Repository.SubjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentResultPageService {

    private final GradeRepository gradeRepository;
    private final SubjectRepository subjectRepository;

    public StudentResultsPageDTO getStudentResult(Long studentId, String semester) {

        long totalObtainedCredits = 0;
        long backlogs = 0;

        List<Grade> grades = gradeRepository.findByGradesBySemester(studentId, semester);

        List<ResultTableDTO> resultTableDTOS = new ArrayList<>();

        for (Grade grade : grades) {
            ResultTableDTO dto = maptoResultTableDTO(grade);

            if (!dto.isPassed()) {
                backlogs++;
            }

            totalObtainedCredits += dto.getCredits();

            resultTableDTOS.add(dto);
        }

        double semesterGrade = gradeCalculation(grades);

        List<Grade> grades1 = gradeRepository.findByStudentId(studentId);

        Long totalCredits = subjectRepository.findTotalCredits(semester).orElse(0L);

        double overallGrade = gradeCalculation(grades1);

        double highestSGPA = gradeRepository.findHighestSGPA(studentId).orElse(0.0);

        return StudentResultsPageDTO.builder()
                .id(studentId)
                .resultTables(resultTableDTOS)
                .semesterGrade(semesterGrade)
                .overallGrade(overallGrade)
                .totalCredits(totalCredits)
                .creditsObtained(totalObtainedCredits)
                .backlogs(backlogs)
                .highestGrade(highestSGPA)
                .build();
    }

    private ResultTableDTO maptoResultTableDTO(Grade grade) {

        boolean passed;
        double score = grade.getGrade();
        String gradeString;

        if (score >= 9.0){
            gradeString = "O";
            passed = true;
        }
        else if (score >= 8.0){
            gradeString = "A";
            passed = true;
        }
        else if (score >= 7.0){
            gradeString = "B";
            passed = true;
        }
        else if (score >= 6.0){
            gradeString = "C";
            passed = true;
        }
        else if (score >= 5.0){
            gradeString = "D";
            passed = true;
        }
        else{
            gradeString = "F";
            passed = false;
        }

        long obtainedCredits = passed ? grade.getSubject().getCredits() : 0;

        return ResultTableDTO.builder()
                .id(grade.getId())
                .subjectName(grade.getSubject().getName())
                .subjectCode(grade.getSubject().getSubjectCode())
                .credits(obtainedCredits)
                .grade(gradeString)
                .score(score)
                .passed(passed)
                .build();
    }

    private double gradeCalculation(List<Grade> grades) {
        double gradeVal = grades.stream()
                .mapToDouble(g -> g.getGrade() * (g.getGrade() >= 5 ? g.getSubject().getCredits() : 0))
                .sum();

        double totalCredits = grades.stream()
                .mapToLong(g -> g.getGrade() >= 5 ? g.getSubject().getCredits() : 0)
                .sum();

        return totalCredits == 0 ? 0 : gradeVal / totalCredits;
    }
}
