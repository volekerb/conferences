package com.conference.magician;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController("/magician")
public class MagicianController {

    @Value("${sqs.queue}")
    private String queue;
    private QueueMessagingTemplate queueMessagingTemplate;
    private SpellCaster spellCaster;

    @Autowired
    public MagicianController(QueueMessagingTemplate queueMessagingTemplate, SpellCaster spellCaster) {
        this.queueMessagingTemplate = queueMessagingTemplate;
        this.spellCaster = spellCaster;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String castSpell(@RequestBody Spell spell) {
        String uuid = UUID.randomUUID().toString();
        spell.set(uuid);
        queueMessagingTemplate.convertAndSend(queue, spell);
        return uuid;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getResult(@PathVariable String id) {
        return spellCaster.getResult(id);
    }
}
