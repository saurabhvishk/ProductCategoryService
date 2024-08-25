package com.example.productcategoryservice.controllers;

import com.example.productcategoryservice.dtos.ProductDto;
import com.example.productcategoryservice.models.Product;
import com.example.productcategoryservice.services.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IProductService productService;

    @Autowired
    private ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private ProductController productController;

    @Test
    public void Test_GetAllProductsAPI_RunSuccessfully() throws Exception {
        // Arrange
        Product product1 = new Product();
        product1.setName("Product 1");
        Product product2 = new Product();
        product2.setName("Product 2");
        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);

        when(productService.getAllProducts()).thenReturn(productList);

        // Act and Assert
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(productList)));
    }

    @Test
    public void Test_CreateProductAPI_ValidProduct_RunSuccessfully() throws Exception {
        // Arrange
        ProductDto productDto = new ProductDto();
        productDto.setName("Product 1");
        productDto.setId(1L);
        productDto.setPrice(100.0);

        Product product = new Product();
        product.setName("Product 1");
        product.setId(1L);
        product.setPrice(100.0);

        when(productService.addProduct(any(Product.class))).thenReturn(product);

        // Act and Assert
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productDto)))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(product)));
    }
}