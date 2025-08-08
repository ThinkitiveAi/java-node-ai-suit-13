package HealthFirstBackend.HealthFirstProject.service;

import HealthFirstBackend.HealthFirstProject.dto.BookAppointmentRequestDTO;
import HealthFirstBackend.HealthFirstProject.dto.BookAppointmentResponseDTO;
import HealthFirstBackend.HealthFirstProject.dto.GetAppointmentsResponseDTO;
import HealthFirstBackend.HealthFirstProject.model.AppointmentSlot;
import HealthFirstBackend.HealthFirstProject.model.Patient;
import HealthFirstBackend.HealthFirstProject.model.Provider;
import HealthFirstBackend.HealthFirstProject.repository.AppointmentSlotRepository;
import HealthFirstBackend.HealthFirstProject.repository.PatientRepository;
import HealthFirstBackend.HealthFirstProject.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Validated
public class AppointmentService {
    @Autowired
    private AppointmentSlotRepository appointmentSlotRepository;
    
    @Autowired
    private PatientRepository patientRepository;
    
    @Autowired
    private ProviderRepository providerRepository;

    @Transactional
    public BookAppointmentResponseDTO bookAppointment(@Valid BookAppointmentRequestDTO request) {
        // Parse UUIDs
        UUID providerId = UUID.fromString(request.getProviderId());
        UUID patientId = UUID.fromString(request.getPatientId());
        
        // Validate that provider and patient exist
        Provider provider = providerRepository.findById(providerId)
            .orElseThrow(() -> new IllegalArgumentException("Provider not found"));
        
        Patient patient = patientRepository.findById(patientId)
            .orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        
        // Check if the requested time is in the future
        if (request.getAppointmentDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Appointment time must be in the future");
        }
        
        // Find available slot for the requested time
        List<AppointmentSlot> availableSlots = appointmentSlotRepository
            .findByProviderIdAndSlotStartTimeAndStatus(providerId, request.getAppointmentDateTime(), AppointmentSlot.Status.AVAILABLE);
        
        if (availableSlots.isEmpty()) {
            throw new IllegalArgumentException("No available slots found for the requested time");
        }
        
        // Get the first available slot
        AppointmentSlot slot = availableSlots.get(0);
        
        // Check if the slot is still available (double-check)
        if (slot.getStatus() != AppointmentSlot.Status.AVAILABLE) {
            throw new IllegalArgumentException("Selected slot is no longer available");
        }
        
        // Book the appointment
        slot.setStatus(AppointmentSlot.Status.BOOKED);
        slot.setPatientId(patientId);
        slot.setAppointmentType(request.getAppointmentType());
        slot.setBookingReference(generateBookingReference());
        
        appointmentSlotRepository.save(slot);
        
        // Prepare response
        BookAppointmentResponseDTO response = new BookAppointmentResponseDTO();
        response.setSuccess(true);
        response.setMessage("Appointment booked successfully");
        
        BookAppointmentResponseDTO.Data data = new BookAppointmentResponseDTO.Data();
        data.setAppointment_id(slot.getId().toString());
        data.setBooking_reference(slot.getBookingReference());
        data.setProvider_id(providerId.toString());
        data.setPatient_id(patientId.toString());
        data.setAppointment_date_time(slot.getSlotStartTime());
        data.setAppointment_type(slot.getAppointmentType());
        data.setStatus(slot.getStatus().name());
        
        BookAppointmentResponseDTO.Data.AppointmentDetails details = new BookAppointmentResponseDTO.Data.AppointmentDetails();
        details.setNotes(request.getNotes());
        details.setProvider_name(provider.getFirstName() + " " + provider.getLastName());
        details.setPatient_name(patient.getFirstName() + " " + patient.getLastName());
        // Location would be set based on provider's availability location
        details.setLocation("To be determined based on provider availability");
        
        data.setAppointment_details(details);
        response.setData(data);
        
        return response;
    }

