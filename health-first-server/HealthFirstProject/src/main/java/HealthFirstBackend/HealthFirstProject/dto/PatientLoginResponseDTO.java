package HealthFirstBackend.HealthFirstProject.dto;

public class PatientLoginResponseDTO {
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
        private String access_token;
        private int expires_in;
        private String token_type;
        private Patient patient;
        public String getAccess_token() { return access_token; }
        public void setAccess_token(String access_token) { this.access_token = access_token; }
        public int getExpires_in() { return expires_in; }
        public void setExpires_in(int expires_in) { this.expires_in = expires_in; }
        public String getToken_type() { return token_type; }
        public void setToken_type(String token_type) { this.token_type = token_type; }
        public Patient getPatient() { return patient; }
        public void setPatient(Patient patient) { this.patient = patient; }
    }
    public static class Patient {
        private String id;
        private String first_name;
        private String last_name;
        private String email;
        private String phone_number;
        private boolean email_verified;
        private boolean phone_verified;
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getFirst_name() { return first_name; }
        public void setFirst_name(String first_name) { this.first_name = first_name; }
        public String getLast_name() { return last_name; }
        public void setLast_name(String last_name) { this.last_name = last_name; }
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