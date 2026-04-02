package com.example.System.Repository;

import com.example.System.Entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @Query("""
SELECT SUM(su.credits)
FROM Subject su
WHERE su.semester.name = :semester
""")
    Optional<Long> findTotalCredits(@Param("semester") String semester);
}
