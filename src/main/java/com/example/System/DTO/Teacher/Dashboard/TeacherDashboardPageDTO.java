package com.example.System.DTO.Teacher.Dashboard;

import com.example.System.DTO.Student.Dashboard.NotificationsDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TeacherDashboardPageDTO {

    private Long id;
    private Long lowAttendance;
    private Long pendingLeaves;
    private Long classesScheduled;
    private List<TeacherDashboardTableDTO> teacherDashboardTableDTOList;
    private List<NotificationsDTO> notificationsDTOList;
    private AttendanceAnalyticsDTO attendanceAnalyticsDTO;

}
