package com.wojtekbier03.productservice.controller;

import com.wojtekbier03.productservice.dto.ProductConfigurationDto;
import com.wojtekbier03.productservice.service.ConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing product configurations.
 */
@RestController
@RequestMapping("/configurations")
@RequiredArgsConstructor
public class ConfigurationController {

    private final ConfigurationService configurationService;

    /**
     * Adds a new product configuration.
     *
     * @param configurationDto DTO containing the product configuration data to be added.
     * @return ResponseEntity containing the saved product configuration DTO.
     */
    @PostMapping
    public ResponseEntity<ProductConfigurationDto> addConfiguration(@RequestBody ProductConfigurationDto configurationDto) {
        ProductConfigurationDto savedConfigurationDto = configurationService.addConfiguration(configurationDto);
        return ResponseEntity.ok(savedConfigurationDto);
    }

    /**
     * Retrieves a product configuration by its ID.
     *
     * @param id The ID of the product configuration to retrieve.
     * @return ResponseEntity containing the product configuration DTO.
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<ProductConfigurationDto> getConfigurationById(@PathVariable Long id) {
        ProductConfigurationDto configurationDto = configurationService.getConfigurationById(id);
        return ResponseEntity.ok(configurationDto);
    }

    /**
     * Retrieves a list of product configurations by type.
     *
     * @param type The type of product configurations to retrieve.
     * @param allowedValues Optional list of allowed values for filtering the configurations.
     * @return ResponseEntity containing a list of product configuration.
     */
    @GetMapping("/type/{type}")
    public ResponseEntity<List<ProductConfigurationDto>> getConfigurationsByType(
            @PathVariable String type,
            @RequestParam(required = false) List<String> allowedValues) {
        List<ProductConfigurationDto> configurations = configurationService.getConfigurationsByType(type, allowedValues);
        return ResponseEntity.ok(configurations);
    }
}