package com.caliq.user_service.controllers;


import com.caliq.user_service.dto.AuthResponseDto;
import com.caliq.user_service.dto.LoginDto;
import com.caliq.user_service.dto.RegistrationDto;
import com.caliq.user_service.services.AuthService;
import com.caliq.user_service.services.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
        AuthResponseDto authResponseDto = authService.authenticateUser(loginDto);
        return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
    }

    @PostMapping("registration")
    public ResponseEntity<String> registration(@RequestBody RegistrationDto registrationDto){
        String response = authService.registrateUser(registrationDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("refreshtoken")
    public ResponseEntity<AuthResponseDto> refreshtoken (@CookieValue("refresh_token") String refreshToken){
        return new ResponseEntity<>(authService.refreshToken(refreshToken), HttpStatus.OK);
    }
}
