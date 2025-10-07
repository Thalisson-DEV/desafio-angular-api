package com.rocketseat.desafioangularapi.dtos;

public record AuthResponseDTO(
        String token,
        Double expiresIn,
        UserDTO user
) {
}
