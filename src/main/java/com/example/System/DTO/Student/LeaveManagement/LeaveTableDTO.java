package com.example.System.DTO.Student.LeaveManagement;

import com.example.System.Enum.LeaveReasonEnum;
import com.example.System.Enum.LeaveStatusEnum;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class LeaveTableDTO {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private String remarks;
    private LeaveStatusEnum status;
    private LeaveReasonEnum reasonEnum;
}
