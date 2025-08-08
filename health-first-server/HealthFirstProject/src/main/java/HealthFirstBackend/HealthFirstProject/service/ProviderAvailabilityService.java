package HealthFirstBackend.HealthFirstProject.service;

import HealthFirstBackend.HealthFirstProject.dto.ProviderAvailabilityRequestDTO;
import HealthFirstBackend.HealthFirstProject.dto.ProviderAvailabilityResponseDTO;
import HealthFirstBackend.HealthFirstProject.model.*;
import HealthFirstBackend.HealthFirstProject.repository.ProviderAvailabilityRepository;
import HealthFirstBackend.HealthFirstProject.repository.AppointmentSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Validated
public class ProviderAvailabilityService {
    @Autowired
    private ProviderAvailabilityRepository availabilityRepository;
    @Autowired
    private AppointmentSlotRepository slotRepository;

    @Transactional
    public ProviderAvailabilityResponseDTO createAvailability(UUID providerId, @Valid ProviderAvailabilityRequestDTO dto) {
        // Validate time range
        if (dto.getEnd_time().isBefore(dto.getStart_time()) || dto.getEnd_time().equals(dto.getStart_time())) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        // Check for conflicts
        checkForConflicts(providerId, dto.getDate(), dto.getStart_time(), dto.getEnd_time());

        // Create availability
        ProviderAvailability availability = new ProviderAvailability();
        availability.setProviderId(providerId);
        availability.setDate(dto.getDate());
        availability.setStartTime(dto.getStart_time());
        availability.setEndTime(dto.getEnd_time());
        availability.setTimezone(dto.getTimezone());
        availability.setSlotDuration(dto.getSlot_duration());
        availability.setBreakDuration(dto.getBreak_duration());
        availability.setIsRecurring(dto.isIs_recurring());
        availability.setRecurrencePattern(dto.getRecurrence_pattern() != null ? 
            ProviderAvailability.RecurrencePattern.valueOf(dto.getRecurrence_pattern().name()) : null);
        availability.setRecurrenceEndDate(dto.getRecurrence_end_date());
        availability.setAppointmentType(ProviderAvailability.AppointmentType.valueOf(dto.getAppointment_type().name()));
        availability.setNotes(dto.getNotes());
        availability.setSpecialRequirements(dto.getSpecial_requirements());

        // Map location
        if (dto.getLocation() != null) {
            Location location = new Location();
            location.setType(Location.LocationType.valueOf(dto.getLocation().getType().name()));
            location.setAddress(dto.getLocation().getAddress());
            location.setRoomNumber(dto.getLocation().getRoom_number());
            availability.setLocation(location);
        }

        // Map pricing
        if (dto.getPricing() != null) {
            Pricing pricing = new Pricing();
            pricing.setBaseFee(dto.getPricing().getBase_fee());
            pricing.setInsuranceAccepted(dto.getPricing().isInsurance_accepted());
            pricing.setCurrency(dto.getPricing().getCurrency());
            availability.setPricing(pricing);
        }

        availabilityRepository.save(availability);

        // Generate appointment slots
        List<AppointmentSlot> slots = generateAppointmentSlots(availability);
        slotRepository.saveAll(slots);

        // Prepare response
        ProviderAvailabilityResponseDTO response = new ProviderAvailabilityResponseDTO();
        response.setSuccess(true);
        response.setMessage("Availability slots created successfully");
        ProviderAvailabilityResponseDTO.Data data = new ProviderAvailabilityResponseDTO.Data();
        data.setAvailability_id(availability.getId().toString());
        data.setSlots_created(slots.size());
        ProviderAvailabilityResponseDTO.Data.DateRange dateRange = new ProviderAvailabilityResponseDTO.Data.DateRange();
        dateRange.setStart(dto.getDate().toString());
        dateRange.setEnd(dto.getRecurrence_end_date() != null ? dto.getRecurrence_end_date().toString() : dto.getDate().toString());
        data.setDate_range(dateRange);
        data.setTotal_appointments_available(slots.size());
        response.setData(data);

        return response;
    }

    private void checkForConflicts(UUID providerId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        List<ProviderAvailability> existing = availabilityRepository.findByProviderIdAndDate(providerId, date);
        for (ProviderAvailability existingAvailability : existing) {
            if ((startTime.isBefore(existingAvailability.getEndTime()) && endTime.isAfter(existingAvailability.getStartTime()))) {
                throw new IllegalArgumentException("Time slot conflicts with existing availability");
            }
        }
    }

    private List<AppointmentSlot> generateAppointmentSlots(ProviderAvailability availability) {
        List<AppointmentSlot> slots = new ArrayList<>();
        LocalTime currentTime = availability.getStartTime();
        
        while (currentTime.isBefore(availability.getEndTime())) {
            LocalTime slotEndTime = currentTime.plusMinutes(availability.getSlotDuration());
            if (slotEndTime.isAfter(availability.getEndTime())) {
                break;
            }

            AppointmentSlot slot = new AppointmentSlot();
            slot.setAvailabilityId(availability.getId());
            slot.setProviderId(availability.getProviderId());
            slot.setSlotStartTime(LocalDateTime.of(availability.getDate(), currentTime));
            slot.setSlotEndTime(LocalDateTime.of(availability.getDate(), slotEndTime));
            slot.setStatus(AppointmentSlot.Status.AVAILABLE);
            slot.setAppointmentType(availability.getAppointmentType().name());
            slots.add(slot);

            // Add break duration
            currentTime = slotEndTime.plusMinutes(availability.getBreakDuration());
        }

        return slots;
    }
} 