package com.example.System.Service.Student;

import com.example.System.DTO.Student.Results.ResultTableDTO;
import com.example.System.DTO.Student.Results.StudentResultsPageDTO;
import com.example.System.Entity.Grade;
import com.example.System.Repository.GradeRepository;
import com.example.System.Repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentResultPageService {

    private final GradeRepository gradeRepository;
    private final SubjectRepository subjectRepository;


    @Cacheable(
            value = "studentResultsPage",
            key = "#studentId + ':' + #semester"
    )
    @Transactional(readOnly = true)
    public StudentResultsPageDTO getStudentResult(Long studentId, String semester) {

        long totalObtainedCredits = 0;
        long backlogs = 0;

        List<Grade> allGrades = gradeRepository.findByStudentIdWithSubject(studentId);

        List<Grade> semesterGrades = allGrades.stream()
                .filter(grade -> grade.getSubject().getSemester().getName().equals(semester))
                .toList();

        List<ResultTableDTO> resultTableDTOS = new ArrayList<>();

        for (Grade grade : semesterGrades) {
            ResultTableDTO dto = maptoResultTableDTO(grade);

            if (!dto.isPassed()) backlogs++;

            totalObtainedCredits += dto.getCredits();

            resultTableDTOS.add(dto);
        }

        double semesterGrade = gradeCalculation(semesterGrades);

        Long totalCredits = subjectRepository.findTotalCredits(semester).orElse(0L);

        double overallGrade = gradeCalculation(allGrades);

        Map<String,Double> semsterGrades = allGrades.stream()
                .collect(Collectors.groupingBy(
                        grade -> grade.getSubject().getSemester().getName(),
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                this::gradeCalculation
                        )
                ));

        double highestSGPA = semsterGrades.values().stream()
                .mapToDouble(Double::doubleValue)
                .max()
                .orElse(0.0);

        return StudentResultsPageDTO.builder()
                .id(studentId)
                .resultTables(resultTableDTOS)
                .semesterGrade(semesterGrade)
                .overallGrade(overallGrade)
                .totalCredits(totalCredits)
                .creditsObtained(totalObtainedCredits)
                .backlogs(backlogs)
                .highestGrade(highestSGPA)
                .semesterGrades(semsterGrades)
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
                .mapToDouble(g -> g.getGrade() * g.getSubject().getCredits())
                .sum();

        double totalCredits = grades.stream()
                .mapToLong(g -> g.getSubject().getCredits())
                .sum();

        return roundTo2Decimal(totalCredits == 0 ? 0 : gradeVal / totalCredits);
    }

    public double roundTo2Decimal(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
