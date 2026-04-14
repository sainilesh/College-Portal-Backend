package com.example.System.Repository;

import com.example.System.Entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    @Query("""
SELECT g
FROM Grade g
JOIN FETCH g.subject s
JOIN FETCH s.semester
WHERE g.student.id = :studentId
""")
    List<Grade> findByStudentIdWithSubject(Long studentId);


}
