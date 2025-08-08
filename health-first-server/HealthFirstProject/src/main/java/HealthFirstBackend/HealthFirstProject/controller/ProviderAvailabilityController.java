package HealthFirstBackend.HealthFirstProject.controller;

import HealthFirstBackend.HealthFirstProject.dto.ProviderAvailabilityRequestDTO;
import HealthFirstBackend.HealthFirstProject.dto.ProviderAvailabilityResponseDTO;
import HealthFirstBackend.HealthFirstProject.service.ProviderAvailabilityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/provider")
public class ProviderAvailabilityController {
    @Autowired
    private ProviderAvailabilityService availabilityService;

    @PostMapping("/availability")
    public ResponseEntity<?> createAvailability(@Valid @RequestBody ProviderAvailabilityRequestDTO request) {
        try {
            // TODO: Get providerId from authenticated user context
            UUID providerId = UUID.randomUUID(); // Placeholder
            ProviderAvailabilityResponseDTO response = availabilityService.createAvailability(providerId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            if (e.getMessage().toLowerCase().contains("conflicts")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
            }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("errors", errors);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    }
} 