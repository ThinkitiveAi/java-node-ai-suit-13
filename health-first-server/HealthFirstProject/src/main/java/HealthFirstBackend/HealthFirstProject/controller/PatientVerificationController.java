package HealthFirstBackend.HealthFirstProject.controller;

import HealthFirstBackend.HealthFirstProject.model.PatientVerificationToken;
import HealthFirstBackend.HealthFirstProject.service.PatientVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/patient")
public class PatientVerificationController {
    @Autowired
    private PatientVerificationService verificationService;

    @GetMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestParam("token") String token) {
        boolean verified = verificationService.verifyToken(token, PatientVerificationToken.Type.EMAIL);
        if (verified) {
            return ResponseEntity.ok(Map.of("success", true, "message", "Email verified successfully."));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "Invalid or expired verification token."));
        }
    }

    @GetMapping("/verify-phone")
    public ResponseEntity<?> verifyPhone(@RequestParam("token") String token) {
        boolean verified = verificationService.verifyToken(token, PatientVerificationToken.Type.PHONE);
        if (verified) {
            return ResponseEntity.ok(Map.of("success", true, "message", "Phone number verified successfully."));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "Invalid or expired verification token."));
        }
    }
} 