package com.example.System.DTO.Teacher;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherExamTableDTO {

    public TeacherExamTableDTO(Long id, String name, String rollNo) {
        this.id = id;
        this.name = name;
        this.rollNo = rollNo;
    }

    private Long id;
    private String name;
    private String rollNo;
    private String email;
}
