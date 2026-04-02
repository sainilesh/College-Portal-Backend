package com.example.System.DTO.Teacher;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LeaveConfirmRequestDTO {

    private Long id;
    private boolean approved;
    private String remarks;
}
