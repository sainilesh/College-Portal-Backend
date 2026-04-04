package com.example.System.Email;
//
//import com.example.System.Configuration.AppConfig;
//import com.example.System.DTO.NotificationMessageDTO;
//import com.example.System.Entity.Student;
//import com.example.System.Repository.StudentSubjectRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
public class SchedulerService {
//
//    private final StudentSubjectRepository studentSubjectRepository;
//    private final RabbitTemplate rabbitTemplate;
//
//    @Scheduled(cron = "0 0 9 * * MON")
//    public void notifyStudentsWithLowAttendance(){
//
//        List<Student> students = studentSubjectRepository.findStudentsBelow75();
//
//        for(Student s: students){
//
//        NotificationMessageDTO notificationMessageDTO = NotificationMessageDTO.builder()
//                    .email(s.getUser().getEmail())
//                    .studentId(s.getId())
//                    .type("Low Attendance Warning")
//                    .build();
//
//        rabbitTemplate.convertAndSend(AppConfig.RabbitMQConfig.QUEUE, notificationMessageDTO);
//        }
//
//    }
//
//
}
