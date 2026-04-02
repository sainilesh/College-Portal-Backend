package com.example.System.DTO.Teacher.Dashboard;

import com.example.System.Enum.TeacherAttendanceStatusEnum;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Data
@Builder
public class TeacherDashboardTableDTO {

    private Long id;
    private String section;
    private LocalTime startTime;
    private LocalTime endTime;
    private String location;
    private TeacherAttendanceStatusEnum attendanceStatus;
}
