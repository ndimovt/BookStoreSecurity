package io.github.ndimovt.RelationTesting.service;

import io.github.ndimovt.RelationTesting.model.User;
import io.github.ndimovt.RelationTesting.model.dtos.LogInUserDto;
import io.github.ndimovt.RelationTesting.model.dtos.RegisterUserDto;
import io.github.ndimovt.RelationTesting.repository.UserRepository;
import io.github.ndimovt.RelationTesting.roles.Roles;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * The class AuthenticationService
 */
@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Instantiates AuthenticationService
     * @param userRepository UserRepository object
     * @param passwordEncoder PasswordEncoder object
     * @param authenticationManager AuthenticationManager object
     */

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Creates User instance with fetched parameters and saves it into database using userRepository interface
     * @param input RegisterUserDto object
     * @return User
     */
    public User signup(RegisterUserDto input){
        User user = new User();
        user.setId(input.getId());
        user.setName(input.getName());
        user.setUsername(input.getUsername());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setCreatedAt(input.getCreatedAt());
        user.setRole(Roles.USER.name());

        return userRepository.save(user);
    }

    /**
     * Authenticates user by given info. May throw exception if provided info is incorrect.
     * @param logInUserDto LogInUserDto object
     * @return UserRepository object
     */
    public User authenticate(LogInUserDto logInUserDto){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        logInUserDto.getUsername(),
                        logInUserDto.getPassword()
                )
        );
        return userRepository.findByUsername(logInUserDto.getUsername()).orElseThrow();
    }
}
