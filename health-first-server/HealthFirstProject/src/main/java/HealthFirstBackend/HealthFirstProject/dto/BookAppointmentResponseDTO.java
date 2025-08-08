package HealthFirstBackend.HealthFirstProject.dto;

import java.time.LocalDateTime;

public class BookAppointmentResponseDTO {
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
        private String appointment_id;
        private String booking_reference;
        private String provider_id;
        private String patient_id;
        private LocalDateTime appointment_date_time;
        private String appointment_type;
        private String status;
        private AppointmentDetails appointment_details;

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
        public AppointmentDetails getAppointment_details() { return appointment_details; }
        public void setAppointment_details(AppointmentDetails appointment_details) { this.appointment_details = appointment_details; }

        public static class AppointmentDetails {
            private String notes;
            private String location;
            private String provider_name;
            private String patient_name;

            public String getNotes() { return notes; }
            public void setNotes(String notes) { this.notes = notes; }
            public String getLocation() { return location; }
            public void setLocation(String location) { this.location = location; }
            public String getProvider_name() { return provider_name; }
            public void setProvider_name(String provider_name) { this.provider_name = provider_name; }
            public String getPatient_name() { return patient_name; }
            public void setPatient_name(String patient_name) { this.patient_name = patient_name; }
        }
    }
} 