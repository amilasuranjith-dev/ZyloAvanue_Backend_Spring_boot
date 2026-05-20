package com.zyloavenue.api.feature.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TokenPairResponse {
	private String tokenType;
	private String accessToken;
	private String refreshToken;
	private long expiresInSeconds;
}

