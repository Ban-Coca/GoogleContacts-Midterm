package com.coca.midterms.controller;

import com.coca.midterms.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PeopleController {
    @Autowired
    private OAuth2AuthorizedClientService authorizeClientService;
    private PeopleService peopleService;
    public String getToken(){
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthorizedClient client = authorizeClientService.loadAuthorizedClient(token.getAuthorizedClientRegistrationId(), token.getName());
        return client.getAccessToken().getTokenValue();
    }

    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping("/get-contacts")
    public ResponseEntity<String> getContacts() {
        String response = peopleService.getAllContacts(getToken());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create-contact")
    public ResponseEntity<String> createContact() {
        return ResponseEntity.ok("Contact created!");
    }
}
