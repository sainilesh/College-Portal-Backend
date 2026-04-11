package com.example.System.Service.Teacher;

import com.example.System.DTO.Teacher.TeacherLeaveRequestPageDTO;
import com.example.System.DTO.Teacher.TeacherLeaveResponseDTO;
import com.example.System.Entity.Leave;
import com.example.System.Enum.LeaveStatusEnum;
import com.example.System.Repository.LeaveRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TeacherLeaveRequestService {

    private final LeaveRepository leaveRepository;

    public List<TeacherLeaveRequestPageDTO> getLeaveRequestPage(Long id){
        return leaveRepository.findLeaveDTOs(id);
    }

    @Transactional
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

    }
}
