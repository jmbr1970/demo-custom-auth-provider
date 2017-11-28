package com.jblesa.demo.authprovider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class CustomAuthenticationProvider implements AuthenticationProvider {

	private static List<User> users = new ArrayList<>();

	public CustomAuthenticationProvider() {
		users.add(new User("jblesa", "123456", "ROLE_ADMIN", "PUBLISH"));
		users.add(new User("ainoa", "123456", "ROLE_USER", "NO_PUBLISH"));
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String name = authentication.getName();
		Object credentials = authentication.getCredentials();

		System.out.println("credentials class: " + credentials.getClass());
		if (!(credentials instanceof String)) {
			return null;
		}

		String password = credentials.toString();

		Optional<User> user = users.stream().filter(u -> u.match(name, password)).findFirst();

		if (!user.isPresent()) {
			throw new BadCredentialsException("Authentication failed for " + name);
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

		user.get().getGrants().stream().forEach(u -> grantedAuthorities.add(new SimpleGrantedAuthority(u)));

		Authentication auth = new UsernamePasswordAuthenticationToken(name, password, grantedAuthorities);

		return auth;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
