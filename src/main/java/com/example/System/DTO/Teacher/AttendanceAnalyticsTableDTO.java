package com.example.System.DTO.Teacher;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AttendanceAnalyticsTableDTO implements Serializable {
    private Long id;
    private String name;
    private String rollNo;
    private Long classesAttended;
    private Long totalClassesHeld;
    private double attendancePercentage;

}
