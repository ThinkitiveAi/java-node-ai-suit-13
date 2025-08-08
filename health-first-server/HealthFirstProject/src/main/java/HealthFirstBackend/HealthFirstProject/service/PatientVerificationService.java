package HealthFirstBackend.HealthFirstProject.service;

import HealthFirstBackend.HealthFirstProject.model.Patient;
import HealthFirstBackend.HealthFirstProject.model.PatientVerificationToken;
import HealthFirstBackend.HealthFirstProject.repository.PatientVerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PatientVerificationService {
    @Autowired
    private PatientVerificationTokenRepository tokenRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Value("${server.port:8080}")
    private int serverPort;
    @Value("${server.host:localhost}")
    private String serverHost;

    public void sendEmailVerification(Patient patient) {
        String token = UUID.randomUUID().toString();
        PatientVerificationToken verificationToken = new PatientVerificationToken();
        verificationToken.setPatient(patient);
        verificationToken.setToken(token);
        verificationToken.setType(PatientVerificationToken.Type.EMAIL);
        verificationToken.setExpiryDate(LocalDateTime.now().plusDays(1));
        tokenRepository.save(verificationToken);
        String verificationUrl = String.format("http://%s:%d/api/v1/patient/verify-email?token=%s", serverHost, serverPort, token);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(patient.getEmail());
        message.setSubject("Verify your Patient Account");
        message.setText("Please verify your email by clicking the following link: " + verificationUrl);
        mailSender.send(message);
    }

    public void sendPhoneVerification(Patient patient) {
        String token = UUID.randomUUID().toString();
        PatientVerificationToken verificationToken = new PatientVerificationToken();
        verificationToken.setPatient(patient);
        verificationToken.setToken(token);
        verificationToken.setType(PatientVerificationToken.Type.PHONE);
        verificationToken.setExpiryDate(LocalDateTime.now().plusMinutes(10));
        tokenRepository.save(verificationToken);
        // Stub: Integrate with SMS provider here
        System.out.println("Send SMS to " + patient.getPhoneNumber() + " with code: " + token);
    }

    public boolean verifyToken(String token, PatientVerificationToken.Type type) {
        var verificationToken = tokenRepository.findByToken(token).orElse(null);
        if (verificationToken == null || verificationToken.getType() != type || verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return false;
        }
        Patient patient = verificationToken.getPatient();
        if (type == PatientVerificationToken.Type.EMAIL) {
            patient.setEmailVerified(true);
        } else if (type == PatientVerificationToken.Type.PHONE) {
            patient.setPhoneVerified(true);
        }
        tokenRepository.delete(verificationToken);
        return true;
    }
} 