package com.example.System.DTO.Teacher.Dashboard;

import com.example.System.Enum.TeacherAttendanceStatusEnum;
import lombok.*;

import java.io.Serializable;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherDashboardTableDTO implements Serializable {

    private Long id;
    private String section;
    private LocalTime startTime;
    private LocalTime endTime;
    private String location;
    private TeacherAttendanceStatusEnum attendanceStatus;
}
