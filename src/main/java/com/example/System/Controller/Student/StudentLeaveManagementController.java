package com.example.System.Controller.Student;

import com.example.System.DTO.Student.LeaveManagement.Details.StudentLeaveManagementDetailsDTO;
import com.example.System.DTO.Student.LeaveManagement.LeaveRequestDTO;
import com.example.System.DTO.Student.LeaveManagement.StudentLeaveManagementPageDTO;
import com.example.System.Entity.User;
import com.example.System.Enum.LeaveReasonEnum;
import com.example.System.Enum.LeaveStatusEnum;
import com.example.System.Repository.UserRepository;
import com.example.System.Service.Student.StudentLeaveManagementPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student/leave")
public class StudentLeaveManagementController {

    private final StudentLeaveManagementPageService studentLeaveManagementPageService;
    private final UserRepository userRepository;

    @PreAuthorize("hasAuthority('STUDENT')")
    @GetMapping
    public ResponseEntity<StudentLeaveManagementPageDTO> getStudentLeaveManagementPage(@AuthenticationPrincipal UserDetails userDetails,
                                                                                       @RequestParam(required = false) LeaveStatusEnum  leaveStatus,
                                                                                       @RequestParam(required = false) LeaveReasonEnum leaveReason,
                                                                                       @RequestParam(defaultValue = "0") int page,
                                                                                       @RequestParam(defaultValue = "5") int size,
                                                                                       @RequestParam(required = false) LocalDate date) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow();

        Long studentId = user.getStudent().getId();
        StudentLeaveManagementPageDTO studentLeaveManagementPageDTO = studentLeaveManagementPageService.getLeaveManagementPage(studentId, leaveStatus, leaveReason, page, size, date);
        return ResponseEntity.status(HttpStatus.OK).body(studentLeaveManagementPageDTO);
    }

    @GetMapping("/details/{leaveId}")
    public ResponseEntity<StudentLeaveManagementDetailsDTO>  getStudentLeaveManagementDetails(@PathVariable Long leaveId) {
        StudentLeaveManagementDetailsDTO studentLeaveManagementDetailsDTO = studentLeaveManagementPageService.getLeaveDetails(leaveId);
        return ResponseEntity.status(HttpStatus.OK).body(studentLeaveManagementDetailsDTO);
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @PostMapping
    public void createNewLeave(@AuthenticationPrincipal UserDetails userDetails, @RequestBody LeaveRequestDTO leaveRequestDTO){
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow();

        Long studentId = user.getStudent().getId();
        studentLeaveManagementPageService.createNewLeave(studentId, leaveRequestDTO);
    }
}
