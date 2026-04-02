package com.example.System.Repository;

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
SELECT ss
FROM StudentSubject ss
WHERE ss.student.id = :id
AND (:subject IS NULL OR ss.subject.name = :subject)
AND (:date IS NULL OR ss.conductedAt = :date)
AND (:semester IS NULL OR ss.subject.semester.name = :semester)
""")
    Page<StudentSubject> findAllStudentAttendance(@Param("id") Long id, @Param("semester") String semester,
                                                  @Param("subject")  String subject,
                                                  @Param("date")LocalDate date,
                                                  Pageable pageable);

    @Query("""
SELECT COUNT(DISTINCT ss.student.id)
FROM StudentSubject ss
WHERE ss.teacher.id = :teacherId
GROUP BY ss.student.id
HAVING (SUM(CASE WHEN ss.attendanceStatus = 'PRESENT' THEN 1 ELSE 0 END) * 100.0) / COUNT(ss) < 75
""")
    Optional<Long> findStudentsWithLowAttendance(@Param("teacherId") Long teacherId);


    @Query("""
SELECT COUNT(ss)
FROM StudentSubject ss
JOIN Student s ON ss.student.id = s.id
WHERE ss.teacher.id = :teacherId
AND s.section.name = :sectionName
""")
    Optional<Long> getTotalClassesForTeacher(@Param("teacherId") Long teacherId, @Param("sectionName") String sectionName);

    @Query("""
SELECT (SUM(CASE WHEN ss.attendanceStatus = 'PRESENT' THEN 1 ELSE 0 END) * 100.0 / COUNT(*))
FROM StudentSubject ss
WHERE ss.teacher.id = :teacherId
""")
    Optional<Double> findAverageAttendance(@Param("teacherId") Long teacherId);

    @Query("""
SELECT COALESCE(
AVG(CASE WHEN ss.attendanceStatus = :status THEN 100.0 ELSE 0.0 END),0)
FROM StudentSubject ss
WHERE ss.teacher.id = :teacherId
AND ss.student.id = :studentId
""")
    Double findAttendanceForStudent(@Param("teacherId") Long teacherId, @Param("studentId") Long studentId, @Param("status") AttendaceStatusEnum status);

    @Query("""
SELECT ss.student.section.name AS section
FROM StudentSubject ss
WHERE ss.teacher.id = :teacherId
GROUP BY ss.student.section
ORDER BY
(SUM(CASE WHEN ss.attendanceStatus = 'PRESENT' THEN 1 ELSE 0 END) * 100.0 / COUNT(ss)) ASC
""")
    List<String> findLeastAttendanceOfSection(@Param("teacherId") Long teacherId);

    @Query("""
SELECT ss
FROM StudentSubject ss
WHERE ss.teacher.id = :teacherId
AND ss.conductedAt = :conductedAt
AND ss.student.section.name = :section
""")
    List<StudentSubject> findByTeacherAndConductedAt(@Param("teacherId") Long teacherId, @Param("conductedAt") LocalDate date, @Param("section") String section);

}

