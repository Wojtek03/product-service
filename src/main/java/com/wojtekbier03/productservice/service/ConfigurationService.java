package com.wojtekbier03.productservice.service;

import com.wojtekbier03.productservice.dto.ProductConfigurationDto;
import com.wojtekbier03.productservice.entity.ProductConfiguration;
import com.wojtekbier03.productservice.mapper.ConfigurationMapper;
import com.wojtekbier03.productservice.repository.ConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public Optional<ProductConfigurationDto> getConfigurationById(Long id) {
        return configurationRepository.findById(id)
                .map(configurationMapper::toDto);
    }

    public List<ProductConfigurationDto> getConfigurationsByType(String type) {
        return configurationRepository.findByType(type).stream()
                .map(configurationMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ProductConfigurationDto> getAvailableProcessorsForComputer(List<String> allowedProcessors) {
        return configurationRepository.findByType("Processor").stream()
                .filter(configuration -> allowedProcessors == null || allowedProcessors.contains(configuration.getValue()))
                .map(configurationMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ProductConfigurationDto> getAvailableRamOptionsForComputer(List<String> allowedRamOptions) {
        return configurationRepository.findByType("RAM").stream()
                .filter(configuration -> allowedRamOptions == null || allowedRamOptions.contains(configuration.getValue()))
                .map(configurationMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ProductConfigurationDto> getAvailableColorsForSmartphone(List<String> allowedColors) {
        return configurationRepository.findByType("Color").stream()
                .filter(configuration -> allowedColors == null || allowedColors.contains(configuration.getValue()))
                .map(configurationMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ProductConfigurationDto> getAvailableAccessoriesForSmartphone(List<String> allowedAccessories) {
        return configurationRepository.findByType("Accessory").stream()
                .filter(configuration -> allowedAccessories == null || allowedAccessories.contains(configuration.getValue()))
                .map(configurationMapper::toDto)
                .collect(Collectors.toList());
    }
}