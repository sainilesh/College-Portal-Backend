package com.example.System.Publisher;

import com.example.System.Configuration.RabbitMQConfig;
import com.example.System.Events.AttendanceEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttendanceEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishAttendanceEvent(AttendanceEvent event) {

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.ATTENDANCE_EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                event
        );

    }
}