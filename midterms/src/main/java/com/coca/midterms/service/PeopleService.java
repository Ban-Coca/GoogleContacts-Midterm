package com.coca.midterms.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PeopleService {
    public final WebClient webClient;

    public PeopleService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://people.googleapis.com/v1").build();
    }



    public String getAllContacts(String accessToken) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/people/me/connections")
                        .queryParam("personFields", "names,emailAddresses,phoneNumbers")
                        .queryParam("pageSize", 10)
                        .build())
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
