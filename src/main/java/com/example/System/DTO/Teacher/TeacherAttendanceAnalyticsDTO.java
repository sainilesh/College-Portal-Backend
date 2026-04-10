package com.example.System.DTO.Teacher;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherAttendanceAnalyticsDTO implements Serializable {

    private Long id;
    private double averageAttendance;
    private Long atRisk;
    private Long totalClassesHeld;
    private List<AttendanceAnalyticsTableDTO> attendanceAnalyticsTableDTOList;
}
