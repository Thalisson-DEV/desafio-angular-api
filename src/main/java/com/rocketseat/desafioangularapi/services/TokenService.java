package com.rocketseat.desafioangularapi.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.rocketseat.desafioangularapi.entitys.Users;
import com.rocketseat.desafioangularapi.exceptions.TokenGenerationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token}")
    private String secret;

    public String generateToken(Users user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("API-Desafio-Angular")
                    .withSubject(user.getEmail())
                    .withExpiresAt(genExpirationDate())
                    .withIssuedAt(Instant.now())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new TokenGenerationException("Erro durante a geração do token.");
        }
    }

    public String validadeToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("API-Desafio-Angular")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
