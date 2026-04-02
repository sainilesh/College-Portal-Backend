package com.example.System.DTO.Teacher;

import com.example.System.Enum.LeaveStatusEnum;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeacherLeaveResponseDTO {

    private Long id;
    private Long leaveId;
    private LeaveStatusEnum leaveStatus;
    private String remarks;

}
