package com.wojtekbier03.productservice.repository;

import com.wojtekbier03.productservice.entity.Product;
import com.wojtekbier03.productservice.entity.ProductConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigurationRepository extends JpaRepository<ProductConfiguration, Long> {
    List<ProductConfiguration> findByType(String type);
}