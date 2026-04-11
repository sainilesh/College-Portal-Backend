package com.example.System.DTO.Teacher;

import com.example.System.Enum.LeaveReasonEnum;
import com.example.System.Enum.LeaveStatusEnum;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class TeacherLeaveRequestPageDTO {

    public TeacherLeaveRequestPageDTO(
            Long id,
            String name,
            LeaveReasonEnum leaveReason,
            LocalDate startTime,           // 5th
            LocalDate endTime,
            LeaveStatusEnum leaveStatus
    ) {
        this.id = id;
        this.name = name;
        this.leaveReason = leaveReason;
        this.startTime = startTime;
        this.endTime = endTime;
        this.leaveStatus = leaveStatus;
    }

    private Long id;
    private String name;
    private LeaveReasonEnum leaveReason;
    private LocalDate startTime;
    private LocalDate endTime;
    private LeaveStatusEnum leaveStatus;

}
