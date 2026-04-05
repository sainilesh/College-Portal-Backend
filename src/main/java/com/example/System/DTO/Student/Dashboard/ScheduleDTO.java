package com.example.System.DTO.Student.Dashboard;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleDTO implements Serializable {
    private Long id;
    private String className;
    private LocalTime startTime;
    private boolean happeningNow;
    private String Location;
}
