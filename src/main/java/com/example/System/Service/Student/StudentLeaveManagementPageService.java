package com.example.System.Service.Student;

import com.example.System.DTO.Student.LeaveManagement.Details.StudentLeaveManagementDetailsDTO;
import com.example.System.DTO.Student.LeaveManagement.LeaveRequestDTO;
import com.example.System.DTO.Student.LeaveManagement.StudentLeaveManagementPageDTO;
import com.example.System.DTO.Student.LeaveManagement.LeaveTableDTO;
import com.example.System.Entity.Leave;
import com.example.System.Entity.Student;
import com.example.System.Entity.Teacher;
import com.example.System.Enum.LeaveReasonEnum;
import com.example.System.Enum.LeaveStatusEnum;
import com.example.System.Repository.LeaveRepository;
import com.example.System.Repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentLeaveManagementPageService {


    private final LeaveRepository leaveRepository;
    private final StudentRepository studentRepository;

    @Transactional(readOnly = true)
    public StudentLeaveManagementPageDTO getLeaveManagementPage(Long studentId, LeaveStatusEnum leaveStatus, LeaveReasonEnum leaveReason, int page, int size, LocalDate date) {

        Long leavesTaken = leaveRepository.getTotalLeaves(studentId);

        Long pendingRequests = leaveRepository.countByStudentIdAndStatus(studentId, LeaveStatusEnum.PENDING)
                .orElseThrow(() -> new IllegalArgumentException("No leaves for the student id"));

        Long remainingLeaves = Math.max(0, 20 - leavesTaken);

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "endDate"));

        Page<Leave> leaves = leaveRepository.findStudentsLeavesByFilters(studentId, leaveStatus, leaveReason, date, pageable);

        List<LeaveTableDTO> leaveTableDTOS = leaves.stream()
                .map(this::mapToLeaveTableDTO)
                .toList();

        return StudentLeaveManagementPageDTO.builder()
                .id(studentId)
                .leavesTaken(leavesTaken)
                .pendingRequests(pendingRequests)
                .remainingLeaves(remainingLeaves)
                .leaveTables(leaveTableDTOS)
                .build();
    }

    @Transactional(readOnly = true)
    private LeaveTableDTO mapToLeaveTableDTO(Leave leave) {

        return LeaveTableDTO.builder()
                .id(leave.getId())
                .reason(leave.getReason())
                .remarks(leave.getRemarks())
                .startDate(leave.getStartDate())
                .endDate(leave.getEndDate())
                .reasonEnum(leave.getLeaveReason())
                .status(leave.getStatus())
                .build();
    }

    @Transactional
    public StudentLeaveManagementDetailsDTO getLeaveDetails(Long leaveId) {
        Leave leave = leaveRepository.findByIdWithTeacher(leaveId).orElseThrow(() ->
                new IllegalArgumentException("leave not found"));

        Long days = ChronoUnit.DAYS.between(leave.getStartDate(), leave.getEndDate()) + 1;

        return StudentLeaveManagementDetailsDTO.builder()
                .id(leaveId)
                .leaveReason(leave.getLeaveReason())
                .leaveStatus(leave.getStatus())
                .reason(leave.getReason())
                .startDate(leave.getStartDate())
                .endDate(leave.getEndDate())
                .duration(days)
                .teacherName(leave.getTeacher().getName())
                .commentedAt(leave.getCreatedAt())
                .remarks(leave.getRemarks())
                .build();
    }

    public void createNewLeave(Long id, LeaveRequestDTO leaveRequestDTO) {

        Student student =  studentRepository.findByIdAndFetch(id).orElseThrow(() ->
                new IllegalArgumentException("student not found"));

        Teacher teacher = student.getSection().getTeacher();

        if (leaveRequestDTO.getEndDate().isBefore(leaveRequestDTO.getStartDate())) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        Leave leave = Leave.builder()
                .startDate(leaveRequestDTO.getStartDate())
                .endDate(leaveRequestDTO.getEndDate())
                .leaveReason(leaveRequestDTO.getLeaveReason())
                .reason(leaveRequestDTO.getReason())
                .student(student)
                .teacher(teacher)
                .status(LeaveStatusEnum.PENDING)
                .build();

        leaveRepository.save(leave);
    }
}
