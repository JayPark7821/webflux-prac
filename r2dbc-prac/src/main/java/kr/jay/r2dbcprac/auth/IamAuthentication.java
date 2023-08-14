package kr.jay.r2dbcprac.auth;

import java.security.Principal;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import lombok.RequiredArgsConstructor;

/**
 * IamAuthentication
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/20
 */

@RequiredArgsConstructor
public class IamAuthentication implements Authentication {
	private final String name;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getDetails() {
		return null;
	}

	@Override
	public Principal getPrincipal() {
		return new Principal() {
			@Override
			public String getName() {
				return name;
			}
		};
	}

	@Override
	public boolean isAuthenticated() {
		return true;
	}

	@Override
	public void setAuthenticated(final boolean isAuthenticated) throws IllegalArgumentException {

	}

	@Override
	public String getName() {
		return name;
	}
}
