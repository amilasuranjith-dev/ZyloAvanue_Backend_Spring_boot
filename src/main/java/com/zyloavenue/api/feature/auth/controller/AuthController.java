package com.zyloavenue.api.feature.auth.controller;

import com.zyloavenue.api.feature.auth.dto.LoginRequest;
import com.zyloavenue.api.feature.auth.dto.RefreshRequest;
import com.zyloavenue.api.feature.auth.dto.TokenPairResponse;
import com.zyloavenue.api.common.api.ApiResponse;
import com.zyloavenue.api.config.security.JwtProperties;
import com.zyloavenue.api.config.security.JwtService;
import com.zyloavenue.api.config.security.UserPrincipal;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zyloavenue.api.common.exception.ApiError;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final JwtProperties jwtProperties;
	private final UserDetailsService userDetailsService;

	public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, JwtProperties jwtProperties, UserDetailsService userDetailsService) {
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.jwtProperties = jwtProperties;
		this.userDetailsService = userDetailsService;
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<TokenPairResponse>> login(@Valid @RequestBody LoginRequest req) {
		Authentication auth = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
		);
		UserPrincipal principal = (UserPrincipal) auth.getPrincipal();

		String access = jwtService.generateAccessToken(principal);
		String refresh = jwtService.generateRefreshToken(principal);
		TokenPairResponse resp = TokenPairResponse.builder()
				.tokenType("Bearer")
				.accessToken(access)
				.refreshToken(refresh)
				.expiresInSeconds(jwtProperties.getAccessTtlSeconds())
				.build();

		return ResponseEntity.ok(ApiResponse.ok(resp));
	}

	@PostMapping("/refresh")
	public ResponseEntity<ApiResponse<TokenPairResponse>> refresh(@Valid @RequestBody RefreshRequest req) {
		Claims claims = jwtService.parseAndValidate(req.getRefreshToken());
		if (!jwtService.isRefreshToken(claims)) {
			return ResponseEntity.badRequest().body(ApiResponse.error(
					ApiError.of("BAD_TOKEN", "Invalid refresh token")
			));
		}

		String email = claims.getSubject();
		UserPrincipal principal = (UserPrincipal) userDetailsService.loadUserByUsername(email);

		String access = jwtService.generateAccessToken(principal);
		String refresh = jwtService.generateRefreshToken(principal);
		TokenPairResponse resp = TokenPairResponse.builder()
				.tokenType("Bearer")
				.accessToken(access)
				.refreshToken(refresh)
				.expiresInSeconds(jwtProperties.getAccessTtlSeconds())
				.build();

		return ResponseEntity.ok(ApiResponse.ok(resp));
	}
}

