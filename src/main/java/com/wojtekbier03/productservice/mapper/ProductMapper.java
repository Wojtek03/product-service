package com.wojtekbier03.productservice.mapper;

import com.wojtekbier03.productservice.dto.ProductDto;
import com.wojtekbier03.productservice.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product product);
    Product toEntity(ProductDto productDto);
    List<ProductDto> toDtoList(List<Product> products);
}