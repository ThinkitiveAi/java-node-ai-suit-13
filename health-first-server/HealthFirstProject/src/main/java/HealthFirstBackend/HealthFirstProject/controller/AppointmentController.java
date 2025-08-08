package HealthFirstBackend.HealthFirstProject.controller;

import HealthFirstBackend.HealthFirstProject.dto.BookAppointmentRequestDTO;
import HealthFirstBackend.HealthFirstProject.dto.BookAppointmentResponseDTO;
import HealthFirstBackend.HealthFirstProject.dto.GetAppointmentsResponseDTO;
import HealthFirstBackend.HealthFirstProject.service.AppointmentService;
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
@RequestMapping("/api/v1/appointments")
@Tag(name = "Appointments", description = "APIs for booking and managing appointments")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/book")
    @Operation(summary = "Book an appointment", description = "Books an appointment with a provider after checking availability")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Appointment booked successfully",
            content = @Content(schema = @Schema(implementation = BookAppointmentResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid request or provider/patient not found",
            content = @Content(schema = @Schema(implementation = Map.class))),
        @ApiResponse(responseCode = "409", description = "No available slots for requested time",
            content = @Content(schema = @Schema(implementation = Map.class))),
        @ApiResponse(responseCode = "422", description = "Invalid input data",
            content = @Content(schema = @Schema(implementation = Map.class)))
    })
    public ResponseEntity<?> bookAppointment(@Valid @RequestBody BookAppointmentRequestDTO request) {
        try {
            BookAppointmentResponseDTO response = appointmentService.bookAppointment(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            
            if (e.getMessage().toLowerCase().contains("not found")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            } else if (e.getMessage().toLowerCase().contains("no available slots") || 
                       e.getMessage().toLowerCase().contains("no longer available")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
            } else if (e.getMessage().toLowerCase().contains("future")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
        }
    }

    @GetMapping("/provider/{providerId}")
    @Operation(summary = "Get appointments for provider", description = "Retrieves all appointments for a specific provider")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Appointments retrieved successfully",
            content = @Content(schema = @Schema(implementation = GetAppointmentsResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid provider ID",
            content = @Content(schema = @Schema(implementation = Map.class))),
        @ApiResponse(responseCode = "404", description = "Provider not found",
            content = @Content(schema = @Schema(implementation = Map.class)))
    })
    public ResponseEntity<?> getAppointmentsForProvider(
            @Parameter(description = "Provider ID", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable String providerId) {
        try {
            UUID providerUUID = UUID.fromString(providerId);
            GetAppointmentsResponseDTO response = appointmentService.getAppointmentsForProvider(providerUUID);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Invalid provider ID format");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get appointments for patient", description = "Retrieves all appointments for a specific patient")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Appointments retrieved successfully",
            content = @Content(schema = @Schema(implementation = GetAppointmentsResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid patient ID",
            content = @Content(schema = @Schema(implementation = Map.class))),
        @ApiResponse(responseCode = "404", description = "Patient not found",
            content = @Content(schema = @Schema(implementation = Map.class)))
    })
    public ResponseEntity<?> getAppointmentsForPatient(
            @Parameter(description = "Patient ID", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable String patientId) {
        try {
            UUID patientUUID = UUID.fromString(patientId);
            GetAppointmentsResponseDTO response = appointmentService.getAppointmentsForPatient(patientUUID);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Invalid patient ID format");
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