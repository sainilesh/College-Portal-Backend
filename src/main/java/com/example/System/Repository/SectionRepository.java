package com.example.System.Repository;

import com.example.System.Entity.Section;
import com.example.System.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

    @Query("""
SELECT s.students
FROM Section s
WHERE s.name = :section
""")
    List<Student> findStudentSection(@Param("section") String section);
}
