package HealthFirstBackend.HealthFirstProject.service;

import HealthFirstBackend.HealthFirstProject.dto.PatientLoginRequestDTO;
import HealthFirstBackend.HealthFirstProject.dto.PatientLoginResponseDTO;
import HealthFirstBackend.HealthFirstProject.model.Patient;
import HealthFirstBackend.HealthFirstProject.repository.PatientRepository;
import HealthFirstBackend.HealthFirstProject.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PatientAuthService {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public PatientLoginResponseDTO login(PatientLoginRequestDTO dto) {
        Optional<Patient> patientOpt = Optional.empty();
        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            patientOpt = patientRepository.findByEmail(dto.getEmail());
        } else if (dto.getPhoneNumber() != null && !dto.getPhoneNumber().isBlank()) {
            patientOpt = patientRepository.findByPhoneNumber(dto.getPhoneNumber());
        }
        Patient patient = patientOpt.orElseThrow(() -> new IllegalArgumentException("Invalid email/phone or password"));
        if (!patient.isActive() || !patient.isEmailVerified()) {
            throw new IllegalArgumentException("Patient account is not verified or is inactive");
        }
        if (!passwordEncoder.matches(dto.getPassword(), patient.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid email/phone or password");
        }
        String token = jwtUtil.generateToken(patient.getId().toString(), patient.getEmail(), "PATIENT");
        PatientLoginResponseDTO response = new PatientLoginResponseDTO();
        response.setSuccess(true);
        response.setMessage("Login successful");
        PatientLoginResponseDTO.Data data = new PatientLoginResponseDTO.Data();
        data.setAccess_token(token);
        data.setExpires_in(1800);
        data.setToken_type("Bearer");
        PatientLoginResponseDTO.Patient safePatient = new PatientLoginResponseDTO.Patient();
        safePatient.setId(patient.getId().toString());
        safePatient.setFirst_name(patient.getFirstName());
        safePatient.setLast_name(patient.getLastName());
        safePatient.setEmail(patient.getEmail());
        safePatient.setPhone_number(patient.getPhoneNumber());
        safePatient.setEmail_verified(patient.isEmailVerified());
        safePatient.setPhone_verified(patient.isPhoneVerified());
        data.setPatient(safePatient);
        response.setData(data);
        return response;
    }
} 