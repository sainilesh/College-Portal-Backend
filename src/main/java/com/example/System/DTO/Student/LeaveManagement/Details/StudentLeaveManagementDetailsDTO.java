package com.example.System.DTO.Student.LeaveManagement.Details;

import com.example.System.Enum.LeaveReasonEnum;
import com.example.System.Enum.LeaveStatusEnum;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class StudentLeaveManagementDetailsDTO {

    private Long id;
    private LeaveReasonEnum leaveReason;
    private LeaveStatusEnum leaveStatus;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long duration;
    private String reason;
    private String teacherName;
    private LocalDateTime commentedAt;
    private String remarks;

}
