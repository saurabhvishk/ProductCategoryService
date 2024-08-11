package com.example.productcategoryservice.controllers;

import com.example.productcategoryservice.dtos.CategoryDto;
import com.example.productcategoryservice.dtos.ProductDto;
import com.example.productcategoryservice.models.Category;
import com.example.productcategoryservice.models.Product;
import com.example.productcategoryservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping
    public List<ProductDto> getAllProducts(){
        List<ProductDto> response = new ArrayList<>();
        List<Product> products = productService.getAllProducts();
        for(Product product : products){
            response.add(getProductDto(product));
        }
        return response;
    }

    @GetMapping("{id}")
    public ProductDto getProduct(@PathVariable Long id){
        ProductDto response = new ProductDto();
        Product product = productService.getProductById(id);
        response = getProductDto(product);
        return response;
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto productDto){
        Product input = getProduct(productDto);
        Product product = productService.addProduct(input);
        return getProductDto(product);
    }

    @PutMapping("{id}")
    public ProductDto updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto){
        Product input = getProduct(productDto);
        Product product = productService.updateProduct(input,id);
        return getProductDto(product);
    }

    private Product getProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        product.setDescription(productDto.getDescription());
        if(productDto.getCategory() != null) {
            Category category = new Category();
            category.setName(productDto.getCategory().getName());
            product.setCategory(category);
        }
        return product;
    }

    private ProductDto getProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        productDto.setDescription(product.getDescription());
        if(product.getCategory() != null) {
            CategoryDto category = new CategoryDto();
            category.setId(product.getCategory().getId());
            category.setName(product.getCategory().getName());
            productDto.setCategory(category);
        }
        return productDto;
    }
}
