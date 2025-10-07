package com.rocketseat.desafioangularapi.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException() {
        super("O email já esta em uso.");
    }

    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
