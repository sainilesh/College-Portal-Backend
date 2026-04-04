package com.example.System.DTO.Student.Attendance;

import com.example.System.Enum.AttendaceStatusEnum;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class AttendanceTableDTO implements Serializable {

    private Long id;
    private LocalDate date;
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private AttendaceStatusEnum status;
    private String remarks;
}
