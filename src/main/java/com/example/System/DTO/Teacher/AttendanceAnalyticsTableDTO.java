package com.example.System.DTO.Teacher;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttendanceAnalyticsTableDTO {
    private Long id;
    private String name;
    private String rollNo;
    private Long classesAttended;
    private Long totalClassesHeld;
    private double attendancePercentage;

}
