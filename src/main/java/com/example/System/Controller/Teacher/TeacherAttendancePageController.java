package com.example.System.Controller.Teacher;

import com.example.System.DTO.Teacher.StudentListRequestDTO;
import com.example.System.DTO.Teacher.TeacherAttendancePageDTO;
import com.example.System.Entity.User;
import com.example.System.Repository.UserRepository;
import com.example.System.Service.Teacher.TeacherAttendancePageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/teacher/attendance")
@RequiredArgsConstructor
public class TeacherAttendancePageController {

    private final TeacherAttendancePageService teacherAttendancePageService;
    private final UserRepository userRepository;

    @PreAuthorize("hasAuthority('TEACHER')")
    @GetMapping
    public ResponseEntity<TeacherAttendancePageDTO> getTeacherAttendancePage(@AuthenticationPrincipal UserDetails userDetails,
                                                                             @RequestParam(defaultValue = "CSE-2A") String section,
                                                                             @RequestParam(required = false) LocalDate date) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow();

        Long teacherId = user.getStudent().getId();

        if(date == null) {date = LocalDate.now();}
        TeacherAttendancePageDTO teacherAttendancePageDTO = teacherAttendancePageService.getAttendancePage(teacherId, section, date);
        return ResponseEntity.status(HttpStatus.OK).body(teacherAttendancePageDTO);
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @PostMapping
    public ResponseEntity<Void> submitAttendance(@AuthenticationPrincipal UserDetails userDetails, @RequestBody List<StudentListRequestDTO> studentListRequestDTOS) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow();

        Long teacherId = user.getStudent().getId();
        teacherAttendancePageService.submitAttendance(teacherId, studentListRequestDTOS);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
