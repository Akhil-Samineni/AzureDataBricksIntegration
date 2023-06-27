package com.samiak.azuredatabricks.controller;

import com.samiak.azuredatabricks.model.Person;
import com.samiak.azuredatabricks.respository.PersonRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.Principal;
import java.util.List;

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

    @GetMapping("/token")
    public ResponseEntity<String> graph(
            @RegisteredOAuth2AuthorizedClient("azuredatabricks") OAuth2AuthorizedClient graphClient, HttpServletRequest request
    ) {
        HttpSession session = request.getSession();
        String token = graphClient.getAccessToken().getTokenValue();
        session.setAttribute("access_token", token);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/azuredatabricks-data")
    public ResponseEntity<String> azuredatabricks(
            @RegisteredOAuth2AuthorizedClient("azuredatabricks") OAuth2AuthorizedClient azureClient
    ) {
        System.out.println(azureClient.getAccessToken());
        String token = azureClient.getAccessToken().getTokenValue();
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
    @Autowired
    PersonRepository personRepository;

    @GetMapping("/mongo/persons")
    public List<Person> getPersons() {
        List<Person> people = personRepository.findAll();
        return people;
    }
}
