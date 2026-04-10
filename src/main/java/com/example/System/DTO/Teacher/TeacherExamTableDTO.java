package com.example.System.DTO.Teacher;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherExamTableDTO {

    private Long id;
    private String name;
    private String rollNo;
    private String email;
}
