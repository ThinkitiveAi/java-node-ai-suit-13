package HealthFirstBackend.HealthFirstProject.dto;

public class PatientRegistrationResponseDTO {
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
        private String patient_id;
        private String email;
        private String phone_number;
        private boolean email_verified;
        private boolean phone_verified;
        public String getPatient_id() { return patient_id; }
        public void setPatient_id(String patient_id) { this.patient_id = patient_id; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPhone_number() { return phone_number; }
        public void setPhone_number(String phone_number) { this.phone_number = phone_number; }
        public boolean isEmail_verified() { return email_verified; }
        public void setEmail_verified(boolean email_verified) { this.email_verified = email_verified; }
        public boolean isPhone_verified() { return phone_verified; }
        public void setPhone_verified(boolean phone_verified) { this.phone_verified = phone_verified; }
    }
} 