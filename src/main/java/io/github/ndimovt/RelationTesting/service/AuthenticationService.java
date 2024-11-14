package io.github.ndimovt.RelationTesting.service;

import io.github.ndimovt.RelationTesting.model.User;
import io.github.ndimovt.RelationTesting.model.dtos.LogInUserDto;
import io.github.ndimovt.RelationTesting.model.dtos.RegisterUserDto;
import io.github.ndimovt.RelationTesting.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }
    public User signup(RegisterUserDto input){
        User user = new User();
        user.setId(input.getId());
        user.setName(input.getName());
        user.setUsername(input.getUsername());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setCreatedAt(input.getCreatedAt());

        return userRepository.save(user);
    }
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
