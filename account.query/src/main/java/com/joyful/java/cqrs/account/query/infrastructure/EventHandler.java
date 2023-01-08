package com.joyful.java.cqrs.account.query.infrastructure;

import com.joyful.java.cqrs.account.common.events.AccountClosedEvent;
import com.joyful.java.cqrs.account.common.events.AccountFundDepositedEvent;
import com.joyful.java.cqrs.account.common.events.AccountOpenedEvent;
import com.joyful.java.cqrs.account.common.events.AccountFundWithdrawnEvent;

public interface EventHandler {
    void on(AccountOpenedEvent event);
    void on(AccountFundWithdrawnEvent event);
    void on(AccountClosedEvent event);
    void on(AccountFundDepositedEvent event);
}
