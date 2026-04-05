package com.example.System.DTO.Student.Dashboard;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDashboardPageDTO implements Serializable {

    private Long id;
    private String name;
    private Long overallAttendance;
    private Long classesAttended;
    private Long classesMissed;
    private List<ScheduleDTO>  schedules;
    private List<SubjectOverviewDTO> subjectOverviews;
    private List<NotificationsDTO> notifications;
}
