package com.rocketseat.desafioangularapi.controlllers;

import com.rocketseat.desafioangularapi.dtos.AuthRequestDTO;
import com.rocketseat.desafioangularapi.dtos.LoginSucessfullDTO;
import com.rocketseat.desafioangularapi.dtos.RegisterRequestDTO;
import com.rocketseat.desafioangularapi.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginSucessfullDTO> login(@RequestBody @Valid AuthRequestDTO authRequest) {
        LoginSucessfullDTO loginSucessfullDTO = authService.login(authRequest);
        return ResponseEntity.ok(loginSucessfullDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequestDTO registerRequest) {
        authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Registro realizado com sucesso");
    }
}
