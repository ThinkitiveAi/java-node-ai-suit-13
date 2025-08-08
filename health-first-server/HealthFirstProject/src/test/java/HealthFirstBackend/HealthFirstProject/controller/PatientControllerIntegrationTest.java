package HealthFirstBackend.HealthFirstProject.controller;

import HealthFirstBackend.HealthFirstProject.dto.PatientRegistrationRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private PatientRegistrationRequestDTO getValidRequest() {
        PatientRegistrationRequestDTO dto = new PatientRegistrationRequestDTO();
        dto.setFirst_name("Jane");
        dto.setLast_name("Smith");
        dto.setEmail("jane.smith@email.com");
        dto.setPhone_number("+1234567890");
        dto.setPassword("SecurePassword123!");
        dto.setConfirm_password("SecurePassword123!");
        dto.setDate_of_birth(LocalDate.of(1990, 5, 15));
        dto.setGender("female");
        PatientRegistrationRequestDTO.AddressDTO address = new PatientRegistrationRequestDTO.AddressDTO();
        address.setStreet("456 Main Street");
        address.setCity("Boston");
        address.setState("MA");
        address.setZip("02101");
        dto.setAddress(address);
        return dto;
    }

    @Test
    void testRegisterPatient_Success() throws Exception {
        PatientRegistrationRequestDTO dto = getValidRequest();
        mockMvc.perform(post("/api/v1/patient/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.email").value(dto.getEmail()))
                .andExpect(jsonPath("$.data.phone_number").value(dto.getPhone_number()))
                .andExpect(jsonPath("$.data.email_verified").value(false))
                .andExpect(jsonPath("$.data.phone_verified").value(false))
                .andExpect(jsonPath("$.data.password").doesNotExist());
    }

    @Test
    void testRegisterPatient_ValidationError() throws Exception {
        PatientRegistrationRequestDTO dto = getValidRequest();
        dto.setEmail("invalid-email");
        mockMvc.perform(post("/api/v1/patient/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors.email").exists());
    }

    @Test
    void testRegisterPatient_Duplicate() throws Exception {
        PatientRegistrationRequestDTO dto = getValidRequest();
        // First registration should succeed
        mockMvc.perform(post("/api/v1/patient/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
        // Second registration with same email/phone should fail
        mockMvc.perform(post("/api/v1/patient/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Email already exists"));
    }
} 