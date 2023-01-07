package com.joyful.java.cqrs.account.command.api.commands;

import com.joyful.java.cqrs.account.common.dto.AccountType;
import com.joyful.java.cqrs.core.commands.BaseCommand;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OpenAccountCommand extends BaseCommand {
    private String accountHolder;
    private AccountType accountType;
    private double opendingBalance;
}
