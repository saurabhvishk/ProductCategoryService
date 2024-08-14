package com.example.productcategoryservice.client;

import com.example.productcategoryservice.dtos.FakeStoreProductDto;
import io.micrometer.common.lang.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class FakeStoreAppClient {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    public <T> ResponseEntity<T> requestForEntity(String url, HttpMethod httpMethod, @Nullable Object request,
                                                  Class<T> responseType,
                                                  Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }

    public FakeStoreProductDto[] getAllProducts() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity("http://fakestoreapi.com/products", FakeStoreProductDto[].class).getBody();
    }

    public FakeStoreProductDto getProductById(Long id) {
        return requestForEntity("http://fakestoreapi.com/products/{id}", HttpMethod.GET, null,
                FakeStoreProductDto.class, id).getBody();
    }

    public FakeStoreProductDto addProduct(FakeStoreProductDto product) {
        return requestForEntity("http://fakestoreapi.com/products/", HttpMethod.POST, product,
                FakeStoreProductDto.class).getBody();
    }

    public FakeStoreProductDto updateProduct(FakeStoreProductDto product, Long id) {
        return requestForEntity("http://fakestoreapi.com/products/{id}", HttpMethod.PUT, product,
                FakeStoreProductDto.class, id).getBody();
    }
}
