package com.springboot4.controller;

import com.springboot4.dto.ProductResponseV1;
import com.springboot4.dto.ProductResponseV2;
import com.springboot4.model.Product;
import com.springboot4.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(version = "1")
    public List<ProductResponseV1> getProductsV1() throws InterruptedException {
        try {
            // call productService method
            return productService.getAllProducts()
                    .stream()
                    .map(this::toProductResponseV1)
                    .toList();
        } catch (RuntimeException e) {
            return fallbackOptionV1(e);
        }
    }

    @GetMapping(version = "2")
    public ProductResponseV2 getProductsV2() throws InterruptedException {
        try {
            // call productService method
            List<Product> products = productService.getAllProducts();
            return toProductResponseV2(products);
        } catch (RuntimeException e) {
            return fallbackOptionV2(e);
        }
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

    public List<ProductResponseV1> fallbackOptionV1(RuntimeException exception) {
        // in realtime - fetch from cache or some list
        System.out.println("Fallback option gets triggered after all retries failed" + exception.getMessage());
        // cause high severity incident
        // send email to team and leadership
        return Collections.emptyList();
    }

    public ProductResponseV2 fallbackOptionV2(RuntimeException exception) {
        // in realtime - fetch from cache or some list
        System.out.println("Fallback option gets triggered after all retries failed" + exception.getMessage());
        // cause high severity incident
        // send email to team and leadership
        return new ProductResponseV2(Collections.emptyList(), 0);
    }
}
