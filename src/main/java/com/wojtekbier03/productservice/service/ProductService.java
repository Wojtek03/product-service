package com.wojtekbier03.productservice.service;

import com.wojtekbier03.productservice.dto.ProductDto;
import com.wojtekbier03.productservice.entity.Product;
import com.wojtekbier03.productservice.entity.ProductConfiguration;
import com.wojtekbier03.productservice.mapper.ProductMapper;
import com.wojtekbier03.productservice.repository.ConfigurationRepository;
import com.wojtekbier03.productservice.repository.ProductRepository;
import com.wojtekbier03.productservice.exceptions.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ConfigurationRepository configurationRepository;

    public List<ProductDto> getAll(Pageable pageable) {
        return productRepository.findAll(pageable).stream()
                .map(productMapper::toDto)
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                    Collections.reverse(list);
                    return list;
                }));
    }

    public ProductDto getById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));
    }

    public ProductDto add(ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    public ProductDto updateProduct(Long id, ProductDto productDTO) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product with ID " + id + " not found");
        }
        Product product = productMapper.toEntity(productDTO);
        product.setId(id);
        Product updatedProduct = productRepository.save(product);
        return productMapper.toDto(updatedProduct);
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product with ID " + id + " not found");
        }
        productRepository.deleteById(id);
    }

    public ResponseEntity<Void> assignConfigurationToProduct(Long productId, Long configurationId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + productId + " not found"));

        ProductConfiguration configuration = configurationRepository.findById(configurationId)
                .orElseThrow(() -> new ProductNotFoundException("Configuration with ID " + configurationId + " not found"));

        if ("Computer".equalsIgnoreCase(product.getType())) {
            if (!"Processor".equalsIgnoreCase(configuration.getType()) && !"RAM".equalsIgnoreCase(configuration.getType())) {
                throw new IllegalArgumentException("Invalid configuration type for Computer");
            }
        } else if ("Smartphone".equalsIgnoreCase(product.getType())) {
            if (!"Color".equalsIgnoreCase(configuration.getType()) && !"Accessories".equalsIgnoreCase(configuration.getType())) {
                throw new IllegalArgumentException("Invalid configuration type for Smartphone");
            }
        } else if ("Electronics".equalsIgnoreCase(product.getType())) {
            throw new IllegalArgumentException("No configurations allowed for Electronics");
        }

        product.getConfigurations().add(configuration);
        productRepository.save(product);

        return ResponseEntity.noContent().build();
    }

    public ProductDto getProductWithConfigurations(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + productId + " not found"));

        return productMapper.toDto(product);
    }
}