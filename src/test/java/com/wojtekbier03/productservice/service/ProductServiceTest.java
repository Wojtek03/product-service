package com.wojtekbier03.productservice.service;

import com.wojtekbier03.productservice.dto.ProductDto;
import com.wojtekbier03.productservice.entity.Product;
import com.wojtekbier03.productservice.entity.ProductConfiguration;
import com.wojtekbier03.productservice.exceptions.ProductNotFoundException;
import com.wojtekbier03.productservice.mapper.ProductMapper;
import com.wojtekbier03.productservice.repository.ConfigurationRepository;
import com.wojtekbier03.productservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;
    private ProductMapper productMapper;
    private ConfigurationRepository configurationRepository;

    @BeforeEach
    void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        configurationRepository = Mockito.mock(ConfigurationRepository.class);
        productMapper = Mappers.getMapper(ProductMapper.class);
        productService = new ProductService(productRepository, productMapper, configurationRepository);
    }

    @Test
    void addProduct_Success() {
        ProductDto dto = new ProductDto();
        dto.setName("Gaming Laptop");
        dto.setPrice(BigDecimal.valueOf(5000));
        dto.setType("Computer");

        Product entity = productMapper.toEntity(dto);
        when(productRepository.save(any(Product.class))).thenReturn(entity);

        ProductDto result = productService.add(dto);

        assertNotNull(result);
        assertEquals(dto.getName(), result.getName());
    }

    @Test
    void getProductById_ProductExists() {
        Product entity = new Product();
        entity.setId(1L);
        entity.setName("Gaming Laptop");
        entity.setPrice(BigDecimal.valueOf(5000));
        entity.setType("Computer");

        when(productRepository.findById(1L)).thenReturn(Optional.of(entity));

        ProductDto result = productService.getById(1L);

        assertNotNull(result);
        assertEquals("Gaming Laptop", result.getName());
    }

    @Test
    void getProductById_ProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            productService.getById(1L);
        });
    }

    @Test
    void assignConfigurationToProduct_Success() {
        // given
        Product product = new Product();
        product.setId(1L);
        product.setName("Gaming Laptop");
        product.setPrice(BigDecimal.valueOf(5000));
        product.setType("Computer");
        product.setConfigurations(new HashSet<>());

        ProductConfiguration configuration = new ProductConfiguration();
        configuration.setId(1L);
        configuration.setName("Processor");
        configuration.setValue("Intel i7");
        configuration.setType("Processor");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(configurationRepository.findById(1L)).thenReturn(Optional.of(configuration));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ResponseEntity<Void> response = productService.assignConfigurationToProduct(1L, 1L);

        assertEquals(ResponseEntity.noContent().build(), response);
        assertTrue(product.getConfigurations().contains(configuration));
    }

    @Test
    void assignConfigurationToProduct_InvalidConfigurationForType() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Gaming Laptop");
        product.setPrice(BigDecimal.valueOf(5000));
        product.setType("Computer");

        ProductConfiguration configuration = new ProductConfiguration();
        configuration.setId(1L);
        configuration.setName("Color");
        configuration.setValue("Black");
        configuration.setType("Color");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(configurationRepository.findById(1L)).thenReturn(Optional.of(configuration));

        assertThrows(IllegalArgumentException.class, () -> {
            productService.assignConfigurationToProduct(1L, 1L);
        });
    }

    @Test
    void getProductWithConfigurations_ProductExists() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Gaming Laptop");
        product.setPrice(BigDecimal.valueOf(5000));
        product.setType("Computer");

        ProductConfiguration configuration = new ProductConfiguration();
        configuration.setId(1L);
        configuration.setName("Processor");
        configuration.setValue("Intel i7");
        configuration.setType("Processor");

        product.setConfigurations(Collections.singleton(configuration));

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductDto result = productService.getProductWithConfigurations(1L);

        assertNotNull(result);
        assertEquals("Gaming Laptop", result.getName());
        assertEquals(1, result.getConfigurations().size());
    }
}