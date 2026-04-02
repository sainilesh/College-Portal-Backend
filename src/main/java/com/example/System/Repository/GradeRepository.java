package com.example.System.Repository;

import com.example.System.Entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    @Query("""
SELECT g
FROM Semester sem
JOIN sem.subjects s
LEFT JOIN Grade g ON g.subject.id = s.id
AND g.student.id = :studentId
WHERE sem.name = :semester
""")
    List<Grade> findByGradesBySemester(Long studentId, String semester);

    List<Grade> findByStudentId(Long studentId);


    @Query(value = """
SELECT MAX(sgpa)
FROM (
    SELECT
        SUM(CASE WHEN g.grade >= 5 THEN g.grade * s.credits ELSE 0 END) /
        NULLIF(SUM(CASE WHEN g.grade >= 5 THEN s.credits ELSE 0 END),0) AS sgpa
    FROM grade g
    JOIN subject s ON s.id = g.subject_id
    WHERE g.student_id = :studentId
    GROUP BY s.semester_id
) sgpa_table;
""", nativeQuery = true)
    Optional<Double> findHighestSGPA(@Param("studentId") Long studentId);

}
