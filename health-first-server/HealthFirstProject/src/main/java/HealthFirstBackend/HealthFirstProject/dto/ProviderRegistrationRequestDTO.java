package HealthFirstBackend.HealthFirstProject.dto;

import jakarta.validation.constraints.*;

public class ProviderRegistrationRequestDTO {
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

    @NotBlank
    @Size(min = 3, max = 100)
    private String specialization;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "License number must be alphanumeric")
    private String license_number;

    @Min(0)
    @Max(50)
    private Integer years_of_experience;

    @NotNull
    private ClinicAddressDTO clinic_address;

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
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public String getLicense_number() { return license_number; }
    public void setLicense_number(String license_number) { this.license_number = license_number; }
    public Integer getYears_of_experience() { return years_of_experience; }
    public void setYears_of_experience(Integer years_of_experience) { this.years_of_experience = years_of_experience; }
    public ClinicAddressDTO getClinic_address() { return clinic_address; }
    public void setClinic_address(ClinicAddressDTO clinic_address) { this.clinic_address = clinic_address; }

    public static class ClinicAddressDTO {
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
} 