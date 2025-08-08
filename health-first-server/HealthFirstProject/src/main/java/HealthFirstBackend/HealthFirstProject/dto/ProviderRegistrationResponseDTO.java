package HealthFirstBackend.HealthFirstProject.dto;

public class ProviderRegistrationResponseDTO {
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
        private String email;
        private String verification_status;
        // Getters and setters omitted for brevity
        public String getProvider_id() { return provider_id; }
        public void setProvider_id(String provider_id) { this.provider_id = provider_id; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getVerification_status() { return verification_status; }
        public void setVerification_status(String verification_status) { this.verification_status = verification_status; }
    }
    // Getters and setters omitted for brevity
} 