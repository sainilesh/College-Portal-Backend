package com.example.System.Authentication;

import com.example.System.Enum.RoleType;
import com.example.System.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;


@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GoogleAccountService  googleAccountService;


    @Override
    public void onAuthenticationSuccess(@NonNull HttpServletRequest request,
                                        @NonNull HttpServletResponse response,
                                        @NonNull Authentication authentication) throws IOException {

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                oauthToken.getAuthorizedClientRegistrationId(),
                oauthToken.getName()
        );

        String accessToken = client.getAccessToken().getTokenValue();

        String refreshToken = null;
        if (client.getRefreshToken() != null) {
            refreshToken = client.getRefreshToken().getTokenValue();
        }

        Instant expiry = client.getAccessToken().getExpiresAt();

        Map<String, Object> attributes = oauthToken.getPrincipal().getAttributes();
        String email = (String) attributes.get("email");

        Long userId = (Long) request.getSession().getAttribute("userId");
        RoleType role = getCurrentUserRole(email);

        googleAccountService.saveOrUpdate(
                email,
                accessToken,
                refreshToken,
                expiry,
                2L,
                role
        );

        request.getSession().removeAttribute("userId");

        response.sendRedirect("http://localhost:8080/api/teacher/dashboard");
    }

    private RoleType getCurrentUserRole(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new
                EntityNotFoundException("user not found")).getRoles();
    }
}