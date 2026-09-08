package com.hibuddy.hibuddy_backend.service;

import com.hibuddy.hibuddy_backend.dto.auth.LoginRequestDTO;
import com.hibuddy.hibuddy_backend.dto.auth.LoginResponseDTO;
import com.hibuddy.hibuddy_backend.dto.auth.RegisterRequestDTO;
import com.hibuddy.hibuddy_backend.dto.auth.RegisterResponseDTO;

public interface Authservice {

    RegisterResponseDTO register(RegisterRequestDTO request);

    LoginResponseDTO login(LoginRequestDTO request);
}