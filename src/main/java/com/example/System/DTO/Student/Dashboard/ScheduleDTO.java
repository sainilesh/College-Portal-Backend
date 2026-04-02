package com.example.System.DTO.Student.Dashboard;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
public class ScheduleDTO {
    private Long id;
    private String className;
    private LocalTime startTime;
    private boolean HappeningNow;
    private String Location;
}
