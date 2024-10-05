package com.wojtekbier03.productservice.service;

import com.wojtekbier03.productservice.dto.ProductConfigurationDto;
import com.wojtekbier03.productservice.entity.ProductConfiguration;
import com.wojtekbier03.productservice.exceptions.ConfigurationNotFoundException;
import com.wojtekbier03.productservice.mapper.ConfigurationMapper;
import com.wojtekbier03.productservice.repository.ConfigurationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

public class ConfigurationServiceTest {

    private ConfigurationService configurationService;
    private ConfigurationRepository configurationRepository;
    private ConfigurationMapper configurationMapper;

    @BeforeEach
    void setUp() {
        configurationRepository = Mockito.mock(ConfigurationRepository.class);
        configurationMapper = Mappers.getMapper(ConfigurationMapper.class);
        configurationService = new ConfigurationService(configurationRepository, configurationMapper);
    }

    @Test
    void addConfiguration_Success() {
        ProductConfigurationDto dto = new ProductConfigurationDto();
        dto.setName("Processor");
        dto.setPrice(BigDecimal.valueOf(100));
        dto.setValue("Intel i7");
        dto.setType("Processor");

        ProductConfiguration entity = configurationMapper.toEntity(dto);
        when(configurationRepository.save(any(ProductConfiguration.class))).thenReturn(entity);

        ProductConfigurationDto result = configurationService.addConfiguration(dto);

        assertNotNull(result);
        assertEquals(dto.getName(), result.getName());
    }

    @Test
    void getConfigurationById_ConfigurationExists() {
        ProductConfiguration entity = new ProductConfiguration();
        entity.setId(1L);
        entity.setName("Processor");
        entity.setPrice(BigDecimal.valueOf(100));
        entity.setValue("Intel i7");
        entity.setType("Processor");

        when(configurationRepository.findById(1L)).thenReturn(Optional.of(entity));

        ProductConfigurationDto result = configurationService.getConfigurationById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getConfigurationById_ConfigurationNotFound() {
        when(configurationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ConfigurationNotFoundException.class, () -> {
            configurationService.getConfigurationById(1L);
        });
    }

    @Test
    void getConfigurationsByType_ValidType() {
        ProductConfiguration entity = new ProductConfiguration();
        entity.setId(1L);
        entity.setName("Processor");
        entity.setPrice(BigDecimal.valueOf(100));
        entity.setValue("Intel i7");
        entity.setType("Processor");

        when(configurationRepository.findByType("Processor")).thenReturn(Collections.singletonList(entity));

        List<ProductConfigurationDto> result = configurationService.getConfigurationsByType("Processor", null);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Processor", result.get(0).getType());
    }
}