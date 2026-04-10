package com.example.System.Controller.Teacher;

import com.example.System.DTO.Teacher.TeacherAttendanceAnalyticsDTO;
import com.example.System.Entity.User;
import com.example.System.Repository.UserRepository;
import com.example.System.Service.Teacher.TeacherAttendanceAnalyticsPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teacher/analytics")
@RequiredArgsConstructor
public class TeacherAttendanceAnalyticsPageController {

    private final TeacherAttendanceAnalyticsPageService teacherAttendanceAnalyticsPageService;
    private final UserRepository userRepository;

    @PreAuthorize("hasAuthority('TEACHER')")
    @GetMapping
    public ResponseEntity<TeacherAttendanceAnalyticsDTO> getAttendanceAnalytics(@AuthenticationPrincipal UserDetails userDetails,
                                                                                @RequestParam(defaultValue = "CSE-2A") String section) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow();

        Long teacherId = user.getTeacher().getId();
        TeacherAttendanceAnalyticsDTO teacherAttendanceAnalyticsDTO = teacherAttendanceAnalyticsPageService.getTeacherAttendanceAnalyticsDTO(teacherId,section);
        return ResponseEntity.status(HttpStatus.OK).body(teacherAttendanceAnalyticsDTO);
    }
}
