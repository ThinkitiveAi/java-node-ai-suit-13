package HealthFirstBackend.HealthFirstProject.dto;

import java.time.LocalDateTime;
import java.util.List;

public class GetAppointmentsResponseDTO {
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
        private String user_id;
        private String user_type; // "provider" or "patient"
        private List<Appointment> appointments;
        private Summary summary;

        public String getUser_id() { return user_id; }
        public void setUser_id(String user_id) { this.user_id = user_id; }
        public String getUser_type() { return user_type; }
        public void setUser_type(String user_type) { this.user_type = user_type; }
        public List<Appointment> getAppointments() { return appointments; }
        public void setAppointments(List<Appointment> appointments) { this.appointments = appointments; }
        public Summary getSummary() { return summary; }
        public void setSummary(Summary summary) { this.summary = summary; }

        public static class Appointment {
            private String appointment_id;
            private String booking_reference;
            private String provider_id;
            private String patient_id;
            private LocalDateTime appointment_date_time;
            private String appointment_type;
            private String status;
            private String notes;
            private String location;
            private String provider_name;
            private String patient_name;
            private LocalDateTime created_at;
            private LocalDateTime updated_at;

            public String getAppointment_id() { return appointment_id; }
            public void setAppointment_id(String appointment_id) { this.appointment_id = appointment_id; }
            public String getBooking_reference() { return booking_reference; }
            public void setBooking_reference(String booking_reference) { this.booking_reference = booking_reference; }
            public String getProvider_id() { return provider_id; }
            public void setProvider_id(String provider_id) { this.provider_id = provider_id; }
            public String getPatient_id() { return patient_id; }
            public void setPatient_id(String patient_id) { this.patient_id = patient_id; }
            public LocalDateTime getAppointment_date_time() { return appointment_date_time; }
            public void setAppointment_date_time(LocalDateTime appointment_date_time) { this.appointment_date_time = appointment_date_time; }
            public String getAppointment_type() { return appointment_type; }
            public void setAppointment_type(String appointment_type) { this.appointment_type = appointment_type; }
            public String getStatus() { return status; }
            public void setStatus(String status) { this.status = status; }
            public String getNotes() { return notes; }
            public void setNotes(String notes) { this.notes = notes; }
            public String getLocation() { return location; }
            public void setLocation(String location) { this.location = location; }
            public String getProvider_name() { return provider_name; }
            public void setProvider_name(String provider_name) { this.provider_name = provider_name; }
            public String getPatient_name() { return patient_name; }
            public void setPatient_name(String patient_name) { this.patient_name = patient_name; }
            public LocalDateTime getCreated_at() { return created_at; }
            public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }
            public LocalDateTime getUpdated_at() { return updated_at; }
            public void setUpdated_at(LocalDateTime updated_at) { this.updated_at = updated_at; }
        }

        public static class Summary {
            private int total_appointments;
            private int upcoming_appointments;
            private int completed_appointments;
            private int cancelled_appointments;

            public int getTotal_appointments() { return total_appointments; }
            public void setTotal_appointments(int total_appointments) { this.total_appointments = total_appointments; }
            public int getUpcoming_appointments() { return upcoming_appointments; }
            public void setUpcoming_appointments(int upcoming_appointments) { this.upcoming_appointments = upcoming_appointments; }
            public int getCompleted_appointments() { return completed_appointments; }
            public void setCompleted_appointments(int completed_appointments) { this.completed_appointments = completed_appointments; }
            public int getCancelled_appointments() { return cancelled_appointments; }
            public void setCancelled_appointments(int cancelled_appointments) { this.cancelled_appointments = cancelled_appointments; }
        }
    }
} 