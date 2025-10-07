package com.rocketseat.desafioangularapi.services;

import com.rocketseat.desafioangularapi.repositories.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    private final UsersRepository usersRepository;

    public AuthorizationService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserDetails user = usersRepository.findByEmail(login);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado para o email: " + login);
        }
        return user;
    }
}