    public GetAppointmentsResponseDTO getAppointmentsForProvider(UUID providerId) {
        List<AppointmentSlot> appointments = appointmentSlotRepository.findByProviderIdOrderBySlotStartTime(providerId);
        
        GetAppointmentsResponseDTO response = new GetAppointmentsResponseDTO();
        response.setSuccess(true);
        response.setMessage("Appointments retrieved successfully");
        
        GetAppointmentsResponseDTO.Data data = new GetAppointmentsResponseDTO.Data();
        data.setUser_id(providerId.toString());
        data.setUser_type("provider");
        
        List<GetAppointmentsResponseDTO.Data.Appointment> appointmentList = new ArrayList<>();
        GetAppointmentsResponseDTO.Data.Summary summary = new GetAppointmentsResponseDTO.Data.Summary();
        
        int total = 0, upcoming = 0, completed = 0, cancelled = 0;
        
        for (AppointmentSlot slot : appointments) {
            if (slot.getStatus() == AppointmentSlot.Status.BOOKED) {
                GetAppointmentsResponseDTO.Data.Appointment appointment = new GetAppointmentsResponseDTO.Data.Appointment();
                appointment.setAppointment_id(slot.getId().toString());
                appointment.setBooking_reference(slot.getBookingReference());
                appointment.setProvider_id(slot.getProviderId().toString());
                appointment.setPatient_id(slot.getPatientId() != null ? slot.getPatientId().toString() : null);
                appointment.setAppointment_date_time(slot.getSlotStartTime());
                appointment.setAppointment_type(slot.getAppointmentType());
                appointment.setStatus(slot.getStatus().name());
                appointment.setCreated_at(slot.getCreatedAt());
                appointment.setUpdated_at(slot.getUpdatedAt());
                
                // Get patient and provider names if available
                if (slot.getPatientId() != null) {
                    patientRepository.findById(slot.getPatientId()).ifPresent(patient -> 
                        appointment.setPatient_name(patient.getFirstName() + " " + patient.getLastName()));
                }
                
                providerRepository.findById(slot.getProviderId()).ifPresent(provider -> 
                    appointment.setProvider_name(provider.getFirstName() + " " + provider.getLastName()));
                
                appointmentList.add(appointment);
                total++;
                
                if (slot.getSlotStartTime().isAfter(LocalDateTime.now())) {
                    upcoming++;
                } else {
                    completed++;
                }
            } else if (slot.getStatus() == AppointmentSlot.Status.CANCELLED) {
                cancelled++;
            }
        }
        
        data.setAppointments(appointmentList);
        summary.setTotal_appointments(total);
        summary.setUpcoming_appointments(upcoming);
        summary.setCompleted_appointments(completed);
        summary.setCancelled_appointments(cancelled);
        data.setSummary(summary);
        
        response.setData(data);
        return response;
    }

    public GetAppointmentsResponseDTO getAppointmentsForPatient(UUID patientId) {
        List<AppointmentSlot> appointments = appointmentSlotRepository.findByPatientIdOrderBySlotStartTime(patientId);
        
        GetAppointmentsResponseDTO response = new GetAppointmentsResponseDTO();
        response.setSuccess(true);
        response.setMessage("Appointments retrieved successfully");
        
        GetAppointmentsResponseDTO.Data data = new GetAppointmentsResponseDTO.Data();
        data.setUser_id(patientId.toString());
        data.setUser_type("patient");
        
        List<GetAppointmentsResponseDTO.Data.Appointment> appointmentList = new ArrayList<>();
        GetAppointmentsResponseDTO.Data.Summary summary = new GetAppointmentsResponseDTO.Data.Summary();
        
        int total = 0, upcoming = 0, completed = 0, cancelled = 0;
        
        for (AppointmentSlot slot : appointments) {
            if (slot.getStatus() == AppointmentSlot.Status.BOOKED) {
                GetAppointmentsResponseDTO.Data.Appointment appointment = new GetAppointmentsResponseDTO.Data.Appointment();
                appointment.setAppointment_id(slot.getId().toString());
                appointment.setBooking_reference(slot.getBookingReference());
                appointment.setProvider_id(slot.getProviderId().toString());
                appointment.setPatient_id(slot.getPatientId().toString());
                appointment.setAppointment_date_time(slot.getSlotStartTime());
                appointment.setAppointment_type(slot.getAppointmentType());
                appointment.setStatus(slot.getStatus().name());
                appointment.setCreated_at(slot.getCreatedAt());
                appointment.setUpdated_at(slot.getUpdatedAt());
                
                // Get patient and provider names
                patientRepository.findById(slot.getPatientId()).ifPresent(patient -> 
                    appointment.setPatient_name(patient.getFirstName() + " " + patient.getLastName()));
                
                providerRepository.findById(slot.getProviderId()).ifPresent(provider -> 
                    appointment.setProvider_name(provider.getFirstName() + " " + provider.getLastName()));
                
                appointmentList.add(appointment);
                total++;
                
                if (slot.getSlotStartTime().isAfter(LocalDateTime.now())) {
                    upcoming++;
                } else {
                    completed++;
                }
            } else if (slot.getStatus() == AppointmentSlot.Status.CANCELLED) {
                cancelled++;
            }
        }
        
        data.setAppointments(appointmentList);
        summary.setTotal_appointments(total);
        summary.setUpcoming_appointments(upcoming);
        summary.setCompleted_appointments(completed);
        summary.setCancelled_appointments(cancelled);
        data.setSummary(summary);
        
        response.setData(data);
        return response;
    }

    private String generateBookingReference() {
        // Generate a unique booking reference
        return "BK" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
} 