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
        throw new UnsupportedOperationException("Not supported yet.");
//        Product product = new Product();
//        product.setId(id);
//        return product;
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody Product product){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PutMapping("{id}")
    public ProductDto updateProduct(@RequestBody Product product,@PathVariable Long id){
        throw new UnsupportedOperationException("Not supported yet.");
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
