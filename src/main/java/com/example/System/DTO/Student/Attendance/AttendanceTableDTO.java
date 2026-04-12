package com.example.System.DTO.Student.Attendance;

import com.example.System.Enum.AttendaceStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class AttendanceTableDTO implements Serializable {

    public AttendanceTableDTO(Long id,
                              String name,
                              LocalDate date,
                              AttendaceStatusEnum status,
                              String remarks) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.status = status;
        this.remarks = remarks;
    }

    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private AttendaceStatusEnum status;
    private String remarks;
}
