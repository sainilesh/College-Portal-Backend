package com.example.System.Controller.Teacher;

import com.example.System.DTO.Teacher.TeacherExamPageDTO;
import com.example.System.DTO.Teacher.TeacherExamPageRequest;
import com.example.System.Entity.User;
import com.example.System.Repository.UserRepository;
import com.example.System.Service.Teacher.TeacherExamPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher/result")
@RequiredArgsConstructor
public class TeacherExamPageController {

    private final TeacherExamPageService teacherExamPageService;
    private final UserRepository userRepository;

    @PreAuthorize("hasAuthority('TEACHER')")
    @GetMapping
    public ResponseEntity<TeacherExamPageDTO> getTeacherExamPage(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow();

        Long teacherId = user.getTeacher().getId();
        TeacherExamPageDTO teacherExamPageDTO = teacherExamPageService.getTeacherExamPage(teacherId);
        return ResponseEntity.status(HttpStatus.OK).body(teacherExamPageDTO);
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @PostMapping
    public ResponseEntity<Void> updateExamResult(@AuthenticationPrincipal UserDetails userDetails, @RequestBody List<TeacherExamPageRequest> teacherExamPageRequests) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow();

        Long teacherId = user.getTeacher().getId();
        teacherExamPageService.updateStudentResults(teacherExamPageRequests, teacherId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
