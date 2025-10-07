package com.rocketseat.desafioangularapi.mappers;

import com.rocketseat.desafioangularapi.dtos.ProductRequestDTO;
import com.rocketseat.desafioangularapi.dtos.RegisterRequestDTO;
import com.rocketseat.desafioangularapi.dtos.UserDTO;
import com.rocketseat.desafioangularapi.entitys.ProductStatus;
import com.rocketseat.desafioangularapi.entitys.Products;
import com.rocketseat.desafioangularapi.entitys.Users;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toUserDTO(Users users) {
        if (users == null) {
            return null;
        }

        return new UserDTO(
                users.getEmail()
        );
    }

    public Users toUserEntity (RegisterRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Users user = new Users();
        user.setEmail(dto.email());
        user.setPassword(dto.password());

        return user;
    }
}
