package com.wojtekbier03.productservice.controller;

import com.wojtekbier03.productservice.dto.ProductDto;
import com.wojtekbier03.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing products.
 */
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * Retrieves a paginated list of all products.
     *
     * @param pageable Pageable object containing pagination information.
     * @return ResponseEntity containing a list of product DTOs.
     */
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAll(Pageable pageable) {
        List<ProductDto> products = productService.getAll(pageable);
        return ResponseEntity.ok(products);
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id The ID of the product to retrieve.
     * @return ResponseEntity containing the product DTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable Long id) {
        ProductDto productDto = productService.getById(id);
        return ResponseEntity.ok(productDto);
    }

    /**
     * Adds a new product.
     *
     * @param productDto DTO containing the product data to be added.
     * @return ResponseEntity containing the saved product DTO.
     */
    @PostMapping
    public ResponseEntity<ProductDto> add(@RequestBody ProductDto productDto) {
        ProductDto savedProductDto = productService.add(productDto);
        return ResponseEntity.ok(savedProductDto);
    }

    /**
     * Updates an existing product.
     *
     * @param id The ID of the product to update.
     * @param productDto DTO containing the updated product data.
     * @return ResponseEntity containing the updated product DTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        ProductDto updatedProductDto = productService.updateProduct(id, productDto);
        return ResponseEntity.ok(updatedProductDto);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id The ID of the product to delete.
     * @return ResponseEntity with no content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Assigns a configuration to a product.
     *
     * @param productId The ID of the product to which the configuration will be assigned.
     * @param configurationId The ID of the configuration to assign.
     * @return ResponseEntity with no content.
     */
    @PostMapping("/{productId}/configurations/{configurationId}")
    public ResponseEntity<Void> assignConfigurationToProduct(
            @PathVariable Long productId, @PathVariable Long configurationId) {
        return productService.assignConfigurationToProduct(productId, configurationId);
    }

    /**
     * Retrieves a product with its associated configurations.
     *
     * @param productId The ID of the product to retrieve with configurations.
     * @return ResponseEntity containing the product DTO with configurations.
     */
    @GetMapping("/{productId}/configurations")
    public ResponseEntity<ProductDto> getProductWithConfigurations(@PathVariable Long productId) {
        ProductDto productWithConfigurations = productService.getProductWithConfigurations(productId);
        return ResponseEntity.ok(productWithConfigurations);
    }
}