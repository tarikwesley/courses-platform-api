package com.project.coursesplatformapi.model;

import com.project.coursesplatformapi.dto.ReviewDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reviews")
@Entity(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    private Integer rating;
    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private LocalDate createdAt;

    public Review(ReviewDTO reviewDTO, Registration registration) {
        this.comment = reviewDTO.comment();
        this.rating = reviewDTO.rating();
        this.user = registration.getUser();
        this.course = registration.getCourse();
        this.createdAt = LocalDate.now();
    }
}
