package com.example.System.DTO.Teacher.Dashboard;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttendanceAnalyticsDTO {

    private Long id;
    private Double averageAttendance;
    private String mostAbsentSection;

}
