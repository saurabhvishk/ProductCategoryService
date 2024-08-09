package com.example.productcategoryservice.services;

import com.example.productcategoryservice.models.Product;

import java.util.List;

public interface IProductService {
    public List<Product> getAllProducts();
    public Product getProductById(Long id);
    public Product addProduct(Product product);
    public Product updateProduct(Product product);
}
