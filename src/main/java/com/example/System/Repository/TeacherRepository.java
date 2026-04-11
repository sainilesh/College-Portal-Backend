package com.example.System.Repository;

import com.example.System.Entity.Student;
import com.example.System.Entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {

    @Query("""
SELECT t FROM Teacher t
JOIN FETCH t.section s
JOIN FETCH s.students
WHERE t.id = :id
""")
    Optional<Teacher> findTeacherWithStudents(Long id);


}
