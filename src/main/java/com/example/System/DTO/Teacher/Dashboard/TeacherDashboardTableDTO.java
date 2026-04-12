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

    public TeacherDashboardTableDTO(Long id, String section, LocalTime startTime,
                                    LocalTime endTime, String Location) {
        this.id = id;
        this.section = section;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = Location;
    }

    private Long id;
    private String section;
    private LocalTime startTime;
    private LocalTime endTime;
    private String location;
    private TeacherAttendanceStatusEnum attendanceStatus;
}
