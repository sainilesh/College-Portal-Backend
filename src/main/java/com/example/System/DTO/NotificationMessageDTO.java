package com.example.System.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationMessageDTO {

    private String email;
    private Long studentId;
    private String type;
    private Long attendance;

}
