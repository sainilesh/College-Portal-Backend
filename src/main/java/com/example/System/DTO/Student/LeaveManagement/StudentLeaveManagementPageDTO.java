package com.example.System.DTO.Student.LeaveManagement;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StudentLeaveManagementPageDTO {

    private Long id;
    private Long leavesTaken;
    private Long pendingRequests;
    private Long remainingLeaves;
    private List<LeaveTableDTO> leaveTables;
}
