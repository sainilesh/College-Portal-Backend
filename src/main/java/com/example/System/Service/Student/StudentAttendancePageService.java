package com.example.System.Service.Student;

import com.example.System.DTO.Student.Attendance.AttendanceTableDTO;
import com.example.System.DTO.Student.Attendance.StudentAttendancePageDTO;
import com.example.System.Entity.StudentSubject;
import com.example.System.Entity.TimeTable;
import com.example.System.Enum.AttendaceStatusEnum;
import com.example.System.Repository.StudentSubjectRepository;
import com.example.System.Repository.TimeTableRepository;
import com.example.System.SystemApplication;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentAttendancePageService {

    private final StudentSubjectRepository studentSubjectRepository;
    private final TimeTableRepository timeTableRepository;

    @Cacheable(
            value = "attendancePage",
            key = "#id + ':' + #semester + ':' + #subject + ':' + #date + ':' + #page + ':' + #size"
    )
    public StudentAttendancePageDTO getAttendancePage(Long id, String semester, String subject, LocalDate date,int page,int size) {

        System.out.println("getting from the DB....");

        Long totalClassesAttended = studentSubjectRepository.getTotalClassesAttended(id, AttendaceStatusEnum.PRESENT).orElseThrow(() ->
                new IllegalArgumentException("Total classes  attended not found"));

        Long totalClasses = studentSubjectRepository.getTotalClassesConducted(id).orElseThrow(() ->
                new IllegalArgumentException("Total classes not found"));

        Long overallAttendance = Math.round(((double)totalClassesAttended/totalClasses)*100);

        Pageable pageable = PageRequest.of(page, size, Sort.by("conductedAt").descending());

        Page<StudentSubject> studentSubjects = studentSubjectRepository.findAllStudentAttendance(id, semester, subject, date, pageable);

        Page<AttendanceTableDTO> attendanceTableDTOS = studentSubjects.map(this::mapToAttendanceTableDTO);

        return StudentAttendancePageDTO.builder()
                .id(id)
                .totalClasses(totalClasses)
                .classesAttended(totalClassesAttended)
                .overallAttendance(overallAttendance)
                .attendanceTables(attendanceTableDTOS)
                .build();
    }

    private AttendanceTableDTO mapToAttendanceTableDTO(StudentSubject studentSubject) {
        DayOfWeek dayOfWeek = studentSubject.getConductedAt().getDayOfWeek();

        TimeTable timeTable = timeTableRepository.findByDayOfWeekAndSubjectAndSection(dayOfWeek, studentSubject.getSubject(),
                        studentSubject.getStudent().getSection())
                .orElseThrow(() -> new IllegalArgumentException("Time table not found"));

        return AttendanceTableDTO.builder()
                .id(studentSubject.getId())
                .name(studentSubject.getSubject().getName())
                .date(studentSubject.getConductedAt())
                .startTime(timeTable.getStartTime())
                .endTime(timeTable.getEndTime())
                .status(studentSubject.getAttendanceStatus())
                .remarks(studentSubject.getRemarks())
                .build();
    }
}
