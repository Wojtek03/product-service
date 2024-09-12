package com.wojtekbier03.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wojtekbier03.productservice.dto.ProductDto;
import com.wojtekbier03.productservice.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addProduct_CorrectData_ProductSaved() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setName("Gaming Laptop");
        productDto.setPrice(BigDecimal.valueOf(5000));
        productDto.setType("Computer");

        Mockito.when(productService.add(any(ProductDto.class)))
                .thenReturn(productDto);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productDto.getId()));
    }

    @Test
    void getAllProducts_ReturnsListOfProducts() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);

        Mockito.when(productService.getAll(any()))
                .thenReturn(List.of(productDto));

        mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(productDto.getId()));
    }

    @Test
    void getProductById_ReturnsProduct() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);

        Mockito.when(productService.getById(anyLong()))
                .thenReturn(productDto);

        mockMvc.perform(get("/products/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productDto.getId()));
    }

    @Test
    void deleteProduct_CorrectId_ProductDeleted() throws Exception {
        Mockito.doNothing().when(productService).delete(anyLong());

        mockMvc.perform(delete("/products/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void assignConfigurationToProduct_Success() throws Exception {
        Mockito.when(productService.assignConfigurationToProduct(anyLong(), anyLong()))
                .thenReturn(ResponseEntity.noContent().build());

        mockMvc.perform(post("/products/{productId}/configurations/{configurationId}", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void getProductWithConfigurations_ReturnsProductWithConfigurations() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);

        Mockito.when(productService.getProductWithConfigurations(anyLong()))
                .thenReturn(productDto);

        mockMvc.perform(get("/products/{productId}/configurations", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productDto.getId()));
    }
}

