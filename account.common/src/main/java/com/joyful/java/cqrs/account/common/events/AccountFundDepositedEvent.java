package com.joyful.java.cqrs.account.common.events;

import com.joyful.java.cqrs.core.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AccountFundDepositedEvent extends BaseEvent {
    private double amount;
}
