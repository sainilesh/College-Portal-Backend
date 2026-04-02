package com.example.System.DTO.Student.Dashboard;

import com.example.System.Enum.SubjectOverviewEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubjectOverviewDTO {

    private Long id;
    private String name;
    private String teacherName;
    private String SubjectCode;
    private Long classesAttended;
    private Long totalClasses;
    private SubjectOverviewEnum subjectOverviewEnum;
}
