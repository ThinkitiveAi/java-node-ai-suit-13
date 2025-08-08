package HealthFirstBackend.HealthFirstProject.dto;

public class ProviderLoginResponseDTO {
    private String token;
    private String providerId;
    private String email;
    private String verificationStatus;

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getProviderId() { return providerId; }
    public void setProviderId(String providerId) { this.providerId = providerId; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getVerificationStatus() { return verificationStatus; }
    public void setVerificationStatus(String verificationStatus) { this.verificationStatus = verificationStatus; }
} 