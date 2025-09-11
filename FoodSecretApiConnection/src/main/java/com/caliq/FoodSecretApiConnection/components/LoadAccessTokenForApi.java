package com.caliq.FoodSecretApiConnection.components;

import com.caliq.FoodSecretApiConnection.records.OAuthTokenResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.time.Instant;

@Component
public class LoadAccessTokenForApi implements ApplicationRunner {

    private final RestClient oauthClient;

    @Value("${fatsecret.oauth.token-url}")
    private String tokenUrl;

    @Value("${fatsecret.oauth.client-id}")
    private String clientId;

    @Value("${fatsecret.oauth.client-secret}")
    private String clientSecret;

    @Value("${fatsecret.oauth.scope: basic}")
    private String scope;

    private String grant_type = "client_credentials";

    private volatile String accessToken;
    private volatile Instant refreshAt = Instant.EPOCH;


    public LoadAccessTokenForApi(@Qualifier("oauthRestClient") RestClient oauthClient) {
        this.oauthClient = oauthClient;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        refreshToken();
    }

    @Scheduled(fixedDelay = 60_000)
    public void scheduledRefresh() {
        ensureValidToken();
    }

    public String getAccessToken() {
        ensureValidToken();
        return accessToken;
    }

    private synchronized void ensureValidToken() {
        if (Instant.now().isAfter(refreshAt)) {
            refreshToken();
        }
    }

    private void refreshToken() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", grant_type);
        form.add("scope", scope);

        OAuthTokenResponse res = oauthClient
                .post()
                .uri(tokenUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .headers(h -> h.setBasicAuth(clientId, clientSecret, StandardCharsets.UTF_8))
                .body(form)
                .retrieve()
                .body(OAuthTokenResponse.class);


        if (res == null || res.accessToken() == null) {
            throw new IllegalStateException("Failed to obtain access_tokens");
        }

        this.accessToken = res.accessToken();
        this.refreshAt = Instant.now().plusSeconds(Math.max(0, res.expiresIn() - 60));
    }
}
