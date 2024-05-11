package com.project.coursesplatformapi.model;

import com.project.coursesplatformapi.dto.CourseDTO;
import com.project.coursesplatformapi.model.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "courses")
@Entity(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true, length = 10)
    @Pattern(regexp = "^[a-zA-Z]+(?:-[a-zA-Z]+)*$")
    private String code;
    private String instructor;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDate createdAt;
    private LocalDate inactivatedAt;

    public Course(CourseDTO courseDTO) {
        this.name = courseDTO.name();
        this.code = courseDTO.code();
        this.instructor = courseDTO.instructor();
        this.description = courseDTO.description();
        this.status = Status.ACTIVE;
        this.createdAt = LocalDate.now();
    }
}