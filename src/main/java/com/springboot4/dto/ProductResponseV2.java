package com.springboot4.dto;

import java.util.List;

public record ProductResponseV2(
        List<ProductV2> products,
        int totalCount
) {
    public record ProductV2(
            Long id,
            String name,
            Double price,
            String description,
            String category
    ) {
    }
}
