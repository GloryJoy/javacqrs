package com.joyful.java.cqrs.account.query.infrastructure.consumer;

import com.joyful.java.cqrs.account.common.events.AccountClosedEvent;
import com.joyful.java.cqrs.account.common.events.AccountFundDepositedEvent;
import com.joyful.java.cqrs.account.common.events.AccountOpenedEvent;
import com.joyful.java.cqrs.account.common.events.AccountFundWithdrawnEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {
    void consume(@Payload AccountOpenedEvent event, Acknowledgment acknowledgment);
    void consume(@Payload AccountFundDepositedEvent event, Acknowledgment acknowledgment);
    void consume(@Payload AccountClosedEvent event, Acknowledgment acknowledgment);
    void consume(@Payload AccountFundWithdrawnEvent event, Acknowledgment acknowledgment);
}
