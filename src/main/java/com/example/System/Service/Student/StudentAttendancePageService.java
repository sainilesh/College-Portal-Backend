package com.example.System.Service.Student;

import com.example.System.DTO.Student.Attendance.AttendanceTableDTO;
import com.example.System.DTO.Student.Attendance.StudentAttendancePageDTO;
import com.example.System.DTO.Student.Dashboard.AttendanceStatsProjection;
import com.example.System.Enum.AttendaceStatusEnum;
import com.example.System.Repository.StudentSubjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentAttendancePageService {

    private final StudentSubjectRepository studentSubjectRepository;


    public StudentAttendancePageDTO getAttendancePage(Long id, String semester, String subject, LocalDate date,int page,int size) {

        AttendanceStatsProjection stats = studentSubjectRepository.getAttendanceStats(id, AttendaceStatusEnum.PRESENT);

        Long totalClassesAttended = stats.getAttendedClasses();
        Long totalClasses = stats.getTotalClasses();

        Long overallAttendance = totalClasses == 0 ? 0 :
                Math.round((totalClassesAttended * 100.0) / totalClasses);

        Pageable pageable = PageRequest.of(page, size, Sort.by("conductedAt").descending());

        Page<AttendanceTableDTO> attendanceTableDTOS = studentSubjectRepository.
                getAttendanceTableDTOs(id , semester, subject, date, pageable);

        List<AttendanceTableDTO> tableDTOList = attendanceTableDTOS.getContent();


        return StudentAttendancePageDTO.builder()
                .id(id)
                .totalClasses(totalClasses)
                .classesAttended(totalClassesAttended)
                .overallAttendance(overallAttendance)
                .attendanceTables(attendanceTableDTOS)
                .build();
    }
}
