package com.conference.magician;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Profile("default")
@Configuration
public class AppConfiguration {

    @Value("${cloud.aws.region.static}")
    String awsRegion;

    @Value("${localstack.sqs.url}")
    String localStackSqsUrl;

    @Value("${sqs.queue}")
    private String queue;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Bean
    public RestTemplate restTemplate() {
        return restTemplateBuilder.build();
    }

    @Bean
    @Primary
    AmazonSQSAsync sqsClient() {
        return AmazonSQSAsyncClientBuilder
                .standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(localStackSqsUrl, awsRegion))
                .build();
    }

    @PostConstruct
    public void initQueues() {
        sqsClient().createQueue(queue);
    }
}
