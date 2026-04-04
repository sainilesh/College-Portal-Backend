package com.example.System.DTO.Student.Attendance;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.io.Serializable;

@Data
@Builder
public class StudentAttendancePageDTO implements Serializable {

    private Long id;
    private Long totalClasses;
    private Long classesAttended;
    private Long overallAttendance;
    private Page<AttendanceTableDTO> attendanceTables;
}
