package com.reactive.magician.reactivemagician;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RestController("/magician")
public class MagicianController {

    @Value("${magicHat.host}")
    private String magicHatHost;

    WebClient webClient;

    @Autowired
    public MagicianController(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("localhost:8080").build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> castSpell(@RequestBody Spell spell) {
        log.info("Received spell:" + spell);
        return webClient.post().uri(magicHatHost).body(BodyInserters.fromObject(spell)).retrieve().bodyToMono(String.class);
    }

}
