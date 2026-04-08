package com.rocketseat.desafioangularapi.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequestDTO(
        @NotBlank(message = "O email é obrigatório")
        @Email(message = "O email digitado é invalido")
        String email,
        @NotBlank(message = "A senha é obrigatório")
        @Size(min = 4, max = 12, message = "A senha deve conter entre 4 e 12 caracteres")
        String password
) {
}
