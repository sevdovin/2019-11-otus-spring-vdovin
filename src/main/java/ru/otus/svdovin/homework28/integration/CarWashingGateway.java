package ru.otus.svdovin.homework28.integration;

import java.util.List;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import ru.otus.svdovin.homework28.domain.MenuItem;
import ru.otus.svdovin.homework28.domain.WorkDone;

@MessagingGateway
public interface CarWashingGateway {

    @Gateway(requestChannel = "ordersChannel", replyChannel = "resultChannel")
    List<WorkDone> processOrder(List<MenuItem> items);
}
