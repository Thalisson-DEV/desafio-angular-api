package com.rocketseat.desafioangularapi.dtos;

import java.util.List;


public record ProductsResponseDTO(
        String message,
        List<ProductDTO> data,

        // Metadados de paginação
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages
) {
}