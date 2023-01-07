package com.joyful.java.cqrs.account.command.api.commands;

import com.joyful.java.cqrs.account.command.domain.AccountAggregate;
import com.joyful.java.cqrs.core.handler.EventSourcingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;
import java.util.function.Consumer;

@Service
public class AccountCommandHandler implements CommandHandler{

    @Autowired
    EventSourcingHandler<AccountAggregate> eventSourcingHandler;

    @Override
    public void handler(OpenAccountCommand command) {
        var aggregate = new AccountAggregate(command);
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handler(DepositFundCommand command) {
        var aggregate = eventSourcingHandler.getById(command.getId());

        aggregate.depositFund(command.getAmount());
        eventSourcingHandler.save(aggregate);

    }

    @Override
    public void handler(WithdrawFundCommand command) {
        var aggregate = eventSourcingHandler.getById(command.getId());

        BiFunction<Double, Double, Boolean> isBalanceSufficent = (withdraw, balance) -> withdraw > balance;

       if  (isBalanceSufficent.apply(command.getAmount(), aggregate.getBalance())) {
           throw new IllegalStateException("The withdrawing amount is greater than account balance.");
       }

       aggregate.withdrawFund(command.getAmount());
       eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handler(CloseAccountCommand command) {
        var aggregate = eventSourcingHandler.getById(command.getId());
        aggregate.closeAccount();
        eventSourcingHandler.save(aggregate);

    }
}
