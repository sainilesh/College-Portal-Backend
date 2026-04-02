package com.example.System.Repository;

import com.example.System.Entity.Section;
import com.example.System.Entity.Subject;
import com.example.System.Entity.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {

    Optional<List<TimeTable>> findAllBySection(Section section);

    Optional<TimeTable> findByDayOfWeekAndSubjectAndSection(DayOfWeek dayofWeek, Subject subject, Section section);

    Long countByDayOfWeekAndTeacherId(DayOfWeek dayofWeek, Long id);

    List<TimeTable> findByDayOfWeekAndTeacherId(DayOfWeek dayofWeek, Long id);
}
