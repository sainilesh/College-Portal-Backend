package com.example.System.Repository;

import com.example.System.DTO.Teacher.TeacherLeaveRequestPageDTO;
import com.example.System.Entity.Leave;
import com.example.System.Enum.LeaveReasonEnum;
import com.example.System.Enum.LeaveStatusEnum;
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
public interface LeaveRepository extends JpaRepository<Leave, Long> {

    @Query(value = """
SELECT COALESCE(SUM((l.end_date - l.start_date) + 1), 0)
FROM Leave l
WHERE l.student_id = :studentId
""", nativeQuery = true)
    Long getTotalLeaves(@Param("studentId") Long studentId);

    Optional<Long> countByStudentIdAndStatus(Long studentId, LeaveStatusEnum status);

    @Query("""
SELECT l FROM Leave l
WHERE l.student.id = :studentId
AND (:status IS NULL OR l.status = :status)
AND (:leaveReason IS NULL OR l.leaveReason = :leaveReason)
AND (:endDate IS NULL OR l.endDate = endDate)
""")
    Page<Leave> findStudentsLeavesByFilters(
            Long studentId,
            LeaveStatusEnum status,
            LeaveReasonEnum leaveReason,
            LocalDate endDate,
            Pageable pageable
    );

    @Query("""
SELECT new com.example.System.DTO.Teacher.TeacherLeaveRequestPageDTO(
    l.id,
    s.name,
    l.leaveReason,
    l.startDate,
    l.endDate,
    l.status
)
FROM Leave l
JOIN l.student s
WHERE l.teacher.id = :teacherId
""")
    List<TeacherLeaveRequestPageDTO> findLeaveDTOs(@Param("teacherId") Long teacherId);


    @Query("SELECT l FROM Leave l JOIN FETCH l.teacher WHERE l.id = :leaveId")
    Optional<Leave> findByIdWithTeacher(@Param("leaveId") Long leaveId);
}
