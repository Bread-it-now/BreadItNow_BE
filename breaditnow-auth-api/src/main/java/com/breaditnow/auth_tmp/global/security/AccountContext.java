//package com.breaditnow.auth_tmp.global.security;
//
//import static com.breaditnow.auth_tmp.global.security.Role.*;
//
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//
//import com.breaditnow.common.exception.AuthErrorCode;
//
//import lombok.Builder;
//import lombok.Getter;
//import lombok.ToString;
//
//@Builder
//@Getter
//@ToString
//public class AccountContext implements UserDetails, OAuth2User {
//
//	private Long userId;
//	private List<SimpleGrantedAuthority> roles;
//
//	private String email;
//	private String password;
//
//	private String oauth2Id;
//	private Map<String, Object> attributes;
//
//	public static AccountContext ofDirect(Long userId, String email, String password,
//		List<SimpleGrantedAuthority> roles) {
//		return AccountContext.builder()
//			.userId(userId)
//			.email(email)
//			.password(password)
//			.roles(roles)
//			.build();
//	}
//
//	public static AccountContext of(Long userId, List<SimpleGrantedAuthority> roles) {
//		return AccountContext.builder()
//			.userId(userId)
//			.roles(roles)
//			.build();
//	}
//
//	public static AccountContext ofOAuth2(Long userId, String oauth2Id, Map<String, Object> attributes) {
//		return AccountContext.builder()
//			.userId(userId)
//			.oauth2Id(oauth2Id)
//			.attributes(attributes)
//			.roles(Collections.singletonList(new SimpleGrantedAuthority(toAuthority(CUSTOMER))))
//			.build();
//	}
//
//	public String getRole() {
//		return getAuthorities().stream()
//			.findFirst()
//			.map(auth -> auth.getAuthority().replace("ROLE_", ""))
//			.orElseThrow(() -> new BadCredentialsException(AuthErrorCode.ROLE_INVALID.name()));
//	}
//
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return roles;
//	}
//
//	@Override
//	public String getPassword() {
//		return this.password;
//	}
//
//	@Override
//	public String getUsername() {
//		return this.email != null ? this.email : this.oauth2Id;
//	}
//
//	@Override
//	public Map<String, Object> getAttributes() {
//		return this.attributes;
//	}
//
//	@Override
//	public String getName() {
//		return this.oauth2Id;
//	}
//
//}
