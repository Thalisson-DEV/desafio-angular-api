package com.rocketseat.desafioangularapi.infra;

import com.rocketseat.desafioangularapi.exceptions.*;
import org.springframework.http.*;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, "Dados inválidos.");
        problemDetail.setType(URI.create("/errors/validation-failed"));
        problemDetail.setTitle("Erro de validação");
        
        String detail = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        
        problemDetail.setDetail(detail);

        return new ResponseEntity<>(problemDetail, status);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ProblemDetail handleProductNotFound(ProductNotFoundException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
        problemDetail.setType(URI.create("/errors/product-not-found"));
        problemDetail.setTitle("Produto Não Encontrado.");
        return problemDetail;
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ProblemDetail handleEmailAlreadyExists(EmailAlreadyExistsException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
        problemDetail.setType(URI.create("/errors/email-already-exists"));
        problemDetail.setTitle("Email já cadastrado.");
        return problemDetail;
    }

    @ExceptionHandler(TokenGenerationException.class)
    public ProblemDetail handleTokenGeneration(TokenGenerationException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_GATEWAY, exception.getMessage());
        problemDetail.setType(URI.create("/errors/token-generation-failure"));
        problemDetail.setTitle("Erro ao gerar token.");
        return problemDetail;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ProblemDetail handleUsernameNotFound(UsernameNotFoundException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());
        problemDetail.setType(URI.create("/errors/user-not-found"));
        problemDetail.setTitle("Usuario não cadastrado");
        return problemDetail;
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ProblemDetail handleInternalAuth(InternalAuthenticationServiceException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
        problemDetail.setType(URI.create("/errors/internal-authentication-error"));
        problemDetail.setTitle("Erro ao autenticar.");
        return problemDetail;
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ProblemDetail handleInvalidToken(InvalidTokenException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, exception.getMessage());
        problemDetail.setType(URI.create("/errors/token-validation-failure"));
        problemDetail.setTitle("Token invalido.");
        return problemDetail;
    }

    @ExceptionHandler(NoProductsFoundException.class)
    public ProblemDetail handleNoProductsFound(NoProductsFoundException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
        problemDetail.setType(URI.create("/errors/no-products-found"));
        problemDetail.setTitle("Nenhum produto encontrado.");
        return problemDetail;
    }
}
