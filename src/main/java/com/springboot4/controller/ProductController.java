package com.springboot4.controller;

import com.springboot4.dto.ProductResponseV1;
import com.springboot4.dto.ProductResponseV2;
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

    @GetMapping(version = "1")
    public List<ProductResponseV1> getProductsV1() {
        // call productService method
        return productService.getAllProducts()
                .stream()
                .map(this::toProductResponseV1)
                .toList();
    }

    @GetMapping(version = "2")
    public ProductResponseV2 getProductsV2() {
        // call productService method
        List<Product> products = productService.getAllProducts();
        return toProductResponseV2(products);
    }

    private ProductResponseV1 toProductResponseV1(Product product) {
        return new ProductResponseV1(
                product.id(),
                product.name(),
                product.price()
        );
    }

    private ProductResponseV2 toProductResponseV2(List<Product> products) {

        List<ProductResponseV2.ProductV2> productListV2 =
                products.stream().map(p -> new ProductResponseV2.ProductV2(
                        p.id(),
                        p.name(),
                        p.price(),
                        p.description(),
                        p.category()
                )).toList();
        return new ProductResponseV2(productListV2, products.size());
    }
}
