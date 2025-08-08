package HealthFirstBackend.HealthFirstProject.service;

import HealthFirstBackend.HealthFirstProject.dto.PatientRegistrationRequestDTO;
import HealthFirstBackend.HealthFirstProject.dto.PatientRegistrationResponseDTO;
import HealthFirstBackend.HealthFirstProject.model.*;
import HealthFirstBackend.HealthFirstProject.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
@Validated
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public PatientRegistrationResponseDTO registerPatient(@Valid PatientRegistrationRequestDTO dto) {
        // Uniqueness checks
        if (patientRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (patientRepository.findByPhoneNumber(dto.getPhone_number()).isPresent()) {
            throw new IllegalArgumentException("Phone number already exists");
        }
        // Password validation
        String password = dto.getPassword();
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Password must contain 8+ characters, uppercase, lowercase, number, special character");
        }
        if (!password.equals(dto.getConfirm_password())) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        // Age validation
        LocalDate dob = dto.getDate_of_birth();
        if (dob == null || Period.between(dob, LocalDate.now()).getYears() < 13) {
            throw new IllegalArgumentException("Patient must be at least 13 years old");
        }
        // Gender validation
        Patient.Gender gender;
        try {
            gender = Patient.Gender.valueOf(dto.getGender().toUpperCase().replace("_", ""));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid gender");
        }
        // Map DTO to entity
        Patient patient = new Patient();
        patient.setFirstName(dto.getFirst_name());
        patient.setLastName(dto.getLast_name());
        patient.setEmail(dto.getEmail());
        patient.setPhoneNumber(dto.getPhone_number());
        patient.setPasswordHash(passwordEncoder.encode(password));
        patient.setDateOfBirth(dob);
        patient.setGender(gender);
        // Address
        Address address = new Address();
        address.setStreet(dto.getAddress().getStreet());
        address.setCity(dto.getAddress().getCity());
        address.setState(dto.getAddress().getState());
        address.setZip(dto.getAddress().getZip());
        patient.setAddress(address);
        // Emergency Contact
        if (dto.getEmergency_contact() != null) {
            EmergencyContact ec = new EmergencyContact();
            ec.setName(dto.getEmergency_contact().getName());
            ec.setPhone(dto.getEmergency_contact().getPhone());
            ec.setRelationship(dto.getEmergency_contact().getRelationship());
            patient.setEmergencyContact(ec);
        }
        // Medical History
        patient.setMedicalHistory(dto.getMedical_history());
        // Insurance Info
        if (dto.getInsurance_info() != null) {
            InsuranceInfo ins = new InsuranceInfo();
            ins.setProvider(dto.getInsurance_info().getProvider());
            ins.setPolicyNumber(dto.getInsurance_info().getPolicy_number());
            patient.setInsuranceInfo(ins);
        }
        patient.setEmailVerified(false);
        patient.setPhoneVerified(false);
        patient.setActive(true);
        patientRepository.save(patient);
        // Prepare response
        PatientRegistrationResponseDTO response = new PatientRegistrationResponseDTO();
        response.setSuccess(true);
        response.setMessage("Patient registered successfully. Verification email sent.");
        PatientRegistrationResponseDTO.Data data = new PatientRegistrationResponseDTO.Data();
        data.setPatient_id(patient.getId().toString());
        data.setEmail(patient.getEmail());
        data.setPhone_number(patient.getPhoneNumber());
        data.setEmail_verified(patient.isEmailVerified());
        data.setPhone_verified(patient.isPhoneVerified());
        response.setData(data);
        return response;
    }

    private boolean isValidPassword(String password) {
        return password != null && password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }
} 