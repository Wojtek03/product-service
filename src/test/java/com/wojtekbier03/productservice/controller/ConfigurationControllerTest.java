package com.wojtekbier03.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wojtekbier03.productservice.dto.ProductConfigurationDto;
import com.wojtekbier03.productservice.service.ConfigurationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ConfigurationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConfigurationService configurationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addConfiguration_CorrectData_ConfigurationSaved() throws Exception {
        ProductConfigurationDto configurationDto = new ProductConfigurationDto();
        configurationDto.setId(1L);
        configurationDto.setName("Processor");
        configurationDto.setValue("Intel i7");
        configurationDto.setType("Processor");

        Mockito.when(configurationService.addConfiguration(any(ProductConfigurationDto.class)))
                .thenReturn(configurationDto);

        mockMvc.perform(post("/configurations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(configurationDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(configurationDto.getId()));
    }

    @Test
    void getConfigurationById_ReturnsConfiguration() throws Exception {
        ProductConfigurationDto configurationDto = new ProductConfigurationDto();
        configurationDto.setId(1L);

        Mockito.when(configurationService.getConfigurationById(anyLong()))
                .thenReturn(configurationDto);

        mockMvc.perform(get("/configurations/id/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(configurationDto.getId()));
    }

    @Test
    void getConfigurationsByType_ReturnsConfigurations() throws Exception {
        ProductConfigurationDto configurationDto = new ProductConfigurationDto();
        configurationDto.setId(1L);

        Mockito.when(configurationService.getConfigurationsByType(any(String.class), any()))
                .thenReturn(List.of(configurationDto));

        mockMvc.perform(get("/configurations/type/{type}", "Processor")
                        .param("allowedValues", "Intel i7")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(configurationDto.getId()));
    }
}

