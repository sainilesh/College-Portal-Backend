package com.example.System.Events;

import lombok.*;

@Setter
@Getter
public class AttendanceEvent {

    private Long studentId;
    private String studentEmail;
    private String subjectName;
    private String status;

}