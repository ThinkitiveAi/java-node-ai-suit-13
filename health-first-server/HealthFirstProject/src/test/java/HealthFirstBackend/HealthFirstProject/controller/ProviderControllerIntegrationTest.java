package HealthFirstBackend.HealthFirstProject.controller;

import HealthFirstBackend.HealthFirstProject.dto.ProviderRegistrationRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProviderControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private ProviderRegistrationRequestDTO getValidRequest() {
        ProviderRegistrationRequestDTO dto = new ProviderRegistrationRequestDTO();
        dto.setFirst_name("John");
        dto.setLast_name("Doe");
        dto.setEmail("john.doe@clinic.com");
        dto.setPhone_number("+1234567890");
        dto.setPassword("SecurePassword123!");
        dto.setConfirm_password("SecurePassword123!");
        dto.setSpecialization("Cardiology");
        dto.setLicense_number("MD123456789");
        dto.setYears_of_experience(10);
        ProviderRegistrationRequestDTO.ClinicAddressDTO address = new ProviderRegistrationRequestDTO.ClinicAddressDTO();
        address.setStreet("123 Medical Center Dr");
        address.setCity("New York");
        address.setState("NY");
        address.setZip("10001");
        dto.setClinic_address(address);
        return dto;
    }

    @Test
    void testRegisterProvider_Success() throws Exception {
        ProviderRegistrationRequestDTO dto = getValidRequest();
        mockMvc.perform(post("/api/v1/provider/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.email").value(dto.getEmail()));
    }

    @Test
    void testRegisterProvider_ValidationError() throws Exception {
        ProviderRegistrationRequestDTO dto = getValidRequest();
        dto.setEmail("invalid-email");
        mockMvc.perform(post("/api/v1/provider/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors.email").exists());
    }
} 