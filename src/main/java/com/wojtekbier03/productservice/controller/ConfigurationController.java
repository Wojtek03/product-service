package com.wojtekbier03.productservice.controller;

import com.wojtekbier03.productservice.dto.ProductConfigurationDto;
import com.wojtekbier03.productservice.service.ConfigurationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "Add a new product configuration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product configuration successfully added",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductConfigurationDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid configuration data provided",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<ProductConfigurationDto> addConfiguration(
            @Parameter(description = "DTO containing the product configuration data to be added")
            @RequestBody ProductConfigurationDto configurationDto) {
        ProductConfigurationDto savedConfigurationDto = configurationService.addConfiguration(configurationDto);
        return ResponseEntity.ok(savedConfigurationDto);
    }

    @Operation(summary = "Retrieve a product configuration by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product configuration successfully retrieved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductConfigurationDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Product configuration not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<ProductConfigurationDto> getConfigurationById(
            @Parameter(description = "ID of the product configuration to retrieve") @PathVariable Long id) {
        ProductConfigurationDto configurationDto = configurationService.getConfigurationById(id);
        return ResponseEntity.ok(configurationDto);
    }

    @Operation(summary = "Retrieve a list of product configurations by type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of product configurations successfully retrieved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductConfigurationDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid type or filter parameters",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping("/type/{type}")
    public ResponseEntity<List<ProductConfigurationDto>> getConfigurationsByType(
            @Parameter(description = "Type of product configurations to retrieve") @PathVariable String type,
            @Parameter(description = "Optional list of allowed values for filtering the configurations")
            @RequestParam(required = false) List<String> allowedValues) {
        List<ProductConfigurationDto> configurations = configurationService.getConfigurationsByType(type, allowedValues);
        return ResponseEntity.ok(configurations);
    }
}