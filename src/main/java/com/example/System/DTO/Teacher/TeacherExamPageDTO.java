package com.example.System.DTO.Teacher;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherExamPageDTO {

    private Long id;
    private List<TeacherExamTableDTO> teacherExamTableDTOList;
}
