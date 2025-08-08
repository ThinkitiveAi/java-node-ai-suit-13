package HealthFirstBackend.HealthFirstProject.service;

import HealthFirstBackend.HealthFirstProject.dto.PatientRegistrationRequestDTO;
import HealthFirstBackend.HealthFirstProject.dto.PatientRegistrationRequestDTO.AddressDTO;
import HealthFirstBackend.HealthFirstProject.dto.PatientRegistrationResponseDTO;
import HealthFirstBackend.HealthFirstProject.model.Patient;
import HealthFirstBackend.HealthFirstProject.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientServiceTest {
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private PatientRegistrationRequestDTO getValidRequest() {
        PatientRegistrationRequestDTO dto = new PatientRegistrationRequestDTO();
        dto.setFirst_name("Jane");
        dto.setLast_name("Smith");
        dto.setEmail("jane.smith@email.com");
        dto.setPhone_number("+1234567890");
        dto.setPassword("SecurePassword123!");
        dto.setConfirm_password("SecurePassword123!");
        dto.setDate_of_birth(LocalDate.of(1990, 5, 15));
        dto.setGender("female");
        AddressDTO address = new AddressDTO();
        address.setStreet("456 Main Street");
        address.setCity("Boston");
        address.setState("MA");
        address.setZip("02101");
        dto.setAddress(address);
        return dto;
    }

    @Test
    void testRegisterPatient_Success() {
        PatientRegistrationRequestDTO dto = getValidRequest();
        when(patientRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(patientRepository.findByPhoneNumber(dto.getPhone_number())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("hashed");
        PatientRegistrationResponseDTO response = patientService.registerPatient(dto);
        assertTrue(response.isSuccess());
        assertEquals("Patient registered successfully. Verification email sent.", response.getMessage());
        assertEquals(dto.getEmail(), response.getData().getEmail());
        assertEquals(dto.getPhone_number(), response.getData().getPhone_number());
        assertFalse(response.getData().isEmail_verified());
        assertFalse(response.getData().isPhone_verified());
    }

    @Test
    void testRegisterPatient_DuplicateEmail() {
        PatientRegistrationRequestDTO dto = getValidRequest();
        when(patientRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(new Patient()));
        Exception ex = assertThrows(IllegalArgumentException.class, () -> patientService.registerPatient(dto));
        assertEquals("Email already exists", ex.getMessage());
    }

    @Test
    void testRegisterPatient_DuplicatePhone() {
        PatientRegistrationRequestDTO dto = getValidRequest();
        when(patientRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(patientRepository.findByPhoneNumber(dto.getPhone_number())).thenReturn(Optional.of(new Patient()));
        Exception ex = assertThrows(IllegalArgumentException.class, () -> patientService.registerPatient(dto));
        assertEquals("Phone number already exists", ex.getMessage());
    }

    @Test
    void testRegisterPatient_InvalidPassword() {
        PatientRegistrationRequestDTO dto = getValidRequest();
        dto.setPassword("password");
        dto.setConfirm_password("password");
        when(patientRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(patientRepository.findByPhoneNumber(dto.getPhone_number())).thenReturn(Optional.empty());
        Exception ex = assertThrows(IllegalArgumentException.class, () -> patientService.registerPatient(dto));
        assertEquals("Password must contain 8+ characters, uppercase, lowercase, number, special character", ex.getMessage());
    }

    @Test
    void testRegisterPatient_PasswordsDoNotMatch() {
        PatientRegistrationRequestDTO dto = getValidRequest();
        dto.setConfirm_password("DifferentPassword123!");
        when(patientRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(patientRepository.findByPhoneNumber(dto.getPhone_number())).thenReturn(Optional.empty());
        Exception ex = assertThrows(IllegalArgumentException.class, () -> patientService.registerPatient(dto));
        assertEquals("Passwords do not match", ex.getMessage());
    }

    @Test
    void testRegisterPatient_InvalidAge() {
        PatientRegistrationRequestDTO dto = getValidRequest();
        dto.setDate_of_birth(LocalDate.now().minusYears(10));
        when(patientRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(patientRepository.findByPhoneNumber(dto.getPhone_number())).thenReturn(Optional.empty());
        Exception ex = assertThrows(IllegalArgumentException.class, () -> patientService.registerPatient(dto));
        assertEquals("Patient must be at least 13 years old", ex.getMessage());
    }

    @Test
    void testRegisterPatient_InvalidGender() {
        PatientRegistrationRequestDTO dto = getValidRequest();
        dto.setGender("invalid");
        when(patientRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(patientRepository.findByPhoneNumber(dto.getPhone_number())).thenReturn(Optional.empty());
        Exception ex = assertThrows(IllegalArgumentException.class, () -> patientService.registerPatient(dto));
        assertEquals("Invalid gender", ex.getMessage());
    }

    @Test
    void testPasswordHashing() {
        PatientRegistrationRequestDTO dto = getValidRequest();
        when(patientRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(patientRepository.findByPhoneNumber(dto.getPhone_number())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("hashed");
        PatientRegistrationResponseDTO response = patientService.registerPatient(dto);
        // Password should not be returned in response
        assertNull(response.getData().getClass().getDeclaredFields(), "No sensitive fields in response DTO");
    }
} 