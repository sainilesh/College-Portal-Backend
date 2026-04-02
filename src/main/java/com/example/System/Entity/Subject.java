package com.example.System.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String subjectCode;

    @Column(nullable = false)
    private Long credits;

    @OneToMany(mappedBy = "subject",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Grade> grades;

    @OneToMany(mappedBy = "subject",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Teacher> teacher;

    @OneToMany(mappedBy = "subject")
    private List<TimeTable> timeTables;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id")
    private Semester semester;
}
