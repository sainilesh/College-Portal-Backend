package com.example.System.DTO.Teacher.Dashboard;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceAnalyticsDTO implements Serializable {

    private Long id;
    private Double averageAttendance;
    private String mostAbsentSection;

}
