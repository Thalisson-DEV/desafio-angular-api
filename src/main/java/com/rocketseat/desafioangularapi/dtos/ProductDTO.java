package com.rocketseat.desafioangularapi.dtos;

import com.rocketseat.desafioangularapi.enums.ProductStatus;

public record ProductDTO(
        Long id,
        String title,
        Double price,
        String description,
        String category,
        ProductStatus status,
        String imageBase64
) {
}
