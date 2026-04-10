package com.example.System.Listener;

import com.example.System.Configuration.RabbitMQConfig;
import com.example.System.Email.EmailService;
import com.example.System.Events.AttendanceEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AttendanceEmailConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = "attendance.queue")
    public void handleAttendanceEvent(AttendanceEvent event) {

        emailService.sendMail(event.getStudentEmail(), "Attendance status",
                "your attendance status is "+event.getStatus()+"for the following subject "
                        + event.getSubjectName());
    }
}