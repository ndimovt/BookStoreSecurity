package io.github.ndimovt.RelationTesting.controller;

import io.github.ndimovt.RelationTesting.model.User;
import io.github.ndimovt.RelationTesting.model.dtos.LogInUserDto;
import io.github.ndimovt.RelationTesting.model.dtos.RegisterUserDto;
import io.github.ndimovt.RelationTesting.response.LoginResponse;
import io.github.ndimovt.RelationTesting.service.AuthenticationService;
import io.github.ndimovt.RelationTesting.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto){
        User registerUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(registerUser);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LogInUserDto input){
        User user = authenticationService.authenticate(input);
        String jwtToken = jwtService.generateToken(user);
        LoginResponse response = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getJwtExpiration());
        return ResponseEntity.ok(response);
    }
}