//package com.coca.midterms.controller;
//
//import com.coca.midterms.service.GoogleContactsService;
//import com.google.api.client.auth.oauth2.BearerToken;
//import com.google.api.client.auth.oauth2.Credential;
//import com.google.api.services.people.v1.model.Person;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.security.oauth2.core.OAuth2AccessToken;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//import java.time.Instant;
//
//@RestController
//public class PeopleController {
//    @Autowired
//    private OAuth2AuthorizedClientService authorizeClientService;
//    @Autowired
//    private OAuth2AuthorizedClientManager authorizedClientManager;
//    @Autowired
//    private GoogleContactsService peopleService;
//
//    public String getToken() {
//        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
//        OAuth2AuthorizedClient client = authorizeClientService.loadAuthorizedClient(token.getAuthorizedClientRegistrationId(), token.getName());
//
//        if (client == null || client.getAccessToken() == null) {
//            throw new RuntimeException("OAuth2 client or access token not found.");
//        }
//
//        OAuth2AccessToken accessToken = client.getAccessToken();
//        if (accessToken.getExpiresAt().isBefore(Instant.now())) {
//            client = authorizedClientManager.authorize(OAuth2AuthorizeRequest.withClientRegistrationId(token.getAuthorizedClientRegistrationId())
//                    .principal(token)
//                    .build());
//            if (client == null) {
//                throw new RuntimeException("Failed to refresh the access token.");
//            }
//            accessToken = client.getAccessToken();
//        }
//
//        return accessToken.getTokenValue();
//    }
//
//    private Credential getCredential(String token){
//        return new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(token);
//    }
//
//    @GetMapping("/get-contacts")
//    public ResponseEntity<String> getContacts() throws GeneralSecurityException, IOException {
//        String token = getToken();
//        Credential credential = getCredential(token);
//        String contacts = peopleService.getAllContacts(credential);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", "application/json");
//        return ResponseEntity.ok().headers(headers).body(contacts);
//    }
//
//    @PostMapping("/create-contact")
//    public void createContact(@RequestParam String name, @RequestParam String email, @RequestParam String phone) throws GeneralSecurityException, IOException {
//        Credential credential = getCredential(getToken());
//        peopleService.createContact(credential, name, email, phone);
//    }
//
//    @DeleteMapping("/delete-contact")
//    public void deleteContact(@RequestParam Person person) throws GeneralSecurityException, IOException {
//        Credential credential = getCredential(getToken());
//        peopleService.deleteContact(credential, person.getResourceName());
//    }
//
//    @PutMapping("/update-contact")
//    public void updateContact(@RequestParam String name, @RequestParam String resourceName, @RequestParam String email, @RequestParam String phone) throws GeneralSecurityException, IOException {
//        Credential credential = getCredential(getToken());
//        peopleService.updateContact(credential, resourceName, name, email, phone);
//    }
//}
