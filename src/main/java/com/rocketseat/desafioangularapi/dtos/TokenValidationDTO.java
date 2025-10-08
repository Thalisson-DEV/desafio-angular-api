package com.rocketseat.desafioangularapi.dtos;

public record TokenValidationDTO(
        String message,
        UserDTO user,
        Integer iat,
        Integer exp
) {
}
