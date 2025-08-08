package HealthFirstBackend.HealthFirstProject.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Data
public class ProviderAvailabilityRequestDTO {
    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime start_time;

    @NotNull
    private LocalTime end_time;

    @NotBlank
    private String timezone;

    @Min(15)
    @Max(480)
    private Integer slot_duration = 30;

    @Min(0)
    @Max(120)
    private Integer break_duration = 0;

    private boolean is_recurring = false;

    private RecurrencePattern recurrence_pattern;

    private LocalDate recurrence_end_date;

    private AppointmentType appointment_type = AppointmentType.CONSULTATION;

    @NotNull
    private LocationDTO location;

    private PricingDTO pricing;

    private List<String> special_requirements;

    @Size(max = 500)
    private String notes;

    // Getters and setters omitted for brevity

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public LocalTime getStart_time() { return start_time; }
    public void setStart_time(LocalTime start_time) { this.start_time = start_time; }
    public LocalTime getEnd_time() { return end_time; }
    public void setEnd_time(LocalTime end_time) { this.end_time = end_time; }
    public String getTimezone() { return timezone; }
    public void setTimezone(String timezone) { this.timezone = timezone; }
    public Integer getSlot_duration() { return slot_duration; }
    public void setSlot_duration(Integer slot_duration) { this.slot_duration = slot_duration; }
    public Integer getBreak_duration() { return break_duration; }
    public void setBreak_duration(Integer break_duration) { this.break_duration = break_duration; }
    public boolean isIs_recurring() { return is_recurring; }
    public void setIs_recurring(boolean is_recurring) { this.is_recurring = is_recurring; }
    public RecurrencePattern getRecurrence_pattern() { return recurrence_pattern; }
    public void setRecurrence_pattern(RecurrencePattern recurrence_pattern) { this.recurrence_pattern = recurrence_pattern; }
    public LocalDate getRecurrence_end_date() { return recurrence_end_date; }
    public void setRecurrence_end_date(LocalDate recurrence_end_date) { this.recurrence_end_date = recurrence_end_date; }
    public AppointmentType getAppointment_type() { return appointment_type; }
    public void setAppointment_type(AppointmentType appointment_type) { this.appointment_type = appointment_type; }
    public LocationDTO getLocation() { return location; }
    public void setLocation(LocationDTO location) { this.location = location; }
    public PricingDTO getPricing() { return pricing; }
    public void setPricing(PricingDTO pricing) { this.pricing = pricing; }
    public List<String> getSpecial_requirements() { return special_requirements; }
    public void setSpecial_requirements(List<String> special_requirements) { this.special_requirements = special_requirements; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public enum RecurrencePattern {
        DAILY, WEEKLY, MONTHLY
    }

    public enum AppointmentType {
        CONSULTATION, FOLLOW_UP, EMERGENCY, TELEMEDICINE
    }

    public static class LocationDTO {
        @NotNull
        private LocationType type;
        private String address;
        private String room_number;
        // Getters and setters omitted for brevity
        public LocationType getType() { return type; }
        public void setType(LocationType type) { this.type = type; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public String getRoom_number() { return room_number; }
        public void setRoom_number(String room_number) { this.room_number = room_number; }

        public enum LocationType {
            CLINIC, HOSPITAL, TELEMEDICINE, HOME_VISIT
        }
    }

    public static class PricingDTO {
        @DecimalMin("0.0")
        private BigDecimal base_fee;
        private boolean insurance_accepted = false;
        private String currency = "USD";
        // Getters and setters omitted for brevity
        public BigDecimal getBase_fee() { return base_fee; }
        public void setBase_fee(BigDecimal base_fee) { this.base_fee = base_fee; }
        public boolean isInsurance_accepted() { return insurance_accepted; }
        public void setInsurance_accepted(boolean insurance_accepted) { this.insurance_accepted = insurance_accepted; }
        public String getCurrency() { return currency; }
        public void setCurrency(String currency) { this.currency = currency; }
    }
} 