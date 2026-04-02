package com.example.System.DTO.Teacher;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeacherExamTableDTO {

    private Long id;
    private String name;
    private String rollNo;
    private String email;
}
