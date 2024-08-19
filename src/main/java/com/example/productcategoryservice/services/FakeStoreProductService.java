package com.example.productcategoryservice.services;

import com.example.productcategoryservice.client.FakeStoreAppClient;
import com.example.productcategoryservice.dtos.FakeStoreProductDto;
import com.example.productcategoryservice.models.Category;
import com.example.productcategoryservice.models.Product;
import io.micrometer.common.lang.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements IProductService {

    private final FakeStoreAppClient fakeStoreAppClient;

    @Autowired
    FakeStoreProductService(FakeStoreAppClient fakeStoreAppClient) {
        this.fakeStoreAppClient = fakeStoreAppClient;
    }

    @Override
    public List<Product> getAllProducts() {
        FakeStoreProductDto[] response = fakeStoreAppClient.getAllProducts();
        List<Product> products = new ArrayList<>();
        if (response == null) {
            return null;
        }
        for (FakeStoreProductDto fakeStoreProductDto : response) {
            products.add(getProduct(fakeStoreProductDto));
        }
        return products;
    }

    @Override
    public Product getProductById(Long id) {
        FakeStoreProductDto response = fakeStoreAppClient.getProductById(id);
        if (response == null) {
            return null;
        }
        return getProduct(response);
    }

    @Override
    public Product addProduct(Product product) {
        FakeStoreProductDto input = getFakeStoreProductDto(product);
        FakeStoreProductDto fakeStoreProductDtoResponse = fakeStoreAppClient.addProduct(input);
        return getProduct(fakeStoreProductDtoResponse);
    }

    @Override
    public Product updateProduct(Product product, Long id) {
        FakeStoreProductDto input = getFakeStoreProductDto(product);
        FakeStoreProductDto fakeStoreProductDtoResponse = fakeStoreAppClient.updateProduct(input, id);
        return getProduct(fakeStoreProductDtoResponse);
    }

    private Product getProduct(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setName(fakeStoreProductDto.getTitle());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setImageUrl(fakeStoreProductDto.getImage());
        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        return product;
    }

    private FakeStoreProductDto getFakeStoreProductDto(Product product) {
        System.out.println(product.toString());
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setTitle(product.getName());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setImage(product.getImageUrl());
        if (product.getCategory() != null) {
            fakeStoreProductDto.setCategory(product.getCategory().getName());
        }

        return fakeStoreProductDto;
    }
}
