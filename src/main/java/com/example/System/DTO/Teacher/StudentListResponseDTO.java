package com.example.System.DTO.Teacher;

import com.example.System.Enum.AttendaceStatusEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentListResponseDTO {

    private Long id;
    private String name;
    private String rollNo;
    private AttendaceStatusEnum  attendanceStatus;
    private String remarks;
}
