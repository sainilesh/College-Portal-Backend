package com.example.System.Service.Teacher;

import com.example.System.DTO.Teacher.TeacherLeaveRequestPageDTO;
import com.example.System.DTO.Teacher.TeacherLeaveResponseDTO;
import com.example.System.Entity.Leave;
import com.example.System.Enum.LeaveStatusEnum;
import com.example.System.Repository.LeaveRepository;
import com.example.System.Repository.TeacherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TeacherLeaveRequestService {

    private final TeacherRepository teacherRepository;
    private final LeaveRepository leaveRepository;

    public List<TeacherLeaveRequestPageDTO> getLeaveRequestPage(Long id){

        List<Leave> leaves = teacherRepository.findById(id).orElseThrow(()
        -> new IllegalArgumentException("teacher not found")).getLeaves();

        return leaves.stream()
                .map(this::mapToTeacherLeaveRequestPageDTO)
                .toList();
    }

    public void editLeaveRequests(TeacherLeaveResponseDTO teacherLeaveResponseDTO){

        Leave leave = leaveRepository.findById(teacherLeaveResponseDTO.getLeaveId()).orElseThrow(
                () -> new IllegalArgumentException("leave not found")
        );

        if(!leave.getStatus().equals(LeaveStatusEnum.PENDING)) throw new IllegalArgumentException("leave not pending");

        if(teacherLeaveResponseDTO.getLeaveStatus().equals(LeaveStatusEnum.APPROVED) ||
        teacherLeaveResponseDTO.getLeaveStatus().equals(LeaveStatusEnum.REJECTED)){
            leave.setStatus(teacherLeaveResponseDTO.getLeaveStatus());
        }

        leave.setRemarks(teacherLeaveResponseDTO.getRemarks());

        leaveRepository.save(leave);
    }

    private TeacherLeaveRequestPageDTO mapToTeacherLeaveRequestPageDTO(Leave leave) {

        return TeacherLeaveRequestPageDTO.builder()
                .id(leave.getId())
                .name(leave.getStudent().getName())
                .leaveReason(leave.getLeaveReason())
                .leaveStatus(leave.getStatus())
                .startTime(leave.getStartDate())
                .endTime(leave.getEndDate())
                .build();
    }
}
