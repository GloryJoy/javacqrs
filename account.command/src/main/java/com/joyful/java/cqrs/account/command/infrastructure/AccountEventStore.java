package com.joyful.java.cqrs.account.command.infrastructure;

import com.joyful.java.cqrs.account.command.domain.AccountAggregate;
import com.joyful.java.cqrs.account.command.domain.EventStoreRepository;
import com.joyful.java.cqrs.core.events.BaseEvent;
import com.joyful.java.cqrs.core.events.EventModel;
import com.joyful.java.cqrs.core.exception.AggregateNotFoundException;
import com.joyful.java.cqrs.core.exception.ConcurrencyException;
import com.joyful.java.cqrs.core.exception.EventDataElementNotFoundException;
import com.joyful.java.cqrs.core.infrastructure.EventStore;
import com.joyful.java.cqrs.core.producer.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class AccountEventStore implements EventStore {

    @Autowired
    EventStoreRepository eventStoreRepository;

    @Autowired
    EventProducer eventProducer;

//    private Boolean isValidVersion(int version){
//        return (version != -1) ? true : false ;
//    }
//
//    private Boolean isExpectedVersion(int version){
//        return ()
//    }

    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (expectedVersion != -1 && eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion) {
            throw new ConcurrencyException();
        }
        AtomicInteger version = new AtomicInteger(expectedVersion);

        events.forEach(event -> {

            event.setVersion(version.incrementAndGet());
            EventModel eventModel = EventModel
                    .builder()
                    .timeStamp(new Date())
                    .aggregateIdentifier(aggregateId)
                    .aggregateType(AccountAggregate.class.getTypeName())
                    .version(version.get())
                    .eventType(event.getClass().getTypeName())
                    .eventData(event)
                    .build();

            Optional<EventModel> persistentEvent = Optional.ofNullable(eventStoreRepository.save(eventModel));
            persistentEvent.ifPresent(em -> {
                eventProducer.produce(event.getClass().getSimpleName(), event);
            });
        });


    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {

        Optional<List<EventModel>> events = Optional.ofNullable(eventStoreRepository.findByAggregateIdentifier(aggregateId));

        return events
                .map(eml -> Optional
                        .ofNullable(eml
                                .stream()
                                .map(e -> e.getEventData())
                                .collect(Collectors.toList()))
                        .orElseThrow(()-> new EventDataElementNotFoundException()))
                .orElseThrow(() -> new AggregateNotFoundException("The given aggregate Id could not be found."));

    }
}
