package com.joyful.java.cqrs.account.command.domain;

import com.joyful.java.cqrs.account.command.api.commands.OpenAccountCommand;
import com.joyful.java.cqrs.account.common.events.AccountClosedEvent;
import com.joyful.java.cqrs.account.common.events.AccountDepositedEvent;
import com.joyful.java.cqrs.account.common.events.AccountOpenedEvent;
import com.joyful.java.cqrs.account.common.events.FundWithdrawnEvent;
import com.joyful.java.cqrs.core.domain.AgreegateRoot;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
public class AccountAggregate extends AgreegateRoot {
    private Boolean active;
    private double balance;

    public double getBalance() {
        return balance;
    }

    public AccountAggregate(OpenAccountCommand command) {
        raiseEvent(AccountOpenedEvent
                .builder()
                .id(command.getId())
                .accountHolder(command.getAccountHolder())
                .accountType(command.getAccountType())
                .createdDate(new Date())
                .openingBalance(command.getOpendingBalance())
                .build());
    }

    public void apply(AccountOpenedEvent event) {
        this.id = event.getId();
        this.active = true;
        this.balance = event.getOpeningBalance();
    }

    public void depositFund(double amount) {
        if (!this.active) {
            throw new IllegalStateException("Fund can not be deposted into a closed account");

        }
        if (amount <= 0) {
            throw new IllegalStateException("The deposit amount must be greater than zero");
        }

        raiseEvent(AccountDepositedEvent
                .builder()
                .id(this.id)
                .amount(amount)
                .build() );
    }

    public void apply(AccountDepositedEvent event){
        this.id = event.getId();
        this.balance += event.getAmount();
    }

    public void withdrawFund(double amount){
        if (!this.active){
            throw new IllegalStateException("Fund cannot be withdrawn from a closed account");

        }

        if (amount <=0) {
            throw new IllegalStateException("The amount must be greater than 0");
        }

        raiseEvent(FundWithdrawnEvent
                .builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply(FundWithdrawnEvent event){
        this.id = event.getId();
        this.balance -= event.getAmount();
    }

    public void closeAccount(){
        if (!this.active){
            throw new IllegalStateException("The account cannot be closed because it is in a closed state.");

        }

        raiseEvent(AccountClosedEvent
                .builder()
                .id(this.id)
                .build());
    }

    public void apply(AccountClosedEvent event){
        this.id = event.getId();
        this.active = false;
    }
}
