package com.example.System.Repository;

import com.example.System.DTO.Teacher.Dashboard.TeacherDashboardStatsProjection;
import com.example.System.Entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DashboardRepository extends JpaRepository<Teacher, Long> {

    @Query(value = """
SELECT
  -- Low attendance students count
  (SELECT COUNT(*) FROM (
      SELECT ss.student_id
      FROM student_subject ss
      WHERE ss.teacher_id = :id
      GROUP BY ss.student_id
      HAVING (SUM(CASE WHEN ss.attendance_status = 'PRESENT' THEN 1 ELSE 0 END) * 100.0 / COUNT(*)) < 75
  ) AS low_attendance_students) AS lowAttendance,

  -- Pending leaves
  (SELECT COUNT(*) FROM leave l
   WHERE l.teacher_id = :id
   AND l.status = 'PENDING') AS pendingLeaves,

  -- Average attendance across all students
  (SELECT AVG(attendance_percentage) FROM (
      SELECT
        (SUM(CASE WHEN ss.attendance_status = 'PRESENT' THEN 1 ELSE 0 END) * 100.0 / COUNT(*)) AS attendance_percentage
      FROM student_subject ss
      WHERE ss.teacher_id = :id
      GROUP BY ss.student_id
  ) AS student_avg) AS averageAttendance,

  -- Least attendance section
  (SELECT s.name FROM section s
   JOIN student st ON st.section_id = s.id
   JOIN student_subject ss ON ss.student_id = st.id
   WHERE ss.teacher_id = :id
   GROUP BY s.name
   ORDER BY
     (SUM(CASE WHEN ss.attendance_status = 'PRESENT' THEN 1 ELSE 0 END) * 100.0 / COUNT(*)) ASC
   LIMIT 1) AS leastSection
""", nativeQuery = true)
    TeacherDashboardStatsProjection getDashboardStats(Long id);

}
