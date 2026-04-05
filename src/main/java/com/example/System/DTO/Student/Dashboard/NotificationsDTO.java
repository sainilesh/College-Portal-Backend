package com.example.System.DTO.Student.Dashboard;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationsDTO implements Serializable {

    private Long id;
    private String title;
    private String message;
    private LocalDateTime createdDate;

}
