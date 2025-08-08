package HealthFirstBackend.HealthFirstProject.service;

import HealthFirstBackend.HealthFirstProject.dto.ProviderLoginRequestDTO;
import HealthFirstBackend.HealthFirstProject.dto.ProviderLoginResponseDTO;
import HealthFirstBackend.HealthFirstProject.model.Provider;
import HealthFirstBackend.HealthFirstProject.repository.ProviderRepository;
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

class ProviderAuthServiceTest {
    @Mock
    private ProviderRepository providerRepository;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    private ProviderAuthService providerAuthService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Provider getVerifiedProvider() {
        Provider provider = new Provider();
        provider.setId(java.util.UUID.randomUUID());
        provider.setEmail("john.doe@clinic.com");
        provider.setPasswordHash("hashed");
        provider.setActive(true);
        provider.setVerificationStatus(Provider.VerificationStatus.VERIFIED);
        return provider;
    }

    @Test
    void testLogin_Success() {
        ProviderLoginRequestDTO dto = new ProviderLoginRequestDTO();
        dto.setEmail("john.doe@clinic.com");
        dto.setPassword("SecurePassword123!");
        Provider provider = getVerifiedProvider();
        when(providerRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(provider));
        when(passwordEncoder.matches(dto.getPassword(), provider.getPasswordHash())).thenReturn(true);
        when(jwtUtil.generateToken(anyString(), anyString(),anyString())).thenReturn("jwt-token");
        ProviderLoginResponseDTO response = providerAuthService.login(dto);
        assertEquals("jwt-token", response.getToken());
        assertEquals(provider.getEmail(), response.getEmail());
        assertEquals("verified", response.getVerificationStatus());
    }

    @Test
    void testLogin_InvalidCredentials() {
        ProviderLoginRequestDTO dto = new ProviderLoginRequestDTO();
        dto.setEmail("john.doe@clinic.com");
        dto.setPassword("wrong");
        when(providerRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(getVerifiedProvider()));
        when(passwordEncoder.matches(dto.getPassword(), "hashed")).thenReturn(false);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> providerAuthService.login(dto));
        assertEquals("Invalid email or password", ex.getMessage());
    }

    @Test
    void testLogin_UnverifiedProvider() {
        ProviderLoginRequestDTO dto = new ProviderLoginRequestDTO();
        dto.setEmail("john.doe@clinic.com");
        dto.setPassword("SecurePassword123!");
        Provider provider = getVerifiedProvider();
        provider.setVerificationStatus(Provider.VerificationStatus.PENDING);
        when(providerRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(provider));
        Exception ex = assertThrows(IllegalArgumentException.class, () -> providerAuthService.login(dto));
        assertEquals("Provider account is not verified or is inactive", ex.getMessage());
    }

    @Test
    void testLogin_InactiveProvider() {
        ProviderLoginRequestDTO dto = new ProviderLoginRequestDTO();
        dto.setEmail("john.doe@clinic.com");
        dto.setPassword("SecurePassword123!");
        Provider provider = getVerifiedProvider();
        provider.setActive(false);
        when(providerRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(provider));
        Exception ex = assertThrows(IllegalArgumentException.class, () -> providerAuthService.login(dto));
        assertEquals("Provider account is not verified or is inactive", ex.getMessage());
    }
} 