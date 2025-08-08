package HealthFirstBackend.HealthFirstProject.service;

import HealthFirstBackend.HealthFirstProject.dto.ProviderLoginRequestDTO;
import HealthFirstBackend.HealthFirstProject.dto.ProviderLoginResponseDTO;
import HealthFirstBackend.HealthFirstProject.model.Provider;
import HealthFirstBackend.HealthFirstProject.repository.ProviderRepository;
import HealthFirstBackend.HealthFirstProject.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProviderAuthService {
    @Autowired
    private ProviderRepository providerRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public ProviderLoginResponseDTO login(ProviderLoginRequestDTO dto) {
        Provider provider = providerRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        if (!provider.isActive() || provider.getVerificationStatus() != Provider.VerificationStatus.VERIFIED) {
            throw new IllegalArgumentException("Provider account is not verified or is inactive");
        }
        if (!passwordEncoder.matches(dto.getPassword(), provider.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        String token = jwtUtil.generateToken(provider.getId().toString(), provider.getEmail(),"PATIENT");
        ProviderLoginResponseDTO response = new ProviderLoginResponseDTO();
        response.setToken(token);
        response.setProviderId(provider.getId().toString());
        response.setEmail(provider.getEmail());
        response.setVerificationStatus(provider.getVerificationStatus().name().toLowerCase());
        return response;
    }
} 