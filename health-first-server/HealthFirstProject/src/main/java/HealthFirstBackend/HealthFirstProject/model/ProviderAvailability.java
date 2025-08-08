package HealthFirstBackend.HealthFirstProject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.GenericGenerator;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "provider_availability", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"provider_id", "date", "start_time"})
})
public class ProviderAvailability {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "provider_id", nullable = false)
    private UUID providerId;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @NotBlank
    @Column(name = "timezone", nullable = false)
    private String timezone;

    @Column(name = "is_recurring", nullable = false)
    private boolean isRecurring = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "recurrence_pattern")
    private RecurrencePattern recurrencePattern;

    @Column(name = "recurrence_end_date")
    private LocalDate recurrenceEndDate;

    @Min(15)
    @Max(480)
    @Column(name = "slot_duration", nullable = false)
    private Integer slotDuration = 30;

    @Min(0)
    @Max(120)
    @Column(name = "break_duration", nullable = false)
    private Integer breakDuration = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.AVAILABLE;

    @Min(1)
    @Max(10)
    @Column(name = "max_appointments_per_slot", nullable = false)
    private Integer maxAppointmentsPerSlot = 1;

    @Min(0)
    @Column(name = "current_appointments", nullable = false)
    private Integer currentAppointments = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "appointment_type", nullable = false)
    private AppointmentType appointmentType = AppointmentType.CONSULTATION;

    @Embedded
    private Location location;

    @Embedded
    private Pricing pricing;

    @Size(max = 500)
    @Column(name = "notes")
    private String notes;

    @ElementCollection
    private List<String> specialRequirements;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Getters and setters omitted for brevity

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public UUID getProviderId() { return providerId; }
    public void setProviderId(UUID providerId) { this.providerId = providerId; }
    
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    
    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    
    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    
    public String getTimezone() { return timezone; }
    public void setTimezone(String timezone) { this.timezone = timezone; }
    
    public boolean isRecurring() { return isRecurring; }
    public void setIsRecurring(boolean isRecurring) { this.isRecurring = isRecurring; }
    
    public RecurrencePattern getRecurrencePattern() { return recurrencePattern; }
    public void setRecurrencePattern(RecurrencePattern recurrencePattern) { this.recurrencePattern = recurrencePattern; }
    
    public LocalDate getRecurrenceEndDate() { return recurrenceEndDate; }
    public void setRecurrenceEndDate(LocalDate recurrenceEndDate) { this.recurrenceEndDate = recurrenceEndDate; }
    
    public Integer getSlotDuration() { return slotDuration; }
    public void setSlotDuration(Integer slotDuration) { this.slotDuration = slotDuration; }
    
    public Integer getBreakDuration() { return breakDuration; }
    public void setBreakDuration(Integer breakDuration) { this.breakDuration = breakDuration; }
    
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    
    public Integer getMaxAppointmentsPerSlot() { return maxAppointmentsPerSlot; }
    public void setMaxAppointmentsPerSlot(Integer maxAppointmentsPerSlot) { this.maxAppointmentsPerSlot = maxAppointmentsPerSlot; }
    
    public Integer getCurrentAppointments() { return currentAppointments; }
    public void setCurrentAppointments(Integer currentAppointments) { this.currentAppointments = currentAppointments; }
    
    public AppointmentType getAppointmentType() { return appointmentType; }
    public void setAppointmentType(AppointmentType appointmentType) { this.appointmentType = appointmentType; }
    
    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }
    
    public Pricing getPricing() { return pricing; }
    public void setPricing(Pricing pricing) { this.pricing = pricing; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public List<String> getSpecialRequirements() { return specialRequirements; }
    public void setSpecialRequirements(List<String> specialRequirements) { this.specialRequirements = specialRequirements; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public enum Status {
        AVAILABLE, BOOKED, CANCELLED, BLOCKED, MAINTENANCE
    }

    public enum RecurrencePattern {
        DAILY, WEEKLY, MONTHLY
    }

    public enum AppointmentType {
        CONSULTATION, FOLLOW_UP, EMERGENCY, TELEMEDICINE
    }
} 