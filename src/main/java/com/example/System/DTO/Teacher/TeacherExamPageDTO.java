package com.example.System.DTO.Teacher;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TeacherExamPageDTO {

    private Long id;
    private List<TeacherExamTableDTO> teacherExamTableDTOList;
}
