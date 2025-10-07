package com.rocketseat.desafioangularapi.services;

import com.rocketseat.desafioangularapi.dtos.*;
import com.rocketseat.desafioangularapi.entitys.Users;
import com.rocketseat.desafioangularapi.exceptions.EmailAlreadyExistsException;
import com.rocketseat.desafioangularapi.repositories.UsersRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

   private final AuthenticationManager authenticationManager;
   private final UsersRepository usersRepository;
   private final TokenService tokenService;

    public AuthService(AuthenticationManager authenticationManager, UsersRepository usersRepository, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.usersRepository = usersRepository;
        this.tokenService = tokenService;
    }

    public LoginSucessfullDTO login(@NotNull AuthRequestDTO authRequest) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(
                authRequest.email(),
                authRequest.password()
        );
        var auth = this.authenticationManager.authenticate(usernamePassword);

        Users userAuthenticated = (Users) auth.getPrincipal();
        String token = tokenService.generateToken(userAuthenticated);

        UserDTO userDTO = new UserDTO(
                userAuthenticated.getUsername()
        );
        AuthResponseDTO authResponseDTO = new AuthResponseDTO(
                token,
                3600.0,
                userDTO
        );

        return new LoginSucessfullDTO(
                "Login efetuado com sucesso",
                authResponseDTO
        );
    }

    public void register(@NotNull RegisterRequestDTO registerRequest) {
        if (usersRepository.findByEmail(registerRequest.email()) != null) {
            throw new EmailAlreadyExistsException("O email já esta cadastrado");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerRequest.password());
        Users newUser = new Users(registerRequest.email(), encryptedPassword);

        usersRepository.save(newUser);
    }
}
