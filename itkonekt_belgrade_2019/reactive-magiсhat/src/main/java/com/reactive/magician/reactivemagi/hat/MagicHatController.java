package com.reactive.magician.reactivemagi.hat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

@RestController("/reactiveMagicHat")
public class MagicHatController {

    WebClient webClient;

    @Autowired
    public MagicHatController(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("localhost:8084").build();
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux streamData() {
        Flux<String> greetingFlux = Flux.fromStream(Stream.generate(() -> "Hello, I'm thinking " + Instant.now().toString()));
        Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(1));
        return Flux.zip(greetingFlux, durationFlux).map(Tuple2::getT1);
    }
}
