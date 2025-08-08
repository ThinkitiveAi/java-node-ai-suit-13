package HealthFirstBackend.HealthFirstProject.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class InsuranceInfo {
    private String provider;

    private String policyNumber;

    // Getters and setters omitted for brevity

    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }
    
    public String getPolicyNumber() { return policyNumber; }
    public void setPolicyNumber(String policyNumber) { this.policyNumber = policyNumber; }
} 