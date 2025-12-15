package com.springboot4.service;

import com.springboot4.dto.ProductResponseV1;
import com.springboot4.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final List<Product> products;

    public ProductService() {
        // initialize with mock data
        products = new ArrayList<>();
        products.add(new Product(1L, "iphone", 999.99, "Iphone", "mobile"));
        products.add(new Product(2L, "samsung", 899.99, "samsung galaxy", "mobile"));
        products.add(new Product(3L, "oppo", 699.99, "oppo", "mobile"));
        products.add(new Product(4L, "airpods", 599.99, "apple airpods", "accessories"));
        products.add(new Product(5L, "iqoo", 999.99, "iqoo", "mobile"));
    }

    public List<Product> getAllProducts(){
        return new ArrayList<>(products);
    }
}
