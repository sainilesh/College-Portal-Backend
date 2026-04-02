package com.example.System.DTO.Teacher;

import com.example.System.Enum.AttendaceStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentListRequestDTO {

    private Long id;
    private String rollNo;
    private AttendaceStatusEnum status;
    private String remarks;
}
