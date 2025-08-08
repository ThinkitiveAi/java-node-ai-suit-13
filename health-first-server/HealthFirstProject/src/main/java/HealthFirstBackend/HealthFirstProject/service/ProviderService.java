package HealthFirstBackend.HealthFirstProject.service;

import HealthFirstBackend.HealthFirstProject.dto.ProviderRegistrationRequestDTO;
import HealthFirstBackend.HealthFirstProject.dto.ProviderRegistrationResponseDTO;
import HealthFirstBackend.HealthFirstProject.model.Provider;
import HealthFirstBackend.HealthFirstProject.model.ClinicAddress;
import HealthFirstBackend.HealthFirstProject.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import java.util.Set;
import java.util.UUID;
import java.time.LocalDateTime;
import HealthFirstBackend.HealthFirstProject.model.ProviderVerificationToken;
import HealthFirstBackend.HealthFirstProject.repository.ProviderVerificationTokenRepository;

@Service
@Validated
public class ProviderService {
    private static final Set<String> SPECIALIZATIONS = Set.of("Cardiology", "Dermatology", "Neurology", "Pediatrics", "General Medicine");
    private final ProviderRepository providerRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
    private final ProviderVerificationTokenRepository tokenRepository;
    @Value("${server.port:8080}")
    private int serverPort;
    @Value("${server.host:localhost}")
    private String serverHost;

    @Autowired
    public ProviderService(ProviderRepository providerRepository, ProviderVerificationTokenRepository tokenRepository) {
        this.providerRepository = providerRepository;
        this.tokenRepository = tokenRepository;
    }

    @Transactional
    public ProviderRegistrationResponseDTO registerProvider(@Valid ProviderRegistrationRequestDTO dto) {
        // Validate unique email
        if (providerRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        // Validate unique phone
        if (providerRepository.findByPhoneNumber(dto.getPhone_number()).isPresent()) {
            throw new IllegalArgumentException("Phone number already exists");
        }
        // Validate unique license
        if (providerRepository.findByLicenseNumber(dto.getLicense_number()).isPresent()) {
            throw new IllegalArgumentException("License number already exists");
        }
        // Validate password
        String password = dto.getPassword();
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Password must contain 8+ characters, uppercase, lowercase, number, special character");
        }
        if (!password.equals(dto.getConfirm_password())) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        // Validate specialization
        if (!SPECIALIZATIONS.contains(dto.getSpecialization())) {
            throw new IllegalArgumentException("Invalid specialization");
        }
        // Map DTO to entity
        Provider provider = new Provider();
        provider.setFirstName(dto.getFirst_name());
        provider.setLastName(dto.getLast_name());
        provider.setEmail(dto.getEmail());
        provider.setPhoneNumber(dto.getPhone_number());
        provider.setPasswordHash(passwordEncoder.encode(password));
        provider.setSpecialization(dto.getSpecialization());
        provider.setLicenseNumber(dto.getLicense_number());
        provider.setYearsOfExperience(dto.getYears_of_experience());
        ClinicAddress address = new ClinicAddress();
        address.setStreet(dto.getClinic_address().getStreet());
        address.setCity(dto.getClinic_address().getCity());
        address.setState(dto.getClinic_address().getState());
        address.setZip(dto.getClinic_address().getZip());
        provider.setClinicAddress(address);
        provider.setVerificationStatus(Provider.VerificationStatus.PENDING);
        provider.setActive(true);
        providerRepository.save(provider);
        // Generate verification token
        ProviderVerificationToken token = new ProviderVerificationToken();
        token.setProvider(provider);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(LocalDateTime.now().plusDays(1));
        tokenRepository.save(token);
        // Send verification email
        String verificationUrl = String.format("http://%s:%d/api/v1/provider/verify?token=%s", serverHost, serverPort, token.getToken());
        String emailText = "Please verify your email by clicking the following link: " + verificationUrl;
        // Prepare response
        ProviderRegistrationResponseDTO response = new ProviderRegistrationResponseDTO();
        response.setSuccess(true);
        response.setMessage("Provider registered successfully. Verification email sent.");
        ProviderRegistrationResponseDTO.Data data = new ProviderRegistrationResponseDTO.Data();
        data.setProvider_id(provider.getId().toString());
        data.setEmail(provider.getEmail());
        data.setVerification_status(provider.getVerificationStatus().name().toLowerCase());
        response.setData(data);
        return response;
    }

    private boolean isValidPassword(String password) {
        return password != null && password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }

    public boolean verifyProvider(String token) {
        ProviderVerificationToken verificationToken = tokenRepository.findByToken(token).orElse(null);
        if (verificationToken == null || verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return false;
        }
        Provider provider = verificationToken.getProvider();
        provider.setVerificationStatus(Provider.VerificationStatus.VERIFIED);
        providerRepository.save(provider);
        tokenRepository.delete(verificationToken);
        return true;
    }
} 