package com.example.System.DTO.Student.Dashboard;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StudentDashboardPageDTO {

    private Long id;
    private String name;
    private Long overallAttendance;
    private Long classesAttended;
    private Long classesMissed;
    private List<ScheduleDTO>  schedules;
    private List<SubjectOverviewDTO> subjectOverviews;
    private List<NotificationsDTO> notifications;
}
