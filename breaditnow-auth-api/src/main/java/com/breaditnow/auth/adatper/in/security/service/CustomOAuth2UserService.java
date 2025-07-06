package com.breaditnow.auth.adatper.in.security.service;

import com.breaditnow.auth.adatper.in.security.AccountContext;
import com.breaditnow.auth.adatper.in.security.oauth2.OAuth2AttributeParser;
import com.breaditnow.auth.domain.model.Account;
import com.breaditnow.auth.domain.model.Provider;
import com.breaditnow.auth.domain.model.SocialAuth;
import com.breaditnow.auth.domain.port.out.LoadAccountPort;
import com.breaditnow.auth.domain.port.out.LoadSocialAuthPort;
import com.breaditnow.auth.domain.port.out.SaveAccountPort;
import com.breaditnow.auth.domain.port.out.SaveSocialAuthPort;
import com.breaditnow.common.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import static com.breaditnow.auth.domain.model.AccountStatus.ACTIVE;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final LoadSocialAuthPort loadSocialAuthPort;
    private final LoadAccountPort loadAccountPort;
    private final SaveSocialAuthPort saveSocialAuthPort;
    private final SaveAccountPort saveAccountPort;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2AttributeParser parsedAttributes = OAuth2AttributeParser.of(registrationId, oAuth2User.getAttributes());
        Provider provider = parsedAttributes.provider();
        String providerId = parsedAttributes.providerId();

        SocialAuth socialAuth = loadSocialAuthPort.findByProviderAndProviderId(provider, providerId)
                .orElse(null);

        Account account;

        if (socialAuth != null) {
            account = loadAccountPort.findById(socialAuth.getAccountId())
                    .orElseThrow(() -> new OAuth2AuthenticationException("연결된 계정을 찾을 수 없습니다."));
        }
        else{
            Account newAccount = Account.builder()
                    .role(Role.CUSTOMER)
                    .status(ACTIVE)
                    .build();
            account = saveAccountPort.save(newAccount);

            SocialAuth newSocialAuth = SocialAuth.builder()
                    .provider(provider)
                    .providerId(providerId)
                    .accountId(account.getId())
                    .build();
            saveSocialAuthPort.save(newSocialAuth);
        }

        String nameAttributeKey = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        return new AccountContext(account, oAuth2User.getAttributes(), nameAttributeKey);
    }
}
