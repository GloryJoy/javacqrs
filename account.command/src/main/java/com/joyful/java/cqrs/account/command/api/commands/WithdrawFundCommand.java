package com.joyful.java.cqrs.account.command.api.commands;


import com.joyful.java.cqrs.core.commands.BaseCommand;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WithdrawFundCommand extends BaseCommand {
    private double amount;
}
