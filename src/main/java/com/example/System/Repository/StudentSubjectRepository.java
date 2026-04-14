package com.example.System.Repository;

import com.example.System.DTO.Student.Attendance.AttendanceTableDTO;
import com.example.System.DTO.Student.Dashboard.AttendanceStatsProjection;
import com.example.System.Entity.Student;
import com.example.System.Entity.StudentSubject;
import com.example.System.Enum.AttendaceStatusEnum;
import com.example.System.Enum.TeacherAttendanceStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentSubjectRepository extends JpaRepository<StudentSubject, Long> {

    @Query("""
SELECT COUNT(ss)
FROM StudentSubject ss
WHERE ss.student.id = :id
AND ss.attendanceStatus = :status
""")
    Optional<Long> getTotalClassesAttended(@Param("id")  Long id, @Param("status")AttendaceStatusEnum status);

    @Query("""
SELECT COUNT(*)
FROM StudentSubject ss
WHERE ss.student.id = :id
""")
    Optional<Long> getTotalClassesConducted(@Param("id")   Long id);


    List<StudentSubject> findAllByStudentId(Long id);


    @Query("""
SELECT COUNT(ss)
FROM StudentSubject ss
WHERE ss.student.id = :studentId
AND ss.subject.id = :subjectId
AND ss.attendanceStatus = :status
""")
    Optional<Long> getTotalClassesAttendedForSubject(@Param("studentId") Long studentId, @Param("subjectId") Long subjectId, @Param("status")AttendaceStatusEnum status);


    @Query("""
SELECT COUNT(ss)
FROM StudentSubject ss
WHERE ss.student.id = :studentId
AND ss.subject.id = :subjectId
""")
    Optional<Long> getTotalClassesForSubject(@Param("studentId") Long studentId, @Param("subjectId") Long subjectId);


    @Query("""
SELECT COUNT(ss)
FROM StudentSubject ss
JOIN Student s ON ss.student.id = s.id
WHERE ss.teacher.id = :teacherId
AND s.section.name = :sectionName
""")
    Optional<Long> getTotalClassesForTeacher(@Param("teacherId") Long teacherId, @Param("sectionName") String sectionName);


    @Query("""
SELECT ss.student.id, COUNT(ss)
FROM StudentSubject ss
WHERE ss.teacher.id = :teacherId
AND ss.subject.id = :subjectId
AND ss.attendanceStatus = :status
GROUP BY ss.student.id
""")
    List<Object[]> getAttendanceCountPerStudent(
            Long teacherId,
            Long subjectId,
            AttendaceStatusEnum status
    );


    @Query("""
SELECT ss
FROM StudentSubject ss
WHERE ss.teacher.id = :teacherId
AND ss.conductedAt = :conductedAt
AND ss.student.section.name = :section
""")
    List<StudentSubject> findByTeacherAndConductedAt(@Param("teacherId") Long teacherId, @Param("conductedAt") LocalDate date, @Param("section") String section);

    @Query("""
SELECT ss.student
FROM StudentSubject ss
GROUP BY ss.student
HAVING
(SUM(CASE WHEN ss.attendanceStatus='PRESENT' THEN 1 ELSE 0 END)*100.0)
 / COUNT(ss) < 75
""")
    List<Student> findStudentsBelow75();

    @Query("""
SELECT new com.example.System.DTO.Student.Attendance.AttendanceTableDTO(
    ss.id,
    ss.subject.name,
    ss.conductedAt,
    tt.startTime,
    tt.endTime,
    ss.attendanceStatus,
    ss.remarks
)
FROM StudentSubject ss
JOIN ss.student st
JOIN st.section sec
JOIN TimeTable tt
    ON tt.subject = ss.subject
    AND tt.section = sec 
    AND tt.dayOfWeek = FUNCTION('upper', FUNCTION('to_char', ss.conductedAt, 'FMDay'))
WHERE st.id = :id
AND (:semester IS NULL OR ss.subject.semester = :semester)
AND (:subject IS NULL OR ss.subject.name = :subject)
AND (:date IS NULL OR ss.conductedAt = :date)
""")
    Page<AttendanceTableDTO> getAttendanceTableDTOs(
            Long id, String semester, String subject, LocalDate date, Pageable pageable);

    @Query("""
SELECT
    COUNT(ss) as totalClasses,
    SUM(CASE WHEN ss.attendanceStatus = :status THEN 1 ELSE 0 END) as attendedClasses
FROM StudentSubject ss
WHERE ss.student.id = :id
""")
    AttendanceStatsProjection getAttendanceStats(@Param("id") Long id,
                                                 @Param("status") AttendaceStatusEnum status);

}

