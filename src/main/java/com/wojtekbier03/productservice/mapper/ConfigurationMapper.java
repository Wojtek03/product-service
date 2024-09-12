package com.wojtekbier03.productservice.mapper;

import com.wojtekbier03.productservice.dto.ProductConfigurationDto;
import com.wojtekbier03.productservice.entity.ProductConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConfigurationMapper {

    ProductConfigurationDto toDto(ProductConfiguration productConfiguration);
    ProductConfiguration toEntity(ProductConfigurationDto productConfigurationDto);
}