package com.example.System.DTO.Student.LeaveManagement;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class StudentLeaveManagementPageDTO implements Serializable {

    private Long id;
    private Long leavesTaken;
    private Long pendingRequests;
    private Long remainingLeaves;
    private List<LeaveTableDTO> leaveTables;
}
