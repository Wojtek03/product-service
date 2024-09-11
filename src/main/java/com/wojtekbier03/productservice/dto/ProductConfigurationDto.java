package com.wojtekbier03.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductConfigurationDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String value;
    private String type;
}