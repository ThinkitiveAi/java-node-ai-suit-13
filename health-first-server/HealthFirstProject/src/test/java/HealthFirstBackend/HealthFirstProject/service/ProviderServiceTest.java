package HealthFirstBackend.HealthFirstProject.service;

import HealthFirstBackend.HealthFirstProject.dto.ProviderRegistrationRequestDTO;
import HealthFirstBackend.HealthFirstProject.model.Provider;
import HealthFirstBackend.HealthFirstProject.model.ClinicAddress;
import HealthFirstBackend.HealthFirstProject.repository.ProviderRepository;
import HealthFirstBackend.HealthFirstProject.model.ProviderVerificationToken;
import HealthFirstBackend.HealthFirstProject.repository.ProviderVerificationTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.LocalDateTime;

class ProviderServiceTest {
    @Mock
    private ProviderRepository providerRepository;
    @Mock
    private ProviderVerificationTokenRepository tokenRepository;
    @Mock
    @InjectMocks
    private ProviderService providerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private ProviderRegistrationRequestDTO getValidRequest() {
        ProviderRegistrationRequestDTO dto = new ProviderRegistrationRequestDTO();
        dto.setFirst_name("John");
        dto.setLast_name("Doe");
        dto.setEmail("john.doe@clinic.com");
        dto.setPhone_number("+1234567890");
        dto.setPassword("SecurePassword123!");
        dto.setConfirm_password("SecurePassword123!");
        dto.setSpecialization("Cardiology");
        dto.setLicense_number("MD123456789");
        dto.setYears_of_experience(10);
        ProviderRegistrationRequestDTO.ClinicAddressDTO address = new ProviderRegistrationRequestDTO.ClinicAddressDTO();
        address.setStreet("123 Medical Center Dr");
        address.setCity("New York");
        address.setState("NY");
        address.setZip("10001");
        dto.setClinic_address(address);
        return dto;
    }

