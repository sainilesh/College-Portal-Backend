package com.example.System.Controller.Student;

import com.example.System.Configuration.RateLimiter.RateLimit;
import com.example.System.DTO.Student.Dashboard.StudentDashboardPageDTO;
import com.example.System.Entity.User;
import com.example.System.Repository.UserRepository;
import com.example.System.Service.Student.StudentDashboardPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StudentDashboardController {

    private final StudentDashboardPageService studentDashboardPageService;
    private final UserRepository userRepository;

    @PreAuthorize("hasAuthority('STUDENT')")
    @GetMapping("/api/student/dashboard")
    @RateLimit(limit = 5, window = 60)
    public ResponseEntity<StudentDashboardPageDTO> getStudentDashboardPage(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow();

        Long studentId = user.getStudent().getId();
        StudentDashboardPageDTO studentDashboardPageDTO = studentDashboardPageService.getStudentDashboard(studentId);
        return ResponseEntity.status(HttpStatus.OK).body(studentDashboardPageDTO);
    }

}
