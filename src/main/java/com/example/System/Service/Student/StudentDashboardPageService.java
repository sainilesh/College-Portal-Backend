package com.example.System.Service.Student;

import com.example.System.DTO.Student.Dashboard.NotificationsDTO;
import com.example.System.DTO.Student.Dashboard.ScheduleDTO;
import com.example.System.DTO.Student.Dashboard.StudentDashboardPageDTO;
import com.example.System.DTO.Student.Dashboard.SubjectOverviewDTO;
import com.example.System.Entity.Notification;
import com.example.System.Entity.Student;
import com.example.System.Entity.StudentSubject;
import com.example.System.Entity.TimeTable;
import com.example.System.Enum.AttendaceStatusEnum;
import com.example.System.Enum.SubjectOverviewEnum;
import com.example.System.Repository.NotificationRepository;
import com.example.System.Repository.StudentRepository;
import com.example.System.Repository.StudentSubjectRepository;
import com.example.System.Repository.TimeTableRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentDashboardPageService {

    private final StudentRepository studentRepository;
    private final StudentSubjectRepository studentSubjectRepository;
    private final NotificationRepository notificationRepository;
    private final TimeTableRepository timeTableRepository;

    public StudentDashboardPageDTO getStudentDashboard(Long id){
        Student student = studentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Student not found"));

        Long classesAttended = studentSubjectRepository.getTotalClassesAttended(id, AttendaceStatusEnum.PRESENT).orElseThrow(() ->
                new IllegalArgumentException("Student not found"));

        Long classesConducted = studentSubjectRepository.getTotalClassesConducted(id).orElseThrow(() ->
                new IllegalArgumentException("Student not found"));

        List< NotificationsDTO> notificationsDTOS = notificationRepository.findAll().stream()
                .map(this :: mapToNotificationDTO)
                .toList();

        List<StudentSubject> studentSubjects = studentSubjectRepository.findAllByStudentId(id);

        List<SubjectOverviewDTO> subjectOverviewDTOS = studentSubjects.stream()
                .map(studentSubject -> mapToSubjectOverview(studentSubject,student))
                .toList();

        Long overallAttendance = Math.round(((double) classesAttended / classesConducted) * 100);

        List<TimeTable> timeTables = timeTableRepository.findAllBySection(student.getSection()).orElseThrow(() ->
                new IllegalArgumentException("Student or Section not found"));

        List<ScheduleDTO> scheduleDTOS = timeTables.stream()
                .map(this::mapToScheduleDTO)
                .toList();

        return StudentDashboardPageDTO.builder()
                .id(student.getId())
                .name(student.getName())
                .classesAttended(classesAttended)
                .classesMissed(classesConducted - classesAttended)
                .notifications(notificationsDTOS)
                .overallAttendance(overallAttendance)
                .schedules(scheduleDTOS)
                .subjectOverviews(subjectOverviewDTOS)
                .build();
    }

    private ScheduleDTO mapToScheduleDTO(TimeTable timeTable) {

        boolean HappeningNow = false;
        LocalTime now = LocalTime.now();
        if(now.isAfter(timeTable.getStartTime()) && now.isBefore(timeTable.getEndTime())){
            HappeningNow = true;
        }
        return ScheduleDTO.builder()
                .id(timeTable.getId())
                .className(timeTable.getSubject().getName())
                .startTime(timeTable.getStartTime())
                .Location(timeTable.getLocation())
                .HappeningNow(HappeningNow)
                .build();
    }

    private SubjectOverviewDTO mapToSubjectOverview(StudentSubject studentSubject, Student student) {

        Long classesAttended = studentSubjectRepository.getTotalClassesAttendedForSubject(student.getId(),
                studentSubject.getSubject().getId(), AttendaceStatusEnum.PRESENT).orElseThrow(() ->
                new IllegalArgumentException("Student or Subject not found"));

        Long totalClasses = studentSubjectRepository.getTotalClassesForSubject(student.getId(),
                studentSubject.getSubject().getId()).orElseThrow(() ->
                new IllegalArgumentException("Student or Subject not found"));

        long attendancePercentage = Math.round(((double)classesAttended/totalClasses) * 100);

        SubjectOverviewEnum subjectOverviewEnum;

        if (attendancePercentage >= 80) {
            subjectOverviewEnum = SubjectOverviewEnum.SAFE;
        }
        else if (attendancePercentage >= 75) {
            subjectOverviewEnum = SubjectOverviewEnum.OK;
        }
        else {
            subjectOverviewEnum = SubjectOverviewEnum.WARNING;
        }


        return SubjectOverviewDTO.builder()
                .id(student.getId())
                .name(student.getName())
                .teacherName(studentSubject.getTeacher().getName())
                .classesAttended(classesAttended)
                .totalClasses(totalClasses)
                .SubjectCode(studentSubject.getSubject().getSubjectCode())
                .subjectOverviewEnum(subjectOverviewEnum)
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
