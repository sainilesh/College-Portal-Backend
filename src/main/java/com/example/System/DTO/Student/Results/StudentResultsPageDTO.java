package com.example.System.DTO.Student.Results;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StudentResultsPageDTO {

    private Long id;
    private double semesterGrade;
    private double overallGrade;
    private Long creditsObtained;
    private Long totalCredits;
    private List<ResultTableDTO> resultTables;
    private List<Long> semesterGrades;
    private Long backlogs;
    private double highestGrade;

}
