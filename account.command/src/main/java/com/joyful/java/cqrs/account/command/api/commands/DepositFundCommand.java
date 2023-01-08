package com.joyful.java.cqrs.account.command.api.commands;

import com.joyful.java.cqrs.core.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class DepositFundCommand extends BaseCommand {
    private double amount;
}
