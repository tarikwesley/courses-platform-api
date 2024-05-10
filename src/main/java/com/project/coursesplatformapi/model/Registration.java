package com.project.coursesplatformapi.model;

import com.project.coursesplatformapi.dto.RegistrationDTO;
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
@Table(name = "registrations")
@Entity(name = "registrations")
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;
    @ManyToOne
    @JoinColumn(name = "code", referencedColumnName = "code")
    private Course course;
    private LocalDate registrationDate;

    public Registration(RegistrationDTO register) {
        this.user = register.user();
        this.course = register.course();
        this.registrationDate = LocalDate.now();
    }
}
