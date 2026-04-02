package com.example.System.DTO.Teacher;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TeacherAttendanceAnalyticsDTO {

    private Long id;
    private double averageAttendance;
    private Long atRisk;
    private Long totalClassesHeld;
    private List<AttendanceAnalyticsTableDTO> attendanceAnalyticsTableDTOList;
}
