package com.wojtekbier03.productservice.controller;

import com.wojtekbier03.productservice.dto.ProductConfigurationDto;
import com.wojtekbier03.productservice.service.ConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/configurations")
@RequiredArgsConstructor
public class ConfigurationController {
    private final ConfigurationService configurationService;

    @PostMapping
    public ResponseEntity<ProductConfigurationDto> addConfiguration(@RequestBody ProductConfigurationDto configurationDto) {
        ProductConfigurationDto savedConfiguration = configurationService.addConfiguration(configurationDto);
        return ResponseEntity.ok(savedConfiguration);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductConfigurationDto> getConfigurationById(@PathVariable Long id) {
        Optional<ProductConfigurationDto> configuration = configurationService.getConfigurationById(id);
        return configuration.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<ProductConfigurationDto>> getConfigurationsByType(@PathVariable String type) {
        List<ProductConfigurationDto> configurations = configurationService.getConfigurationsByType(type);
        return ResponseEntity.ok(configurations);
    }

    @GetMapping("/computers/processors")
    public ResponseEntity<List<ProductConfigurationDto>> getAvailableProcessorsForComputer(
            @RequestParam(required = false) List<String> allowedProcessors) {
        List<ProductConfigurationDto> processors = configurationService.getAvailableProcessorsForComputer(allowedProcessors);
        return ResponseEntity.ok(processors);
    }

    @GetMapping("/computers/ram")
    public ResponseEntity<List<ProductConfigurationDto>> getAvailableRamOptionsForComputer(
            @RequestParam(required = false) List<String> allowedRamOptions) {
        List<ProductConfigurationDto> ramOptions = configurationService.getAvailableRamOptionsForComputer(allowedRamOptions);
        return ResponseEntity.ok(ramOptions);
    }

    @GetMapping("/smartphones/colors")
    public ResponseEntity<List<ProductConfigurationDto>> getAvailableColorsForSmartphone(
            @RequestParam(required = false) List<String> allowedColors) {
        List<ProductConfigurationDto> colors = configurationService.getAvailableColorsForSmartphone(allowedColors);
        return ResponseEntity.ok(colors);
    }

    @GetMapping("/smartphones/accessories")
    public ResponseEntity<List<ProductConfigurationDto>> getAvailableAccessoriesForSmartphone(
            @RequestParam(required = false) List<String> allowedAccessories) {
        List<ProductConfigurationDto> accessories = configurationService.getAvailableAccessoriesForSmartphone(allowedAccessories);
        return ResponseEntity.ok(accessories);
    }
}