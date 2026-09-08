package com.hibuddy.hibuddy_backend.controller;

import com.hibuddy.hibuddy_backend.dto.auth.LoginRequestDTO;
import com.hibuddy.hibuddy_backend.dto.auth.LoginResponseDTO;
import com.hibuddy.hibuddy_backend.dto.auth.RegisterRequestDTO;
import com.hibuddy.hibuddy_backend.dto.auth.RegisterResponseDTO;
import com.hibuddy.hibuddy_backend.service.Authservice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class Authcontroller {

    private final Authservice authservice;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(
            @RequestBody RegisterRequestDTO request) {

        RegisterResponseDTO response =
                authservice.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO request) {

        LoginResponseDTO response =
                authservice.login(request);

        return ResponseEntity.ok(response);
    }
}