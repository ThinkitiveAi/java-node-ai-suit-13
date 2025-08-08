package HealthFirstBackend.HealthFirstProject.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Embeddable
public class Pricing {
    @DecimalMin("0.0")
    private BigDecimal baseFee;

    private boolean insuranceAccepted = false;

    private String currency = "USD";

    // Getters and setters omitted for brevity

    public BigDecimal getBaseFee() { return baseFee; }
    public void setBaseFee(BigDecimal baseFee) { this.baseFee = baseFee; }
    
    public boolean isInsuranceAccepted() { return insuranceAccepted; }
    public void setInsuranceAccepted(boolean insuranceAccepted) { this.insuranceAccepted = insuranceAccepted; }
    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
} 