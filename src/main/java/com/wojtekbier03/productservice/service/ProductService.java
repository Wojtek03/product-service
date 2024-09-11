package com.wojtekbier03.productservice.service;

import com.wojtekbier03.productservice.dto.ProductConfigurationDto;
import com.wojtekbier03.productservice.dto.ProductDto;
import com.wojtekbier03.productservice.entity.Product;
import com.wojtekbier03.productservice.entity.ProductConfiguration;
import com.wojtekbier03.productservice.mapper.ConfigurationMapper;
import com.wojtekbier03.productservice.mapper.ProductMapper;
import com.wojtekbier03.productservice.repository.ConfigurationRepository;
import com.wojtekbier03.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductDto> getAll(Pageable pageable) {
        return productRepository.findAll(pageable).stream()
                .map(productMapper::toDto)
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                    Collections.reverse(list);
                    return list;
                }));
    }

    public Optional<ProductDto> getById(Long id){
        return productRepository.findById(id)
                .map(productMapper::toDto);
    }

    public ProductDto add(ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    public ProductDto updateProduct(Long id, ProductDto productDTO) {
        Product product = productMapper.toEntity(productDTO);
        product.setId(id);
        Product updatedProduct = productRepository.save(product);
        return productMapper.toDto(updatedProduct);
    }
    public Product save(Product product){
        return productRepository.save(product);
    }

    public void delete(Long id){
        productRepository.deleteById(id);
    }
}