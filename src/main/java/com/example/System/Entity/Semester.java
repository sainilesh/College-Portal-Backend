package com.example.System.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Semester {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "semester", fetch = FetchType.LAZY)
    private List<Subject> subjects;

    @OneToMany(mappedBy = "semester")
    private List<Section> sections;

}
