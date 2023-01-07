package com.joyful.java.cqrs.account.common.events;

import com.joyful.java.cqrs.core.events.BaseEvent;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class AccountClosedEvent extends BaseEvent {
}
