package com.signin.jwt.service;

import com.signin.jwt.dto.ProductDto;
import com.signin.jwt.entities.Product;
import com.signin.jwt.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductService {

    public static final String PRODUCT_CACHE = "products";
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @CachePut(value = PRODUCT_CACHE, key = "#result.id()")
    public ProductDto createProduct(ProductDto productDto) {
        var product = new Product();
        product.setName(productDto.name());
        product.setPrice(productDto.price());

        Product savedProduct = productRepository.save(product);
        return new ProductDto(savedProduct.getId(), savedProduct.getName(),
                savedProduct.getPrice());
    }

    @Cacheable(value = PRODUCT_CACHE, key = "#productId")
    public ProductDto getProduct(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find product with id " + productId));
        log.info("✅Getting data from database: {}",productId);
        return new ProductDto(product.getId(), product.getName(),
                product.getPrice());
    }

    @CachePut(value = PRODUCT_CACHE, key = "#result.id()")
    public ProductDto updateProduct(ProductDto productDto) {
        Long productId = productDto.id();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find product with id " + productId));

        product.setName(productDto.name());
        product.setPrice(productDto.price());

        Product updatedProduct = productRepository.save(product);
        return new ProductDto(updatedProduct.getId(), updatedProduct.getName(),
                updatedProduct.getPrice());
    }

    @CacheEvict(value = PRODUCT_CACHE, key = "#productId")
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
