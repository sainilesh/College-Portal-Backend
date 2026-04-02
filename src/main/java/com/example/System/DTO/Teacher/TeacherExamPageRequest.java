package com.example.System.DTO.Teacher;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeacherExamPageRequest {

    private Long id;
    private String rollNo;
    private Long midTerm;
    private Long endTerm;

}
