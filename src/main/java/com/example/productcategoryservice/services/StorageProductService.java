package com.example.productcategoryservice.services;

import com.example.productcategoryservice.models.Product;
import com.example.productcategoryservice.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class StorageProductService implements IProductService {

    private final ProductRepo productRepo;

    @Autowired
    StorageProductService (ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public List<Product> getAllProducts() {
        Optional<List<Product>> optionalProducts = Optional.of(productRepo.findAll());
        return optionalProducts.orElse(null);
    }

    @Override
    public Product getProductById(Long id) {
        Optional<Product> optionalProduct = productRepo.findById(id);
        return optionalProduct.orElse(null);
    }

    @Override
    public Product addProduct(Product product) {
        Optional<Product> optionalProduct = Optional.of(productRepo.save(product));
        return optionalProduct.orElse(null);
    }

    @Override
    public Product updateProduct(Product product, Long id) {
        Optional<Product> optionalProduct = Optional.of(productRepo.save(product));
        return optionalProduct.orElse(null);
    }
}
