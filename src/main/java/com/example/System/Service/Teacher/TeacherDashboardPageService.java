package com.example.System.Service.Teacher;

import com.example.System.DTO.Student.Dashboard.NotificationsDTO;
import com.example.System.DTO.Teacher.Dashboard.AttendanceAnalyticsDTO;
import com.example.System.DTO.Teacher.Dashboard.TeacherDashboardPageDTO;
import com.example.System.DTO.Teacher.Dashboard.TeacherDashboardStatsProjection;
import com.example.System.DTO.Teacher.Dashboard.TeacherDashboardTableDTO;
import com.example.System.Entity.Teacher;
import com.example.System.Repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TeacherDashboardPageService {

    private final TeacherRepository teacherRepository;
    private final DashboardRepository dashboardRepository;
    private final TimeTableRepository timeTableRepository;
    private final NotificationRepository notificationRepository;

    @Cacheable(value = "teacherDashboard")
    public TeacherDashboardPageDTO getTeacherDashboardPageDTO(Long id) {

        Teacher teacher = teacherRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Teacher not found!"));

        TeacherDashboardStatsProjection stats =
                dashboardRepository.getDashboardStats(id);

        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();

        List<NotificationsDTO> notificationsDTOS = notificationRepository.getNotificationDTOs(PageRequest.of(0, 10));

        List<TeacherDashboardTableDTO> teacherDashboardTableDTOS = timeTableRepository.
                getTeacherDashboardTables(dayOfWeek, id);

        AttendanceAnalyticsDTO attendanceAnalyticsDTOS = AttendanceAnalyticsDTO.builder()
                        .id(id)
                        .averageAttendance(stats.getAverageAttendance())
                        .mostAbsentSection(stats.getLeastSection())
                        .build();

        return TeacherDashboardPageDTO.builder()
                .id(teacher.getId())
                .notificationsDTOList(notificationsDTOS)
                .pendingLeaves(stats.getPendingLeaves())
                .teacherDashboardTableDTOList(teacherDashboardTableDTOS)
                .attendanceAnalyticsDTO(attendanceAnalyticsDTOS)
                .classesScheduled((long) teacherDashboardTableDTOS.size())
                .lowAttendance(stats.getLowAttendance())
                .build();
    }


}
