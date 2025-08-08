package HealthFirstBackend.HealthFirstProject.controller;

import HealthFirstBackend.HealthFirstProject.dto.ProviderLoginRequestDTO;
import HealthFirstBackend.HealthFirstProject.dto.ProviderLoginResponseDTO;
import HealthFirstBackend.HealthFirstProject.service.ProviderAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/provider")
public class ProviderAuthController {
    @Autowired
    private ProviderAuthService providerAuthService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody ProviderLoginRequestDTO request) {
        try {
            ProviderLoginResponseDTO response = providerAuthService.login(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }
} 