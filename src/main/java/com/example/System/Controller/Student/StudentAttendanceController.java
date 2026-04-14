package com.example.System.Controller.Student;

import com.example.System.Configuration.RateLimiter.RateLimit;
import com.example.System.DTO.Student.Attendance.StudentAttendancePageDTO;
import com.example.System.Entity.User;
import com.example.System.Repository.UserRepository;
import com.example.System.Service.Student.StudentAttendancePageService;
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
public class StudentAttendanceController {

    private final StudentAttendancePageService studentAttendancePageService;
    private final UserRepository userRepository;

    @PreAuthorize("hasAuthority('STUDENT')")
    @GetMapping("/api/student/attendance")
    @RateLimit(limit = 5, window = 120)
    public ResponseEntity<StudentAttendancePageDTO> getAttendancePage(@AuthenticationPrincipal UserDetails userDetails,
                                                                      @RequestParam(required = false) String semester,
                                                                      @RequestParam(required = false) String subject,
                                                                      @RequestParam(required = false)LocalDate date,
                                                                      @RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size){
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow();

        Long studentId = user.getStudent().getId();

        StudentAttendancePageDTO studentAttendancePageDTO = studentAttendancePageService.getAttendancePage(studentId, semester, subject, date, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(studentAttendancePageDTO);
    }

}
