package com.samiak.azuredatabricks.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.Principal;

@RestController
public class HomeController {
    private final WebClient webClient;
    private static String DATABRICKS_ENDPOINT = "https://adb-6462097691393438.18.azuredatabricks.net";

    public HomeController() {
        this.webClient = WebClient.builder().build();
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello(Principal principal) {
        String res = "Hello " + principal.getName();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/graph")
    public ResponseEntity<String> graph(
            @RegisteredOAuth2AuthorizedClient("graph") OAuth2AuthorizedClient graphClient
    ) {
        String res = "JWT Token is: " + graphClient.getAccessToken().getTokenValue();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/azuredatabricks-data")
    public ResponseEntity<String> azuredatabricks(
            @RegisteredOAuth2AuthorizedClient("azuredatabricks") OAuth2AuthorizedClient graphClient
    ) {
        System.out.println(graphClient.getAccessToken());
        String token = graphClient.getAccessToken().getTokenValue();
        String res = null;
        try {
            res = webClient.get()
                    .uri(DATABRICKS_ENDPOINT + "/api/2.0/token-management/tokens")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            System.out.println(res);
        } catch (Exception w) {
            w.printStackTrace();
        }
        return ResponseEntity.ok(res);
    }

}
