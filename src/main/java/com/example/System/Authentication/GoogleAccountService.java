package com.example.System.Authentication;

import com.example.System.Entity.GoogleAccount;
import com.example.System.Enum.RoleType;
import com.example.System.Repository.GoogleAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GoogleAccountService {

    private final GoogleAccountRepository repository;

    public void saveOrUpdate(String email,
                             String accessToken,
                             String refreshToken,
                             Instant expiryTime,
                             Long userId,
                             RoleType role) {

        GoogleAccount account = repository.findByEmail(email)
                .orElse(new GoogleAccount());

        account.setEmail(email);
        account.setAccessToken(accessToken);

        if (refreshToken != null) {
            account.setRefreshToken(refreshToken);
        }

        account.setExpiryTime(expiryTime);
        account.setUserId(userId);
        account.setRole(role);
        account.setUpdatedAt(Instant.now());

        repository.save(account);
    }

    public String getValidAccessToken(Long userId) {

        GoogleAccount account = repository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Google account not connected"));

        if (account.getExpiryTime().isAfter(Instant.now().plusSeconds(60))) {
            return account.getAccessToken();
        }

        return refreshAccessToken(account);
    }

    private String refreshAccessToken(GoogleAccount account) {

        RestTemplate restTemplate = new RestTemplate();

        String url = "https://oauth2.googleapis.com/token";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", "520104829659-hlug7mn23rhumuv880e6t850cub3l405.apps.googleusercontent.com");
        params.add("client_secret", "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");
        params.add("refresh_token", account.getRefreshToken());
        params.add("grant_type", "refresh_token");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(params, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(url, request, Map.class);

        Map body = response.getBody();

        String newAccessToken = (String) body.get("access_token");
        Integer expiresIn = (Integer) body.get("expires_in");

        account.setAccessToken(newAccessToken);
        account.setExpiryTime(Instant.now().plusSeconds(expiresIn));

        repository.save(account);

        return newAccessToken;
    }
}