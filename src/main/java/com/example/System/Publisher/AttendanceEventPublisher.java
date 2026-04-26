package com.example.System.Publisher;

import com.example.System.Configuration.RabbitMQConfig;
import com.example.System.Events.AttendanceEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
public class AttendanceEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishAttendanceEvent(AttendanceEvent event) {

        try{
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.ATTENDANCE_EXCHANGE,
                    RabbitMQConfig.ATTENDANCE_ROUTING_KEY,
                    event
            );
        } catch (AmqpRejectAndDontRequeueException ex){
            System.out.println("Rejected during publishing" + ex.getMessage());
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}