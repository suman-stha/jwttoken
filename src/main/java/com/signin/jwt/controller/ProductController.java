package com.signin.jwt.controller;



import com.signin.jwt.dto.ProductDto;
import com.signin.jwt.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ProductDto createProduct(@RequestBody @Valid ProductDto productDto) {
        return productService.createProduct(productDto);
    }

    @GetMapping("/{productId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ProductDto getProduct(@PathVariable Long productId) {
        return productService.getProduct(productId);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.OK)
    public ProductDto updateProduct(@RequestBody @Valid ProductDto productDto) {
        return productService.updateProduct(productDto);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }
}
