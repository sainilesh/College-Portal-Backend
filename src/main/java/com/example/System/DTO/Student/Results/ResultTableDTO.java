package com.example.System.DTO.Student.Results;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultTableDTO {

    private Long id;
    private String subjectCode;
    private String subjectName;
    private Long credits;
    private double score;
    private String grade;
    private boolean passed;
}
