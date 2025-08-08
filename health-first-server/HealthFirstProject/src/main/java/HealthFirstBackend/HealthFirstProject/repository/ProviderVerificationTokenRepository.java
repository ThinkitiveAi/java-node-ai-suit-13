package HealthFirstBackend.HealthFirstProject.repository;

import HealthFirstBackend.HealthFirstProject.model.ProviderVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProviderVerificationTokenRepository extends JpaRepository<ProviderVerificationToken, Long> {
    Optional<ProviderVerificationToken> findByToken(String token);
} 