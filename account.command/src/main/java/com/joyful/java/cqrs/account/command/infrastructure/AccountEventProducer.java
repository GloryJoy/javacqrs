package com.joyful.java.cqrs.account.command.infrastructure;

import com.joyful.java.cqrs.core.events.BaseEvent;
import com.joyful.java.cqrs.core.producer.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AccountEventProducer implements EventProducer {

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void produce(String topicName, BaseEvent event) {
        this.kafkaTemplate.send(topicName, event);
    }
}
