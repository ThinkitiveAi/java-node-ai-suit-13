package HealthFirstBackend.HealthFirstProject.repository;

import HealthFirstBackend.HealthFirstProject.model.AppointmentSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AppointmentSlotRepository extends JpaRepository<AppointmentSlot, UUID> {
    List<AppointmentSlot> findByProviderIdAndSlotStartTimeBetween(UUID providerId, LocalDateTime startTime, LocalDateTime endTime);
    List<AppointmentSlot> findByAvailabilityId(UUID availabilityId);
    List<AppointmentSlot> findByProviderIdAndStatus(UUID providerId, AppointmentSlot.Status status);
    List<AppointmentSlot> findByPatientId(UUID patientId);
} 