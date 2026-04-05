package com.example.System.DTO.Student.Dashboard;

import com.example.System.Enum.SubjectOverviewEnum;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectOverviewDTO implements Serializable {

    private Long id;
    private String name;
    private String teacherName;
    private String SubjectCode;
    private Long classesAttended;
    private Long totalClasses;
    private SubjectOverviewEnum subjectOverviewEnum;
}
