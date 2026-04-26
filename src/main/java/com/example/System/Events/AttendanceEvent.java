package com.example.System.Events;

import com.example.System.Entity.Teacher;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceEvent {

    private Long studentId;
    private String studentEmail;
    private String subjectName;
    private String status;
    private Long teacherId;

}