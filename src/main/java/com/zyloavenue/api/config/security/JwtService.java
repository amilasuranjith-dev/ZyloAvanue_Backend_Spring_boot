package com.zyloavenue.api.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {
	private final JwtProperties props;

	public JwtService(JwtProperties props) {
		this.props = props;
	}

	public String generateAccessToken(UserPrincipal principal) {
		Instant now = Instant.now();
		return Jwts.builder()
				.issuer(props.getIssuer())
				.subject(principal.getUsername())
				.claim("uid", principal.getId())
				.claim("roles", principal.getAuthorities().stream().map(a -> a.getAuthority()).toList())
				.claim("typ", "access")
				.issuedAt(Date.from(now))
				.expiration(Date.from(now.plusSeconds(props.getAccessTtlSeconds())))
				.signWith(key(), Jwts.SIG.HS256)
				.compact();
	}

	public String generateRefreshToken(UserPrincipal principal) {
		Instant now = Instant.now();
		return Jwts.builder()
				.issuer(props.getIssuer())
				.subject(principal.getUsername())
				.claim("uid", principal.getId())
				.claim("typ", "refresh")
				.issuedAt(Date.from(now))
				.expiration(Date.from(now.plusSeconds(props.getRefreshTtlSeconds())))
				.signWith(key(), Jwts.SIG.HS256)
				.compact();
	}

	public Claims parseAndValidate(String jwt) {
		return Jwts.parser()
				.verifyWith(key())
				.build()
				.parseSignedClaims(jwt)
				.getPayload();
	}

	public boolean isAccessToken(Claims claims) {
		return "access".equals(String.valueOf(claims.get("typ")));
	}

	public boolean isRefreshToken(Claims claims) {
		return "refresh".equals(String.valueOf(claims.get("typ")));
	}

	@SuppressWarnings("unchecked")
	public List<String> roles(Claims claims) {
		Object v = claims.get("roles");
		if (v instanceof List<?> list) {
			return list.stream().map(String::valueOf).toList();
		}
		return List.of();
	}

	private SecretKey key() {
		return Keys.hmacShaKeyFor(props.getSecret().getBytes(StandardCharsets.UTF_8));
	}
}
