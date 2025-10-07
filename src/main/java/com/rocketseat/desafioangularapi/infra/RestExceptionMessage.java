package com.rocketseat.desafioangularapi.infra;

public record RestExceptionMessage(
        String type,
        String title,
        int status,
        String detail
) {}
