package com.rocketseat.desafioangularapi.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
        @NotBlank(message = "O email é obrigatório.")
        @Email(message = "O email digitado é inválido.")
        String email,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 4, max = 12, message = "A senha deve ter entre 4 e 12 caracteres.")
        String password
) {
}
