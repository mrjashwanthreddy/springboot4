package com.springboot4.service;

import com.springboot4.dto.ProductResponseV1;
import com.springboot4.model.Product;
import org.springframework.resilience.annotation.ConcurrencyLimit;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ProductService {

    private final List<Product> products;

    private static AtomicInteger active = new AtomicInteger(0);
    private static AtomicInteger attemptCount = new AtomicInteger(0);

    public ProductService() {
        // initialize with mock data
        products = new ArrayList<>();
        products.add(new Product(1L, "iphone", 999.99, "Iphone", "mobile"));
        products.add(new Product(2L, "samsung", 899.99, "samsung galaxy", "mobile"));
        products.add(new Product(3L, "oppo", 699.99, "oppo", "mobile"));
        products.add(new Product(4L, "airpods", 599.99, "apple airpods", "accessories"));
        products.add(new Product(5L, "iqoo", 999.99, "iqoo", "mobile"));
    }

    // inbuilt resilience mechanism
    @Retryable(
            maxRetries = 4, // Retry upto 4 times after initial failure
            delay = 100,// wait for 100ms before first retry
            jitter = 10, // +-10ms randomness to prevent retry storms
            multiplier = 2, // exponential backoff (100ms -> 200ms -> 400ms -> 800ms)
            maxDelay = 1000 // cap retry delay max at 1000ms
    )
    @ConcurrencyLimit(2)
    public List<Product> getAllProducts() throws InterruptedException {
        // concurrency limit test lines
        //int counter = active.incrementAndGet();
        //System.out.println("Started - Active count : " + counter);
        //Thread.sleep(5000);
        //.decrementAndGet();
        //System.out.println("Ended - Active count : " + (counter - 1));

        // retryable test lines
        int attempt = attemptCount.incrementAndGet();
        System.out.println("Attempt #" + attempt);
        if (attempt <= 10) { // succeed on 4th attempt
            System.out.println("Failed Attempt #" + attempt);
            throw new RuntimeException("simulated external service failure");
        }
        System.out.println("Success Attempt #" + attempt);
        attemptCount.set(0); // reset for next run

        return new ArrayList<>(products);
    }

}
