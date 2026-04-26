package com.example.System.Listener;

import com.example.System.Email.EmailService;
import com.example.System.Entity.Notification;
import com.example.System.Entity.Teacher;
import com.example.System.Events.AttendanceEvent;
import com.example.System.Repository.NotificationRepository;
import com.example.System.Repository.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AttendanceEmailConsumer {

    private final EmailService emailService;
    private final NotificationRepository notificationRepository;
    private final CacheManager cacheManager;
    private final TeacherRepository teacherRepository;

    @RabbitListener(queues = "attendance.queue",
    containerFactory = "rabbitListenerContainerFactory")
    public void handleAttendanceEvent(AttendanceEvent event) {

        log.info("Received AttendanceEvent {}", event);

        emailService.sendMail(event.getStudentEmail(), "Attendance status",
                "your attendance status is "+event.getStatus()+"for the following subject "
                        + event.getSubjectName());

        Teacher teacher = teacherRepository.findById(event.getTeacherId()).orElseThrow(
                () -> new EntityNotFoundException("teacher not found")
        );

        log.info("teacher is" + teacher.getName());

        Notification notification = Notification.builder()
                .title("Attendance Updated")
                .message("Attendance has been updated check mail or dashboard")
                .teacher(teacher)
                .build();

        notificationRepository.save(notification);

        Long teacherId = teacher.getId();
        Long studentId = event.getStudentId();

        evictCache("teacherDashboard", teacherId);
        evictCache("studentDashboard", studentId);
        evictCache("teacherAttendanceAnalytics",  teacherId);

    }

    private void evictCache(String cacheName, Long key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.evict(key);
            log.info("Evicted cache: {} for key {}", cacheName, key);
        }
    }
}