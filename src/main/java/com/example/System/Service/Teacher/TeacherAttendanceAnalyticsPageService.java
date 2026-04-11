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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TeacherAttendanceAnalyticsPageService {

    private final TeacherRepository teacherRepository;
    private final StudentSubjectRepository studentSubjectRepository;

    @Cacheable(value = "teacherAttendanceAnalytics", key = "#id + '_' + #section")
    public TeacherAttendanceAnalyticsDTO getTeacherAttendanceAnalyticsDTO(Long id, String section) {

        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));

        long totalClassesHeld = studentSubjectRepository
                .getTotalClassesForTeacher(id, section)
                .orElse(0L);

        List<Object[]> results = studentSubjectRepository.getAttendanceCountPerStudent(
                id,
                teacher.getSubject().getId(),
                AttendaceStatusEnum.PRESENT
        );

        Map<Long, Long> attendanceMap = results.stream()
                .collect(Collectors.toMap(
                        row -> (Long) row[0],
                        row -> (Long) row[1]
                ));

        Section sectionEntity = teacher.getSection();

        double totalPercentageSum = 0;
        long atRiskCount = 0;

        List<AttendanceAnalyticsTableDTO> table = new ArrayList<>();

        for (Student student : sectionEntity.getStudents()) {

            Long attended = attendanceMap.getOrDefault(student.getId(), 0L);

            double percentage = totalClassesHeld == 0
                    ? 0
                    : (attended * 100.0) / totalClassesHeld;

            totalPercentageSum += percentage;

            if (percentage < 75) {
                atRiskCount++;
            }

            table.add(
                    AttendanceAnalyticsTableDTO.builder()
                            .id(student.getId())
                            .name(student.getName())
                            .rollNo(student.getRollNo())
                            .attendancePercentage(roundTo2Decimal(percentage))
                            .classesAttended(attended)
                            .totalClassesHeld(totalClassesHeld)
                            .build()
            );
        }

        double averageAttendance = sectionEntity.getStudents().isEmpty()
                ? 0
                : totalPercentageSum / sectionEntity.getStudents().size();

        return TeacherAttendanceAnalyticsDTO.builder()
                .id(id)
                .totalClassesHeld(totalClassesHeld)
                .averageAttendance(roundTo2Decimal(averageAttendance))
                .atRisk(atRiskCount)
                .attendanceAnalyticsTableDTOList(table)
                .build();
    }

    public double roundTo2Decimal(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
