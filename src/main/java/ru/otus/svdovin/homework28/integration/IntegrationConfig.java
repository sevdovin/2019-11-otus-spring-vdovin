package ru.otus.svdovin.homework28.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessageChannel;

@Configuration
public class IntegrationConfig {

    @Bean
    public MessageChannel ordersChannel() {
        return MessageChannels.queue(100).get();
    }

    @Bean
    public MessageChannel resultChannel() {
        return MessageChannels.direct().get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(100).maxMessagesPerPoll(1).get();
    }

    @Bean
    public IntegrationFlow washingFlow() {
        return IntegrationFlows.from("ordersChannel")
                .split()
                .handle("washingServiceImpl", "process")
                .aggregate()
                .channel("resultChannel")
                .get();
    }
}
