package com.rocketseat.desafioangularapi.dtos;

import com.rocketseat.desafioangularapi.enums.ProductStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

public record ProductRequestDTO(
        @NotBlank(message = "O titulo é obrigatório.")
        String title,
        @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero.")
        Double price,
        String description,
        String category,
        String imageBase64,
        ProductStatus status
) {
}
