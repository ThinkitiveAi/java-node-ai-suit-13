package HealthFirstBackend.HealthFirstProject.controller;

import HealthFirstBackend.HealthFirstProject.dto.ProviderAvailabilityRequestDTO;
import HealthFirstBackend.HealthFirstProject.dto.ProviderAvailabilityResponseDTO;
import HealthFirstBackend.HealthFirstProject.dto.ProviderAvailabilityListResponseDTO;
import HealthFirstBackend.HealthFirstProject.service.ProviderAvailabilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/provider")
@Tag(name = "Provider Availability", description = "APIs for managing provider availability and slots")
public class ProviderAvailabilityController {
    @Autowired
    private ProviderAvailabilityService availabilityService;

    @PostMapping("/availability")
    @Operation(summary = "Create provider availability", description = "Creates new availability slots for a provider")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Availability created successfully",
            content = @Content(schema = @Schema(implementation = ProviderAvailabilityResponseDTO.class))),
        @ApiResponse(responseCode = "409", description = "Time slot conflicts with existing availability",
            content = @Content(schema = @Schema(implementation = Map.class))),
        @ApiResponse(responseCode = "422", description = "Invalid input data",
            content = @Content(schema = @Schema(implementation = Map.class)))
    })
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

    @GetMapping("/availability/{providerId}")
    @Operation(summary = "Get provider availability slots", description = "Retrieves all availability slots for a specific provider")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Availability slots retrieved successfully",
            content = @Content(schema = @Schema(implementation = ProviderAvailabilityListResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "No availability slots found for provider",
            content = @Content(schema = @Schema(implementation = Map.class))),
        @ApiResponse(responseCode = "400", description = "Invalid provider ID",
            content = @Content(schema = @Schema(implementation = Map.class)))
    })
    public ResponseEntity<?> getAvailabilitySlots(
            @Parameter(description = "Provider ID", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable String providerId) {
        try {
            UUID providerUUID = UUID.fromString(providerId);
            ProviderAvailabilityListResponseDTO response = availabilityService.getAvailabilitySlots(providerUUID);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            if (e.getMessage().toLowerCase().contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Invalid provider ID format");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
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