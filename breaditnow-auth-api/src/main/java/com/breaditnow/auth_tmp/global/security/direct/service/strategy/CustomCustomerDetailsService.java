//package com.breaditnow.auth_tmp.global.security.direct.service.strategy;
//
//import static com.breaditnow.auth_tmp.global.security.Role.*;
//
//import java.util.Collections;
//import java.util.List;
//
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.breaditnow.auth_tmp.global.security.AccountContext;
//import com.breaditnow.domain.domain.customer.entity.Customer;
//import com.breaditnow.domain.domain.customer.repository.CustomerRepository;
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class CustomCustomerDetailsService implements DirectUserDetailsService {
//	private final CustomerRepository customerRepository;
//
//	@Override
//	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//		Customer customer = customerRepository.findByEmail(email)
//			.orElseThrow(() -> new UsernameNotFoundException(EMAIL_NOT_FOUND.name()));
//
//		List<SimpleGrantedAuthority> authorities = Collections.singletonList(
//			new SimpleGrantedAuthority(toAuthority(CUSTOMER)));
//		return AccountContext.ofDirect(customer.getId(), email, customer.getPassword(), authorities);
//	}
//
//	@Override
//	public String getRole() {
//		return CUSTOMER.name();
//	}
//}
