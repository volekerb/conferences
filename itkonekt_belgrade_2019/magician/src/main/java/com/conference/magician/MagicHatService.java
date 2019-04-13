package com.conference.magician;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class MagicHatService {

    @Value("${magicHat.host}")
    private String magicHatHost;

    private final RestTemplate restTemplate;

    @Autowired
    public MagicHatService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String applySpell(Spell spell) {
        log.info("Casting... " +  spell.getWords());
        ResponseEntity<String> response = restTemplate.postForEntity(magicHatHost, spell, String.class);
        log.info("Response: " + response.getBody());
        return response.getBody();
    }
}