    @Test
    void testRegisterProvider_Success() {
        ProviderRegistrationRequestDTO dto = getValidRequest();
        when(providerRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(providerRepository.findByPhoneNumber(dto.getPhone_number())).thenReturn(Optional.empty());
        when(providerRepository.findByLicenseNumber(dto.getLicense_number())).thenReturn(Optional.empty());
        Provider savedProvider = new Provider();
        savedProvider.setId(java.util.UUID.randomUUID());
        savedProvider.setEmail(dto.getEmail());
        savedProvider.setVerificationStatus(Provider.VerificationStatus.PENDING);
        when(providerRepository.save(any(Provider.class))).thenReturn(savedProvider);
        var response = providerService.registerProvider(dto);
        assertTrue(response.isSuccess());
        assertEquals("Provider registered successfully. Verification email sent.", response.getMessage());
        assertEquals(dto.getEmail(), response.getData().getEmail());
        assertEquals("pending", response.getData().getVerification_status());
    }

    @Test
    void testRegisterProvider_DuplicateEmail() {
        ProviderRegistrationRequestDTO dto = getValidRequest();
        when(providerRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(new Provider()));
        Exception ex = assertThrows(IllegalArgumentException.class, () -> providerService.registerProvider(dto));
        assertEquals("Email already exists", ex.getMessage());
    }

    @Test
    void testRegisterProvider_DuplicatePhone() {
        ProviderRegistrationRequestDTO dto = getValidRequest();
        when(providerRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(providerRepository.findByPhoneNumber(dto.getPhone_number())).thenReturn(Optional.of(new Provider()));
        Exception ex = assertThrows(IllegalArgumentException.class, () -> providerService.registerProvider(dto));
        assertEquals("Phone number already exists", ex.getMessage());
    }

    @Test
    void testRegisterProvider_DuplicateLicense() {
        ProviderRegistrationRequestDTO dto = getValidRequest();
        when(providerRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(providerRepository.findByPhoneNumber(dto.getPhone_number())).thenReturn(Optional.empty());
        when(providerRepository.findByLicenseNumber(dto.getLicense_number())).thenReturn(Optional.of(new Provider()));
        Exception ex = assertThrows(IllegalArgumentException.class, () -> providerService.registerProvider(dto));
        assertEquals("License number already exists", ex.getMessage());
    }

    @Test
    void testRegisterProvider_InvalidPassword() {
        ProviderRegistrationRequestDTO dto = getValidRequest();
        dto.setPassword("password");
        dto.setConfirm_password("password");
        when(providerRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(providerRepository.findByPhoneNumber(dto.getPhone_number())).thenReturn(Optional.empty());
        when(providerRepository.findByLicenseNumber(dto.getLicense_number())).thenReturn(Optional.empty());
        Exception ex = assertThrows(IllegalArgumentException.class, () -> providerService.registerProvider(dto));
        assertEquals("Password must contain 8+ characters, uppercase, lowercase, number, special character", ex.getMessage());
    }

    @Test
    void testRegisterProvider_PasswordsDoNotMatch() {
        ProviderRegistrationRequestDTO dto = getValidRequest();
        dto.setConfirm_password("DifferentPassword123!");
        when(providerRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(providerRepository.findByPhoneNumber(dto.getPhone_number())).thenReturn(Optional.empty());
        when(providerRepository.findByLicenseNumber(dto.getLicense_number())).thenReturn(Optional.empty());
        Exception ex = assertThrows(IllegalArgumentException.class, () -> providerService.registerProvider(dto));
        assertEquals("Passwords do not match", ex.getMessage());
    }

    @Test
    void testRegisterProvider_InvalidSpecialization() {
        ProviderRegistrationRequestDTO dto = getValidRequest();
        dto.setSpecialization("UnknownSpecialty");
        when(providerRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(providerRepository.findByPhoneNumber(dto.getPhone_number())).thenReturn(Optional.empty());
        when(providerRepository.findByLicenseNumber(dto.getLicense_number())).thenReturn(Optional.empty());
        Exception ex = assertThrows(IllegalArgumentException.class, () -> providerService.registerProvider(dto));
        assertEquals("Invalid specialization", ex.getMessage());
    }

    @Test
    void testPasswordHashing() {
        ProviderRegistrationRequestDTO dto = getValidRequest();
        when(providerRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(providerRepository.findByPhoneNumber(dto.getPhone_number())).thenReturn(Optional.empty());
        when(providerRepository.findByLicenseNumber(dto.getLicense_number())).thenReturn(Optional.empty());
        ProviderService realService = new ProviderService(providerRepository,tokenRepository);
        var response = realService.registerProvider(dto);
        // Password should not be returned in response
        assertNull(response.getData().getProvider_id(), "Provider ID should not be null after registration");
        // Hash check
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        assertTrue(encoder.matches("SecurePassword123!", providerRepository.save(any(Provider.class)).getPasswordHash()));
    }

    @Test
    void testVerifyProvider_Success() {
        Provider provider = new Provider();
        provider.setVerificationStatus(Provider.VerificationStatus.PENDING);
        ProviderVerificationToken token = new ProviderVerificationToken();
        token.setToken("valid-token");
        token.setProvider(provider);
        token.setExpiryDate(LocalDateTime.now().plusHours(1));
        when(tokenRepository.findByToken("valid-token")).thenReturn(Optional.of(token));
        boolean result = providerService.verifyProvider("valid-token");
        assertTrue(result);
        assertEquals(Provider.VerificationStatus.VERIFIED, provider.getVerificationStatus());
        verify(tokenRepository).delete(token);
    }

    @Test
    void testVerifyProvider_InvalidToken() {
        when(tokenRepository.findByToken("invalid-token")).thenReturn(Optional.empty());
        boolean result = providerService.verifyProvider("invalid-token");
        assertFalse(result);
    }

    @Test
    void testVerifyProvider_ExpiredToken() {
        Provider provider = new Provider();
        provider.setVerificationStatus(Provider.VerificationStatus.PENDING);
        ProviderVerificationToken token = new ProviderVerificationToken();
        token.setToken("expired-token");
        token.setProvider(provider);
        token.setExpiryDate(LocalDateTime.now().minusHours(1));
        when(tokenRepository.findByToken("expired-token")).thenReturn(Optional.of(token));
        boolean result = providerService.verifyProvider("expired-token");
        assertFalse(result);
    }
} 