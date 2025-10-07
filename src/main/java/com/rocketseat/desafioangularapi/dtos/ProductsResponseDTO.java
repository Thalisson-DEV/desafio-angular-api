package com.rocketseat.desafioangularapi.dtos;

import java.util.List;

public record ProductsResponseDTO(
        String message,
        List<ProductDTO> data
) {
}
