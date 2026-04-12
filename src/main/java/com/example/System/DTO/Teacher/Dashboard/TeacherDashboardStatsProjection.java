package com.example.System.DTO.Teacher.Dashboard;

public interface TeacherDashboardStatsProjection {

    Long getLowAttendance();
    Long getPendingLeaves();
    Double getAverageAttendance();
    String getLeastSection();
}
