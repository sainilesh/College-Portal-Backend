package com.example.System.DTO.Student.Results;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentResultsPageDTO implements Serializable {

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
