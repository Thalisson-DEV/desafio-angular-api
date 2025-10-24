package com.rocketseat.desafioangularapi.infra;

import com.rocketseat.desafioangularapi.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    private ResponseEntity<RestExceptionMessage> productNotFound(ProductNotFoundException exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        RestExceptionMessage restExceptionMessage = new RestExceptionMessage(
                "/errors/product-not-found",
                "Produto Não Encontrado.",
                status.value(),
                exception.getMessage()
        );

        return new ResponseEntity<>(restExceptionMessage, status);
    }


    @ExceptionHandler(EmailAlreadyExistsException.class)
    private ResponseEntity<RestExceptionMessage> emailAlreadyExists(EmailAlreadyExistsException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        RestExceptionMessage restExceptionMessage = new RestExceptionMessage(
                "/erros/email-already-exists",
                "Email já cadastrado.",
                status.value(),
                exception.getMessage()
        );

        return new ResponseEntity<>(restExceptionMessage, status);
    }

    @ExceptionHandler(TokenGenerationException.class)
    private ResponseEntity<RestExceptionMessage> tokenGeneration(TokenGenerationException exception) {
        HttpStatus status = HttpStatus.BAD_GATEWAY;

        RestExceptionMessage restExceptionMessage = new RestExceptionMessage(
                "/erros/token-generation-failure",
                "Erro ao gerar token.",
                status.value(),
                exception.getMessage()
        );

        return new ResponseEntity<>(restExceptionMessage, status);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    private ResponseEntity<RestExceptionMessage> usernameNotFound(UsernameNotFoundException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        RestExceptionMessage restExceptionMessage = new RestExceptionMessage(
                "/erros/user-not-found",
                "Usuario não cadastrado",
                status.value(),
                exception.getMessage()
        );

        return new ResponseEntity<>(restExceptionMessage, status);
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    private ResponseEntity<RestExceptionMessage> internalAuthenticationServiceException(InternalAuthenticationServiceException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        RestExceptionMessage restExceptionMessage = new RestExceptionMessage(
                "/erros/internal-authentication-error",
                "Erro ao autenticar.",
                status.value(),
                exception.getMessage()
        );

        return new ResponseEntity<>(restExceptionMessage, status);
    }

    @ExceptionHandler(InvalidTokenException.class)
    private ResponseEntity<RestExceptionMessage> tokenInvalid(InvalidTokenException exception) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        RestExceptionMessage restExceptionMessage = new RestExceptionMessage(
                "/erros/token-validation-failure",
                "Token invalido.",
                status.value(),
                exception.getMessage()
        );

        return new ResponseEntity<>(restExceptionMessage, status);
    }

    @ExceptionHandler(NoProductsFoundException.class)
    private ResponseEntity<RestExceptionMessage> noProductsFound(NoProductsFoundException exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        RestExceptionMessage restExceptionMessage = new RestExceptionMessage(
                "/erros/no-products-found",
                "Nenhum produto encontrado.",
                status.value(),
                exception.getMessage()
        );

        return new ResponseEntity<>(restExceptionMessage, status);
    }
}
