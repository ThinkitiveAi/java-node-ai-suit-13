package HealthFirstBackend.HealthFirstProject.service;

import HealthFirstBackend.HealthFirstProject.dto.PatientLoginRequestDTO;
import HealthFirstBackend.HealthFirstProject.dto.PatientLoginResponseDTO;
import HealthFirstBackend.HealthFirstProject.model.Patient;
import HealthFirstBackend.HealthFirstProject.repository.PatientRepository;
import HealthFirstBackend.HealthFirstProject.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientAuthServiceTest {
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    private PatientAuthService patientAuthService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Patient getActiveVerifiedPatient() {
        Patient patient = new Patient();
        patient.setId(java.util.UUID.randomUUID());
        patient.setFirstName("Jane");
        patient.setLastName("Smith");
        patient.setEmail("jane.smith@email.com");
        patient.setPhoneNumber("+1234567890");
        patient.setPasswordHash("hashed");
        patient.setActive(true);
        patient.setEmailVerified(true);
        patient.setPhoneVerified(true);
        return patient;
    }

    @Test
    void testLogin_Success_Email() {
        PatientLoginRequestDTO dto = new PatientLoginRequestDTO();
        dto.setEmail("jane.smith@email.com");
        dto.setPassword("SecurePassword123!");
        Patient patient = getActiveVerifiedPatient();
        when(patientRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(patient));
        when(passwordEncoder.matches(dto.getPassword(), patient.getPasswordHash())).thenReturn(true);
        when(jwtUtil.generateToken(anyString(), anyString(), anyString())).thenReturn("jwt-token");
        PatientLoginResponseDTO response = patientAuthService.login(dto);
        assertTrue(response.isSuccess());
        assertEquals("Login successful", response.getMessage());
        assertEquals("jwt-token", response.getData().getAccess_token());
        assertEquals("Bearer", response.getData().getToken_type());
        assertEquals(patient.getEmail(), response.getData().getPatient().getEmail());
    }

    @Test
    void testLogin_Success_Phone() {
        PatientLoginRequestDTO dto = new PatientLoginRequestDTO();
        dto.setPhoneNumber("+1234567890");
        dto.setPassword("SecurePassword123!");
        Patient patient = getActiveVerifiedPatient();
        when(patientRepository.findByPhoneNumber(dto.getPhoneNumber())).thenReturn(Optional.of(patient));
        when(passwordEncoder.matches(dto.getPassword(), patient.getPasswordHash())).thenReturn(true);
        when(jwtUtil.generateToken(anyString(), anyString(), anyString())).thenReturn("jwt-token");
        PatientLoginResponseDTO response = patientAuthService.login(dto);
        assertTrue(response.isSuccess());
        assertEquals("Login successful", response.getMessage());
        assertEquals("jwt-token", response.getData().getAccess_token());
        assertEquals("Bearer", response.getData().getToken_type());
        assertEquals(patient.getPhoneNumber(), response.getData().getPatient().getPhone_number());
    }

    @Test
    void testLogin_InvalidCredentials() {
        PatientLoginRequestDTO dto = new PatientLoginRequestDTO();
        dto.setEmail("jane.smith@email.com");
        dto.setPassword("wrong");
        when(patientRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(getActiveVerifiedPatient()));
        when(passwordEncoder.matches(dto.getPassword(), "hashed")).thenReturn(false);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> patientAuthService.login(dto));
        assertEquals("Invalid email/phone or password", ex.getMessage());
    }

    @Test
    void testLogin_InactivePatient() {
        PatientLoginRequestDTO dto = new PatientLoginRequestDTO();
        dto.setEmail("jane.smith@email.com");
        dto.setPassword("SecurePassword123!");
        Patient patient = getActiveVerifiedPatient();
        patient.setActive(false);
        when(patientRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(patient));
        Exception ex = assertThrows(IllegalArgumentException.class, () -> patientAuthService.login(dto));
        assertEquals("Patient account is not verified or is inactive", ex.getMessage());
    }

    @Test
    void testLogin_UnverifiedPatient() {
        PatientLoginRequestDTO dto = new PatientLoginRequestDTO();
        dto.setEmail("jane.smith@email.com");
        dto.setPassword("SecurePassword123!");
        Patient patient = getActiveVerifiedPatient();
        patient.setEmailVerified(false);
        when(patientRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(patient));
        Exception ex = assertThrows(IllegalArgumentException.class, () -> patientAuthService.login(dto));
        assertEquals("Patient account is not verified or is inactive", ex.getMessage());
    }
} 