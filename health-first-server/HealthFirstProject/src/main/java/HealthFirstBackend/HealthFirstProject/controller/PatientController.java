package HealthFirstBackend.HealthFirstProject.controller;

import HealthFirstBackend.HealthFirstProject.dto.PatientRegistrationRequestDTO;
import HealthFirstBackend.HealthFirstProject.dto.PatientRegistrationResponseDTO;
import HealthFirstBackend.HealthFirstProject.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/patient")
@Tag(name = "Patient Management", description = "APIs for patient registration and management")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @PostMapping("/register")
    @Operation(summary = "Register a new patient", description = "Creates a new patient account with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Patient registered successfully",
            content = @Content(schema = @Schema(implementation = PatientRegistrationResponseDTO.class))),
        @ApiResponse(responseCode = "409", description = "Patient already exists",
            content = @Content(schema = @Schema(implementation = Map.class))),
        @ApiResponse(responseCode = "422", description = "Invalid input data",
            content = @Content(schema = @Schema(implementation = Map.class)))
    })
    public ResponseEntity<?> registerPatient(@Valid @RequestBody PatientRegistrationRequestDTO request) {
        try {
            PatientRegistrationResponseDTO response = patientService.registerPatient(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            if (e.getMessage().toLowerCase().contains("exists")) {
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