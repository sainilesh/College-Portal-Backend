package com.example.System.Controller.Teacher;

import com.example.System.DTO.Teacher.Dashboard.TeacherDashboardPageDTO;
import com.example.System.Entity.User;
import com.example.System.Repository.UserRepository;
import com.example.System.Service.Teacher.TeacherDashboardPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teacher/dashboard")
@RequiredArgsConstructor
public class TeacherDashboardPageController {

    private final TeacherDashboardPageService teacherDashboardPageService;
    private final UserRepository userRepository;

    @PreAuthorize("hasAuthority('TEACHER')")
    @GetMapping
    public ResponseEntity<TeacherDashboardPageDTO> getTeacherDashboard(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow();

        Long teacherId = user.getStudent().getId();
        TeacherDashboardPageDTO teacherDashboardPageDTO = teacherDashboardPageService.getTeacherDashboardPageDTO(teacherId);
        return ResponseEntity.status(HttpStatus.OK).body(teacherDashboardPageDTO);
    }
}
