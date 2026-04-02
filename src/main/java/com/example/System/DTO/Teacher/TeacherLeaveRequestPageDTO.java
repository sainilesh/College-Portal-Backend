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

    private Long id;
    private String name;
    private LeaveReasonEnum leaveReason;
    private LocalDate startTime;
    private LocalDate endTime;
    private LeaveStatusEnum leaveStatus;

}
