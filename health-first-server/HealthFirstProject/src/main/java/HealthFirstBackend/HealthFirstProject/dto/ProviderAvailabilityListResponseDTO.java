package HealthFirstBackend.HealthFirstProject.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ProviderAvailabilityListResponseDTO {
    private boolean success;
    private String message;
    private Data data;

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Data getData() { return data; }
    public void setData(Data data) { this.data = data; }

    public static class Data {
        private String provider_id;
        private List<AvailabilitySlot> availability_slots;
        private int total_slots;
        private Summary summary;

        public String getProvider_id() { return provider_id; }
        public void setProvider_id(String provider_id) { this.provider_id = provider_id; }
        public List<AvailabilitySlot> getAvailability_slots() { return availability_slots; }
        public void setAvailability_slots(List<AvailabilitySlot> availability_slots) { this.availability_slots = availability_slots; }
        public int getTotal_slots() { return total_slots; }
        public void setTotal_slots(int total_slots) { this.total_slots = total_slots; }
        public Summary getSummary() { return summary; }
        public void setSummary(Summary summary) { this.summary = summary; }

        public static class AvailabilitySlot {
            private String id;
            private LocalDate date;
            private LocalTime start_time;
            private LocalTime end_time;
            private String timezone;
            private String status;
            private String appointment_type;
            private int max_appointments_per_slot;
            private int current_appointments;
            private int available_appointments;
            private Location location;
            private Pricing pricing;
            private String notes;
            private List<String> special_requirements;

            public String getId() { return id; }
            public void setId(String id) { this.id = id; }
            public LocalDate getDate() { return date; }
            public void setDate(LocalDate date) { this.date = date; }
            public LocalTime getStart_time() { return start_time; }
            public void setStart_time(LocalTime start_time) { this.start_time = start_time; }
            public LocalTime getEnd_time() { return end_time; }
            public void setEnd_time(LocalTime end_time) { this.end_time = end_time; }
            public String getTimezone() { return timezone; }
            public void setTimezone(String timezone) { this.timezone = timezone; }
            public String getStatus() { return status; }
            public void setStatus(String status) { this.status = status; }
            public String getAppointment_type() { return appointment_type; }
            public void setAppointment_type(String appointment_type) { this.appointment_type = appointment_type; }
            public int getMax_appointments_per_slot() { return max_appointments_per_slot; }
            public void setMax_appointments_per_slot(int max_appointments_per_slot) { this.max_appointments_per_slot = max_appointments_per_slot; }
            public int getCurrent_appointments() { return current_appointments; }
            public void setCurrent_appointments(int current_appointments) { this.current_appointments = current_appointments; }
            public int getAvailable_appointments() { return available_appointments; }
            public void setAvailable_appointments(int available_appointments) { this.available_appointments = available_appointments; }
            public Location getLocation() { return location; }
            public void setLocation(Location location) { this.location = location; }
            public Pricing getPricing() { return pricing; }
            public void setPricing(Pricing pricing) { this.pricing = pricing; }
            public String getNotes() { return notes; }
            public void setNotes(String notes) { this.notes = notes; }
            public List<String> getSpecial_requirements() { return special_requirements; }
            public void setSpecial_requirements(List<String> special_requirements) { this.special_requirements = special_requirements; }

            public static class Location {
                private String type;
                private String address;
                private String room_number;

                public String getType() { return type; }
                public void setType(String type) { this.type = type; }
                public String getAddress() { return address; }
                public void setAddress(String address) { this.address = address; }
                public String getRoom_number() { return room_number; }
                public void setRoom_number(String room_number) { this.room_number = room_number; }
            }

            public static class Pricing {
                private BigDecimal base_fee;
                private boolean insurance_accepted;
                private String currency;

                public BigDecimal getBase_fee() { return base_fee; }
                public void setBase_fee(BigDecimal base_fee) { this.base_fee = base_fee; }
                public boolean isInsurance_accepted() { return insurance_accepted; }
                public void setInsurance_accepted(boolean insurance_accepted) { this.insurance_accepted = insurance_accepted; }
                public String getCurrency() { return currency; }
                public void setCurrency(String currency) { this.currency = currency; }
            }
        }

        public static class Summary {
            private int total_available_slots;
            private int total_booked_slots;
            private int total_cancelled_slots;
            private int total_blocked_slots;

            public int getTotal_available_slots() { return total_available_slots; }
            public void setTotal_available_slots(int total_available_slots) { this.total_available_slots = total_available_slots; }
            public int getTotal_booked_slots() { return total_booked_slots; }
            public void setTotal_booked_slots(int total_booked_slots) { this.total_booked_slots = total_booked_slots; }
            public int getTotal_cancelled_slots() { return total_cancelled_slots; }
            public void setTotal_cancelled_slots(int total_cancelled_slots) { this.total_cancelled_slots = total_cancelled_slots; }
            public int getTotal_blocked_slots() { return total_blocked_slots; }
            public void setTotal_blocked_slots(int total_blocked_slots) { this.total_blocked_slots = total_blocked_slots; }
        }
    }
} 