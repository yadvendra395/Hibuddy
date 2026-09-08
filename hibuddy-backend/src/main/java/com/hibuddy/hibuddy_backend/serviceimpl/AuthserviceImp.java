package com.hibuddy.hibuddy_backend.serviceimpl;

import com.hibuddy.hibuddy_backend.dto.auth.LoginRequestDTO;
import com.hibuddy.hibuddy_backend.dto.auth.LoginResponseDTO;
import com.hibuddy.hibuddy_backend.dto.auth.RegisterRequestDTO;
import com.hibuddy.hibuddy_backend.dto.auth.RegisterResponseDTO;
import com.hibuddy.hibuddy_backend.entity.User;
import com.hibuddy.hibuddy_backend.repository.UserRepository;
import com.hibuddy.hibuddy_backend.security.JwtService;
import com.hibuddy.hibuddy_backend.service.Authservice;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthserviceImp implements Authservice {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;


    @Override
    public RegisterResponseDTO register(RegisterRequestDTO request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        User savedUser = userRepository.save(user);

        return new RegisterResponseDTO(
                savedUser.getUserId(),
                savedUser.getUsername(),
                savedUser.getEmail()
        );
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(
                        request.getEmail()
                );

        String token =
                jwtService.generateToken(userDetails.getUsername());

        return new LoginResponseDTO(token);
    }
}