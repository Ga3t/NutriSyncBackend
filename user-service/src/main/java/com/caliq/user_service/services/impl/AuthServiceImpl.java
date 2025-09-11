package com.caliq.user_service.services.impl;

import com.caliq.user_service.dto.AuthResponseDto;
import com.caliq.user_service.dto.LoginDto;
import com.caliq.user_service.dto.RegistrationDto;
import com.caliq.user_service.exceptions.EmailAllreadyTakenException;
import com.caliq.user_service.exceptions.UserNotFoundException;
import com.caliq.user_service.exceptions.UsernameAllreadyTakenException;
import com.caliq.user_service.models.UserEntity;
import com.caliq.user_service.models.enums.Roles;
import com.caliq.user_service.repository.UserRepository;
import com.caliq.user_service.security.JwtGenerator;
import com.caliq.user_service.security.RefreshTokenGenerator;
import com.caliq.user_service.services.AuthService;
import com.caliq.user_service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@Primary
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private JwtGenerator jwtGenerator;
    private PasswordEncoder passwordEncoder;
    private RefreshTokenGenerator refreshTokenGenerator;
    private UserService userService;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, JwtGenerator jwtGenerator, PasswordEncoder passwordEncoder, RefreshTokenGenerator refreshTokenGenerator,UserService userService ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtGenerator = jwtGenerator;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenGenerator = refreshTokenGenerator;
        this.userService = userService;
    }

    @Override
    @Transactional
    public AuthResponseDto authenticateUser(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getLogin(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserEntity user = userService.findByUsername(loginDto.getLogin());
        Roles role = user.getRole();
        String jwtToken = jwtGenerator.generateToken(authentication, user.getUserId(), role);
        String refreshToken = refreshTokenGenerator.generateRefreshToken(user.getUserId());

        return new AuthResponseDto(jwtToken, refreshToken);
    }

    @Override
    @Transactional
    public String registrateUser(RegistrationDto registrationDto) {

        if(userRepository.existsByUsername(registrationDto.getUsername()))
            throw new UsernameAllreadyTakenException("Username already taken!");
        if(userRepository.existsByEmail(registrationDto.getEmail()))
            throw new EmailAllreadyTakenException("Email already taken!");
        UserEntity userNew = new UserEntity();

        userNew.setUsername(registrationDto.getUsername());
        userNew.setEmail(registrationDto.getEmail());
        userNew.setPassword(passwordEncoder.encode((registrationDto.getPassword())));
        userNew.setRegistrationDate(LocalDateTime.now());
        userNew.setRole(Roles.USER);
        userRepository.save(userNew);

        return "User registered successfully!";
    }
}
