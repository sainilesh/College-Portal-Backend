package com.example.System.DTO.Teacher;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class TeacherAttendancePageDTO {

    private Long id;
    private LocalDate date;
    private List<StudentListResponseDTO>  studentList;
    private String subjectName;
    private String semester;

}
