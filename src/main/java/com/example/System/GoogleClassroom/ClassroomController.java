package com.example.System.GoogleClassroom;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/classroom")
public class ClassroomController {

    private final GoogleClassroomRestService googleClassroomRestService;

    @PostMapping("/create")
    public String createClassroom(@RequestParam String subject,
                                  @RequestParam String section) {

        Long userId = 2L;

        return googleClassroomRestService.createClassroom(userId, subject, section);
    }
}
