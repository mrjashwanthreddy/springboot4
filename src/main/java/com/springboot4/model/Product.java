package com.springboot4.model;

// immutability and clean code
// we are not going to use lombok and getter/setter's
public record Product(
        Long id,
        String name,
        Double price,
        String description,
        String category
) {
}
