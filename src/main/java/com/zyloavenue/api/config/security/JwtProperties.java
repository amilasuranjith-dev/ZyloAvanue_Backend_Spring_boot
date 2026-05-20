package com.zyloavenue.api.config.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.jwt")
public class JwtProperties {
	private String issuer;
	private String secret;
	private long accessTtlSeconds;
	private long refreshTtlSeconds;
}
