package com.example.System.Controller.Student;

import com.example.System.DTO.Student.Results.StudentResultsPageDTO;
import com.example.System.Entity.User;
import com.example.System.Repository.UserRepository;
import com.example.System.Service.Student.StudentResultPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/student/results")
@RestController
@RequiredArgsConstructor
public class StudentResultController {

    private final StudentResultPageService studentResultPageService;
    private final UserRepository userRepository;

    @PreAuthorize("hasAuthority('STUDENT')")
    @GetMapping
    public ResponseEntity<StudentResultsPageDTO> getStudentResultPage(@AuthenticationPrincipal UserDetails userDetails,
                                                                      @RequestParam(defaultValue = "1-1") String semester) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow();

        Long studentId = user.getStudent().getId();
        StudentResultsPageDTO studentResultsPageDTO = studentResultPageService.getStudentResult(studentId, semester);
        return ResponseEntity.status(HttpStatus.OK).body(studentResultsPageDTO);
    }
}
