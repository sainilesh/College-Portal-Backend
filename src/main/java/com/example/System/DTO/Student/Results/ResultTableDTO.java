package com.example.System.DTO.Student.Results;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultTableDTO implements Serializable {

    private Long id;
    private String subjectCode;
    private String subjectName;
    private Long credits;
    private double score;
    private String grade;
    private boolean passed;
}
