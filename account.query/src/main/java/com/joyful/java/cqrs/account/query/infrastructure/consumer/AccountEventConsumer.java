package com.joyful.java.cqrs.account.query.infrastructure.consumer;

import com.joyful.java.cqrs.account.common.events.AccountClosedEvent;
import com.joyful.java.cqrs.account.common.events.AccountFundDepositedEvent;
import com.joyful.java.cqrs.account.common.events.AccountFundWithdrawnEvent;
import com.joyful.java.cqrs.account.common.events.AccountOpenedEvent;
import com.joyful.java.cqrs.account.query.infrastructure.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class AccountEventConsumer implements EventConsumer {

    @Autowired
    EventHandler eventHandler;

    @KafkaListener(topics = "AccountOpenedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(AccountOpenedEvent event, Acknowledgment acknowledgment) {
        eventHandler.on(event);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = "AccountFundDepositedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(AccountFundDepositedEvent event, Acknowledgment acknowledgment) {
        eventHandler.on(event);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = "AccountClosedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(AccountClosedEvent event, Acknowledgment acknowledgment) {
        eventHandler.on(event);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = "AccountFundWithdrawnEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(AccountFundWithdrawnEvent event, Acknowledgment acknowledgment) {
        eventHandler.on(event);
        acknowledgment.acknowledge();
    }
}
