package HealthFirstBackend.HealthFirstProject.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public class PatientRegistrationRequestDTO {
    @NotBlank
    @Size(min = 2, max = 50)
    private String first_name;

    @NotBlank
    @Size(min = 2, max = 50)
    private String last_name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number format")
    private String phone_number;

    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotBlank
    private String confirm_password;

    @NotNull
    @Past
    private LocalDate date_of_birth;

    @NotBlank
    @Pattern(regexp = "male|female|other|prefer_not_to_say", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Invalid gender")
    private String gender;

    @NotNull
    private AddressDTO address;

    private EmergencyContactDTO emergency_contact;

    private List<@NotBlank String> medical_history;

    private InsuranceInfoDTO insurance_info;

    // Getters and setters omitted for brevity
    public String getFirst_name() { return first_name; }
    public void setFirst_name(String first_name) { this.first_name = first_name; }
    public String getLast_name() { return last_name; }
    public void setLast_name(String last_name) { this.last_name = last_name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone_number() { return phone_number; }
    public void setPhone_number(String phone_number) { this.phone_number = phone_number; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getConfirm_password() { return confirm_password; }
    public void setConfirm_password(String confirm_password) { this.confirm_password = confirm_password; }
    public LocalDate getDate_of_birth() { return date_of_birth; }
    public void setDate_of_birth(LocalDate date_of_birth) { this.date_of_birth = date_of_birth; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public AddressDTO getAddress() { return address; }
    public void setAddress(AddressDTO address) { this.address = address; }
    public EmergencyContactDTO getEmergency_contact() { return emergency_contact; }
    public void setEmergency_contact(EmergencyContactDTO emergency_contact) { this.emergency_contact = emergency_contact; }
    public List<String> getMedical_history() { return medical_history; }
    public void setMedical_history(List<String> medical_history) { this.medical_history = medical_history; }
    public InsuranceInfoDTO getInsurance_info() { return insurance_info; }
    public void setInsurance_info(InsuranceInfoDTO insurance_info) { this.insurance_info = insurance_info; }

    public static class AddressDTO {
        @NotBlank
        @Size(max = 200)
        private String street;
        @NotBlank
        @Size(max = 100)
        private String city;
        @NotBlank
        @Size(max = 50)
        private String state;
        @NotBlank
        @Pattern(regexp = "^\\d{5}(-\\d{4})?$", message = "Invalid postal code")
        private String zip;
        // Getters and setters omitted for brevity
        public String getStreet() { return street; }
        public void setStreet(String street) { this.street = street; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getState() { return state; }
        public void setState(String state) { this.state = state; }
        public String getZip() { return zip; }
        public void setZip(String zip) { this.zip = zip; }
    }
    public static class EmergencyContactDTO {
        private String name;
        @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number format")
        private String phone;
        private String relationship;
        // Getters and setters omitted for brevity
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getRelationship() { return relationship; }
        public void setRelationship(String relationship) { this.relationship = relationship; }
    }
    public static class InsuranceInfoDTO {
        private String provider;
        private String policy_number;
        // Getters and setters omitted for brevity
        public String getProvider() { return provider; }
        public void setProvider(String provider) { this.provider = provider; }
        public String getPolicy_number() { return policy_number; }
        public void setPolicy_number(String policy_number) { this.policy_number = policy_number; }
    }
} 