package com.example.System.Service.Teacher;

import com.example.System.DTO.Student.Dashboard.NotificationsDTO;
import com.example.System.DTO.Teacher.Dashboard.AttendanceAnalyticsDTO;
import com.example.System.DTO.Teacher.Dashboard.TeacherDashboardPageDTO;
import com.example.System.DTO.Teacher.Dashboard.TeacherDashboardTableDTO;
import com.example.System.Entity.Notification;
import com.example.System.Entity.Teacher;
import com.example.System.Entity.TimeTable;
import com.example.System.Repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TeacherDashboardPageService {

    private final TeacherRepository teacherRepository;
    private final StudentSubjectRepository studentSubjectRepository;
    private final LeaveRepository leaveRepository;
    private final TimeTableRepository timeTableRepository;
    private final NotificationRepository notificationRepository;

    @Cacheable(value = "teacherDashboard")
    public TeacherDashboardPageDTO getTeacherDashboardPageDTO(Long id) {

        Teacher teacher = teacherRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Teacher not found!"));

        Long lowAttendance = studentSubjectRepository.findStudentsWithLowAttendance(id).orElse(0L);

        Long pendingLeaves = leaveRepository.findTotalPendingLeaves(id).orElse(0L);

        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();

        Long classesScheduled = timeTableRepository.countByDayOfWeekAndTeacherId(dayOfWeek, id);

        List<NotificationsDTO> notificationsDTOS = notificationRepository.findAll().stream()
                .map(this :: mapToNotificationDTO)
                .toList();

        List<TeacherDashboardTableDTO> teacherDashboardTableDTOS = timeTableRepository.findByDayOfWeekAndTeacherId(dayOfWeek, id).stream()
                        .map(this::mapToDashboardTableDTO)
                        .toList();

        Double averageAttendance = studentSubjectRepository.findAverageAttendance(id).orElse(0D);

        String section = studentSubjectRepository.findLeastAttendanceOfSection(id)
                .stream()
                .findFirst()
                .orElse(null);

        AttendanceAnalyticsDTO attendanceAnalyticsDTOS = AttendanceAnalyticsDTO.builder()
                        .id(id)
                        .averageAttendance(averageAttendance)
                        .mostAbsentSection(section)
                        .build();

        return TeacherDashboardPageDTO.builder()
                .id(teacher.getId())
                .notificationsDTOList(notificationsDTOS)
                .pendingLeaves(pendingLeaves)
                .teacherDashboardTableDTOList(teacherDashboardTableDTOS)
                .attendanceAnalyticsDTO(attendanceAnalyticsDTOS)
                .classesScheduled(classesScheduled)
                .lowAttendance(lowAttendance)
                .build();
    }

    private TeacherDashboardTableDTO mapToDashboardTableDTO(TimeTable timeTable) {

        return TeacherDashboardTableDTO.builder()
                .id(timeTable.getId())
                .section(timeTable.getSection().getName())
                .startTime(timeTable.getStartTime())
                .endTime(timeTable.getEndTime())
                .location(timeTable.getLocation())
                .attendanceStatus(null)
                .build();
    }

    private NotificationsDTO mapToNotificationDTO(Notification notification) {
        return NotificationsDTO.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .createdDate(notification.getCreatedAt())
                .build();
    }
}
