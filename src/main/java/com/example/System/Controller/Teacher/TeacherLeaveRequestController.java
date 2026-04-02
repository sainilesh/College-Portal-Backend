package com.example.System.Controller.Teacher;

import com.example.System.DTO.Teacher.TeacherLeaveRequestPageDTO;
import com.example.System.DTO.Teacher.TeacherLeaveResponseDTO;
import com.example.System.Entity.User;
import com.example.System.Repository.UserRepository;
import com.example.System.Service.Teacher.TeacherLeaveRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher/leave")
@RequiredArgsConstructor
public class TeacherLeaveRequestController {

    private final TeacherLeaveRequestService teacherLeaveRequestService;
    private final UserRepository userRepository;

    @PreAuthorize("hasAuthority('TEACHER')")
    @GetMapping
    public ResponseEntity<List<TeacherLeaveRequestPageDTO>> teacherLeaveRequestPage(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow();

        Long teacherId = user.getStudent().getId();
        List<TeacherLeaveRequestPageDTO> teacherLeaveRequestPageDTO = teacherLeaveRequestService.getLeaveRequestPage(teacherId);
        return ResponseEntity.status(HttpStatus.OK).body(teacherLeaveRequestPageDTO);
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @PostMapping
    public ResponseEntity<Void> editLeaveRequest(@RequestBody TeacherLeaveResponseDTO teacherLeaveResponseDTO) {
        teacherLeaveRequestService.editLeaveRequests(teacherLeaveResponseDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
