package com.conference.magichat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController("/magicHat")
public class MagicHatController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String doMagic(@RequestBody Spell spell) {
        List<String> words = spell.getWords();
        log.info("Spell words: " + words);
        return words.contains("Pow") ? "You're lucky!" : "Sorry, try next time";
    }

}
