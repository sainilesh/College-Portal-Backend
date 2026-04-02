package com.example.System.Repository;

import com.example.System.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByRollNo(String rollNo);

    @Query("""
SELECT s
FROM Student s
WHERE s.section.name = :sectionName
""")
    List<Student> findStudentsBySection(@Param("sectionName") String sectionName);
}
