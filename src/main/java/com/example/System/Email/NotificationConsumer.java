package com.example.System.Email;
//
//import com.example.System.Configuration.AppConfig;
//import com.example.System.DTO.NotificationMessageDTO;
//import lombok.RequiredArgsConstructor;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//
//import java.time.Duration;
//
//@Service
//@RequiredArgsConstructor
public class NotificationConsumer {
//
//    private final EmailService emailService;
//    private final RedisTemplate<String, String> redisTemplate;
//
//    @RabbitListener(queues = AppConfig.RabbitMQConfig.QUEUE)
//    public void sendAttendanceMail(NotificationMessageDTO notificationMessageDTO) {
//
//        String redisKey = "Attendance_warning" + notificationMessageDTO.getStudentId();
//
//        if(Boolean.TRUE.equals(redisTemplate.hasKey(redisKey))) {
//            return;
//        }
//
//        emailService.sendMail(notificationMessageDTO.getEmail(), "Attendance Warning",
//                "Your Attendance is below 75%");
//
//        redisTemplate.opsForValue().set(redisKey, "sent", Duration.ofDays(7));
//    }
//
}
