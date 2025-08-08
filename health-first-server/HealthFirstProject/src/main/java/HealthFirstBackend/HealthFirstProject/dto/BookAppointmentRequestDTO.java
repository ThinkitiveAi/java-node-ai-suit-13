package HealthFirstBackend.HealthFirstProject.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

public class BookAppointmentRequestDTO {
    @NotNull
    private String providerId;

    @NotNull
    private String patientId;

    @NotNull
    private LocalDateTime appointmentDateTime;

    @NotBlank
    @Size(max = 50)
    private String appointmentType;

    @Size(max = 500)
    private String notes;

    private List<String> specialRequirements;

    @Size(max = 100)
    private String preferredProviderNotes;

    // Getters and setters
    public String getProviderId() { return providerId; }
    public void setProviderId(String providerId) { this.providerId = providerId; }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public LocalDateTime getAppointmentDateTime() { return appointmentDateTime; }
    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) { this.appointmentDateTime = appointmentDateTime; }

    public String getAppointmentType() { return appointmentType; }
    public void setAppointmentType(String appointmentType) { this.appointmentType = appointmentType; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public List<String> getSpecialRequirements() { return specialRequirements; }
    public void setSpecialRequirements(List<String> specialRequirements) { this.specialRequirements = specialRequirements; }

    public String getPreferredProviderNotes() { return preferredProviderNotes; }
    public void setPreferredProviderNotes(String preferredProviderNotes) { this.preferredProviderNotes = preferredProviderNotes; }
} 