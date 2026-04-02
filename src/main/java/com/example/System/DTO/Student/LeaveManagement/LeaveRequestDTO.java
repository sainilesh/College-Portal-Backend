package com.example.System.DTO.Student.LeaveManagement;

import com.example.System.Enum.LeaveReasonEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class LeaveRequestDTO {

    private LocalDate startDate;
    private LocalDate endDate;
    private LeaveReasonEnum leaveReason;
    private String reason;

}
