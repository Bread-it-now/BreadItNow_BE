package com.breaditnow.auth.global.security.direct.service;

import static com.breaditnow.auth.global.exception.AuthErrorCode.*;
import static com.breaditnow.auth.global.security.Role.*;

import java.util.Collections;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.breaditnow.auth.global.security.AccountContext;
import com.breaditnow.domain.domain.owner.entity.Owner;
import com.breaditnow.domain.domain.owner.repository.OwnerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOwnerDetailsService implements UserDetailsService {
	private final OwnerRepository ownerRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Owner owner = ownerRepository.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException(EMAIL_NOT_FOUND.name()));

		List<SimpleGrantedAuthority> authorities = Collections.singletonList(
			new SimpleGrantedAuthority("ROLE_" + OWNER.name()));

		return AccountContext.ofDirect(owner.getId(), email, owner.getPassword(), authorities);
	}
}
