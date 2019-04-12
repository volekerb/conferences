package com.conference.magician;

import com.amazonaws.services.sqs.AmazonSQS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Slf4j
@Service
public class SpellCaster {

    @Value("${sqs.queue}")
    private String queue;

    private final AmazonSQS sqsClient;
    private final MagicHatService magicHatService;
    private HashMap<String, String> resultsMap = new HashMap<>();

    @Autowired
    public SpellCaster(AmazonSQS sqsClient, MagicHatService magicHatService) {
        this.sqsClient = sqsClient;
        this.magicHatService = magicHatService;
    }

    @SqsListener("${sqs.queue}")
    public void subscribeToSqs(final Spell spell) {
        log.info("Received message: " + spell);
        String result = magicHatService.applySpell(spell);
        resultsMap.put(spell.getId(), result);
    }

    public String getResult(String id) {
        return resultsMap.get(id);
    }
}
