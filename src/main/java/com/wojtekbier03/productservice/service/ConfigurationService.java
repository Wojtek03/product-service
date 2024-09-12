package com.wojtekbier03.productservice.service;

import com.wojtekbier03.productservice.dto.ProductConfigurationDto;
import com.wojtekbier03.productservice.entity.ProductConfiguration;
import com.wojtekbier03.productservice.exceptions.ConfigurationNotFoundException;
import com.wojtekbier03.productservice.mapper.ConfigurationMapper;
import com.wojtekbier03.productservice.repository.ConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConfigurationService {

    private final ConfigurationRepository configurationRepository;
    private final ConfigurationMapper configurationMapper;

    public ProductConfigurationDto addConfiguration(ProductConfigurationDto configurationDto) {
        ProductConfiguration configurationProduct = configurationMapper.toEntity(configurationDto);
        ProductConfiguration savedConfiguration = configurationRepository.save(configurationProduct);
        return configurationMapper.toDto(savedConfiguration);
    }

    public ProductConfigurationDto getConfigurationById(Long id) {
        return configurationRepository.findById(id)
                .map(configurationMapper::toDto)
                .orElseThrow(() -> new ConfigurationNotFoundException("Configuration not found for id: " + id));
    }

    public List<ProductConfigurationDto> getConfigurationsByType(String type, List<String> allowedValues) {
        return configurationRepository.findByType(type).stream()
                .filter(configuration -> allowedValues == null ||
                        allowedValues.stream().anyMatch(value -> value.equalsIgnoreCase(configuration.getValue())))
                .map(configurationMapper::toDto)
                .collect(Collectors.toList());
    }
}