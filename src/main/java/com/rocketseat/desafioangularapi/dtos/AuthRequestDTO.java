package com.rocketseat.desafioangularapi.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthRequestDTO(
        @NotBlank(message = "O email é obrigatório")
        @Email(message = "O email digitado é invalido")
        String email,
        @NotBlank(message = "A senha é obrigatório")
        String password
) {
}
