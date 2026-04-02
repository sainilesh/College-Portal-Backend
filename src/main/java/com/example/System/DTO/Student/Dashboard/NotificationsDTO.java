package com.example.System.DTO.Student.Dashboard;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationsDTO {

    private Long id;
    private String title;
    private String message;
    private LocalDateTime createdDate;

}
