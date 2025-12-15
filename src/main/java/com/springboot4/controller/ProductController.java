package com.springboot4.controller;

import com.springboot4.dto.ProductResponseV1;
import com.springboot4.model.Product;
import com.springboot4.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/v1")
    public List<ProductResponseV1> getProductsV1() {
        // call productService method
        return productService.getAllProducts()
                .stream()
                .map(this::toProductResponseV1)
                .toList();
    }

    private ProductResponseV1 toProductResponseV1(Product product) {
        return new ProductResponseV1(
                product.id(),
                product.name(),
                product.price()
        );
    }
}
