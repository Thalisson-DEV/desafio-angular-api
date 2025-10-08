package com.rocketseat.desafioangularapi.repositories;

import com.rocketseat.desafioangularapi.entitys.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsersRepository extends JpaRepository<Users, Long> {

    UserDetails findByEmail(String login);

    boolean existsByEmail(String email);
}
