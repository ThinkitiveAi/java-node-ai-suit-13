package HealthFirstBackend.HealthFirstProject.repository;

import HealthFirstBackend.HealthFirstProject.model.PatientVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PatientVerificationTokenRepository extends JpaRepository<PatientVerificationToken, Long> {
    Optional<PatientVerificationToken> findByToken(String token);
}