package com.rocketseat.desafioangularapi.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.rocketseat.desafioangularapi.dtos.*;
import com.rocketseat.desafioangularapi.entitys.Users;
import com.rocketseat.desafioangularapi.exceptions.EmailAlreadyExistsException;
import com.rocketseat.desafioangularapi.exceptions.InvalidTokenException;
import com.rocketseat.desafioangularapi.mappers.UserMapper;
import com.rocketseat.desafioangularapi.repositories.UsersRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

   private final AuthenticationManager authenticationManager;
   private final UsersRepository usersRepository;
   private final TokenService tokenService;
   private final UserMapper userMapper;

    public AuthService(AuthenticationManager authenticationManager, UsersRepository usersRepository, TokenService tokenService, UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.usersRepository = usersRepository;
        this.tokenService = tokenService;
        this.userMapper = userMapper;
    }

    public LoginSucessfullDTO login(@NotNull AuthRequestDTO authRequest) {
        if (!usersRepository.existsByEmail(authRequest.email())) {
            throw new UsernameNotFoundException("O email " + authRequest.email() + " não possui cadastro");
        }

        var usernamePassword = new UsernamePasswordAuthenticationToken(
                authRequest.email(),
                authRequest.password()
        );
        var auth = authenticationManager.authenticate(usernamePassword);

        Users userAuthenticated = (Users) auth.getPrincipal();
        String token = tokenService.generateToken(userAuthenticated);

        UserDTO userDTO = userMapper.toUserDTO(userAuthenticated);
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

    @Cacheable(
            value = "profile",
            key = "T(org.springframework.security.core.context.SecurityContextHolder).getContext().getAuthentication().getPrincipal().getId()"
    )
    public UserProfileDTO profile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDTO userDTO = userMapper.toUserDTO((Users) auth.getPrincipal());

        return new UserProfileDTO(
                "Perfil do usuário",
                userDTO
        );
    }

    public TokenValidationDTO validateToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        String usuario = tokenService.validadeToken(token);
        if (usuario.isEmpty()) {
            throw new InvalidTokenException("Token invalido e/ou expirado, execute o login novamente.");
        }

        Users user = (Users) usersRepository.findByEmail(usuario);
        if (user == null) {
            throw new UsernameNotFoundException(usuario);
        }
        UserDTO userDTO = userMapper.toUserDTO(user);

        DecodedJWT decodedJWT = JWT.decode(token);

        Integer iat = decodedJWT.getIssuedAt() != null ? (int) (decodedJWT.getIssuedAt().getTime() / 1000) : null;
        Integer exp = decodedJWT.getExpiresAt() != null ? (int) (decodedJWT.getExpiresAt().getTime() / 1000) : null;

        return new TokenValidationDTO(
                "Acesso autorizado!",
                userDTO,
                iat,
                exp
        );
    }
}
