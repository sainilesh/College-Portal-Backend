package com.example.System.Service.Teacher;

import com.example.System.DTO.Teacher.AttendanceAnalyticsTableDTO;
import com.example.System.DTO.Teacher.TeacherAttendanceAnalyticsDTO;
import com.example.System.Entity.Section;
import com.example.System.Entity.Student;
import com.example.System.Entity.Teacher;
import com.example.System.Enum.AttendaceStatusEnum;
import com.example.System.Repository.StudentSubjectRepository;
import com.example.System.Repository.TeacherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TeacherAttendanceAnalyticsPageService {

    private final TeacherRepository teacherRepository;
    private final StudentSubjectRepository studentSubjectRepository;

    @Cacheable(value = "teacherAttendanceAnalytics")
    public TeacherAttendanceAnalyticsDTO getTeacherAttendanceAnalyticsDTO(Long id, String section) {

        Teacher teacher = teacherRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Teacher with id " + id + " not found!"));

        Long totalClassesHeld = studentSubjectRepository.getTotalClassesForTeacher(id, section).orElse(0L);

        double averageAttendance = studentSubjectRepository.findAverageAttendance(id).orElse(0D);

        Long atRisk = studentSubjectRepository.findStudentsWithLowAttendance(id).orElse(0L);

        Section section1 = teacher.getSection();

        List<AttendanceAnalyticsTableDTO> attendanceAnalyticsTableDTOList = section1.getStudents().stream()
                .map(student -> mapToAttendanceAnalyticsDTO(student, teacher))
                .toList();

        return TeacherAttendanceAnalyticsDTO.builder()
                .id(id)
                .totalClassesHeld(totalClassesHeld)
                .averageAttendance(averageAttendance)
                .atRisk(atRisk)
                .attendanceAnalyticsTableDTOList(attendanceAnalyticsTableDTOList)
                .build();

    }

    private AttendanceAnalyticsTableDTO mapToAttendanceAnalyticsDTO(Student student, Teacher teacher) {

        double attendancePercentage = studentSubjectRepository.findAttendanceForStudent(student.getId(), teacher.getId(),
                AttendaceStatusEnum.PRESENT);

        Long totalClasses = studentSubjectRepository.getTotalClassesForTeacher(teacher.getId(), student.getSection().getName())
                .orElse(0L);

        Long classesAttended = studentSubjectRepository.getTotalClassesAttendedForSubject(student.getId(),
                teacher.getSubject().getId(), AttendaceStatusEnum.PRESENT).orElse(0L);

        return AttendanceAnalyticsTableDTO.builder()
                .id(student.getId())
                .name(student.getName())
                .rollNo(student.getRollNo())
                .attendancePercentage(attendancePercentage)
                .classesAttended(classesAttended)
                .totalClassesHeld(totalClasses)
                .build();
    }
}
