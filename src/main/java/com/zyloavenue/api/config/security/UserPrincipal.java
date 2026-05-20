package com.zyloavenue.api.config.security;

import com.zyloavenue.api.feature.user.entity.UserEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Getter
public class UserPrincipal implements UserDetails {
	private final Long id;
	private final String email;
	private final String passwordHash;
	private final boolean enabled;
	private final Set<GrantedAuthority> authorities;

	public UserPrincipal(Long id, String email, String passwordHash, boolean enabled, Set<GrantedAuthority> authorities) {
		this.id = id;
		this.email = email;
		this.passwordHash = passwordHash;
		this.enabled = enabled;
		this.authorities = authorities;
	}

	public static UserPrincipal from(UserEntity user) {
		Set<GrantedAuthority> auths = user.getRoles().stream()
				.map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName().name()))
				.collect(java.util.stream.Collectors.toSet());
		return new UserPrincipal(user.getId(), user.getEmail(), user.getPasswordHash(), user.isEnabled(), auths);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return passwordHash;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
}
