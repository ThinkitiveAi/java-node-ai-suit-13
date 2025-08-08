package HealthFirstBackend.HealthFirstProject.repository;

import HealthFirstBackend.HealthFirstProject.model.ProviderAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ProviderAvailabilityRepository extends JpaRepository<ProviderAvailability, UUID> {
    List<ProviderAvailability> findByProviderIdAndDate(UUID providerId, LocalDate date);
    List<ProviderAvailability> findByProviderIdAndDateBetween(UUID providerId, LocalDate startDate, LocalDate endDate);
    List<ProviderAvailability> findByProviderIdAndStatus(UUID providerId, ProviderAvailability.Status status);
    List<ProviderAvailability> findByProviderIdAndDateBetweenAndStatus(UUID providerId, LocalDate startDate, LocalDate endDate, ProviderAvailability.Status status);
} 