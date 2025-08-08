package HealthFirstBackend.HealthFirstProject.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class Location {
    @NotNull
    @Enumerated(EnumType.STRING)
    private LocationType type;

    private String address;

    private String roomNumber;

    // Getters and setters omitted for brevity

    public LocationType getType() { return type; }
    public void setType(LocationType type) { this.type = type; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public enum LocationType {
        CLINIC, HOSPITAL, TELEMEDICINE, HOME_VISIT
    }
} 