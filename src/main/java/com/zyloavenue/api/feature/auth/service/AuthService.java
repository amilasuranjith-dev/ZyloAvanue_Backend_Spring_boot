package com.zyloavenue.api.feature.auth.service;

import com.zyloavenue.api.common.exception.BadRequestException;
import com.zyloavenue.api.config.security.JwtService;
import com.zyloavenue.api.config.security.UserPrincipal;
import com.zyloavenue.api.feature.auth.dto.LoginRequest;
import com.zyloavenue.api.feature.auth.dto.TokenPairResponse;
import com.zyloavenue.api.config.security.JwtProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;
    private final UserDetailsService userDetailsService;

    public AuthService(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            JwtProperties jwtProperties,
            UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.jwtProperties = jwtProperties;
        this.userDetailsService = userDetailsService;
    }

    public TokenPairResponse login(LoginRequest request) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
            return generateTokenPair(principal);
        } catch (Exception e) {
            throw new BadRequestException("Invalid email or password");
        }
    }

    public TokenPairResponse refreshToken(String refreshToken) {
        try {
            Claims claims = jwtService.parseAndValidate(refreshToken);

            if (!jwtService.isRefreshToken(claims)) {
                throw new BadRequestException("Invalid refresh token");
            }

            String email = claims.getSubject();
            UserPrincipal principal = (UserPrincipal) userDetailsService.loadUserByUsername(email);

            return generateTokenPair(principal);
        } catch (Exception e) {
            throw new BadRequestException("Failed to refresh token: " + e.getMessage());
        }
    }

    private TokenPairResponse generateTokenPair(UserPrincipal principal) {
        String accessToken = jwtService.generateAccessToken(principal);
        String refreshToken = jwtService.generateRefreshToken(principal);

        return TokenPairResponse.builder()
                .tokenType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresInSeconds(jwtProperties.getAccessTtlSeconds())
                .build();
    }
}

