package com.breaditnow.auth.adatper.in.security;

import com.breaditnow.auth.domain.model.Account;
import com.breaditnow.common.domain.Role;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static com.breaditnow.auth.domain.model.AccountStatus.DELETED;

@Getter
public class AccountContext implements UserDetails, OAuth2User {
    private final Account account;
    private final String password;
    private String providerId;
    private Map<String, Object> attributes;

    public AccountContext(Account account, String password) {
        this.account = account;
        this.password = password;
    }

    public AccountContext(Account account, String providerId, Map<String, Object> attributes) {
        this.account = account;
        this.password = null;
        this.providerId = providerId;
        this.attributes = attributes;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return String.valueOf(account.getId());
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role role = account.getRole();
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return account.getStatus() != DELETED;
    }

    @Override
    public boolean isAccountNonLocked() {
        return account.isActive();
    }

    @Override
    public boolean isEnabled() {
        return account.isActive();
    }

    @Override
    public String getName() {
        return this.providerId;
    }
}
