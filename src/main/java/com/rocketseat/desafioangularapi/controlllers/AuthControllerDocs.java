package com.rocketseat.desafioangularapi.controlllers;

import com.rocketseat.desafioangularapi.dtos.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

@Tag(name = "Authentication", description = "Operations pertaining to user authentication and profile")
public interface AuthControllerDocs {

    @Operation(summary = "Authenticate user and get access token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully authenticated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginSucessfullDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid credentials or bad request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    ResponseEntity<LoginSucessfullDTO> login(AuthRequestDTO authRequest);

    @Operation(summary = "Register a new user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account successfully created",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid data or user already exists",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    ResponseEntity<String> register(RegisterRequestDTO registerRequest);

    @Operation(summary = "Get the profile of the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserProfileDTO.class))),
            @ApiResponse(responseCode = "403", description = "Access denied or invalid token",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    ResponseEntity<UserProfileDTO> userProfile();

    @Operation(summary = "Validate JWT Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token validation result",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenValidationDTO.class))),
            @ApiResponse(responseCode = "401", description = "Token is missing or malformed",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    ResponseEntity<TokenValidationDTO> validarToken(String token);
}
