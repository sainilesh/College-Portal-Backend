package com.example.System.DTO.Teacher.Dashboard;

import com.example.System.DTO.Student.Dashboard.NotificationsDTO;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherDashboardPageDTO implements Serializable {

    private Long id;
    private Long lowAttendance;
    private Long pendingLeaves;
    private Long classesScheduled;
    private List<TeacherDashboardTableDTO> teacherDashboardTableDTOList;
    private List<NotificationsDTO> notificationsDTOList;
    private AttendanceAnalyticsDTO attendanceAnalyticsDTO;

}
