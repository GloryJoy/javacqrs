package com.joyful.java.cqrs.account.command.api.commands;

public interface CommandHandler {
    void handler(OpenAccountCommand command);
    void handler(DepositFundCommand command);
    void handler(WithdrawFundCommand command);
    void handler(CloseAccountCommand command);

}
