package HealthFirstBackend.HealthFirstProject.controller;

import HealthFirstBackend.HealthFirstProject.security.JwtRefreshUtil;
import HealthFirstBackend.HealthFirstProject.security.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/provider")
public class ProviderTokenController {
    @Autowired
    private JwtRefreshUtil jwtRefreshUtil;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");
        if (refreshToken == null || !jwtRefreshUtil.validateRefreshToken(refreshToken)) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Invalid or expired refresh token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
        Claims claims = jwtRefreshUtil.getClaims(refreshToken);
        String providerId = claims.get("providerId", String.class);
        String email = claims.get("email", String.class);
        String newAccessToken = jwtUtil.generateToken(providerId, email,"PROVIDER");
        Map<String, Object> response = new HashMap<>();
        response.put("token", newAccessToken);
        response.put("success", true);
        return ResponseEntity.ok(response);
    }
} 