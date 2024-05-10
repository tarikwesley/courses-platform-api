package com.project.coursesplatformapi.repository;

import com.project.coursesplatformapi.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
}
