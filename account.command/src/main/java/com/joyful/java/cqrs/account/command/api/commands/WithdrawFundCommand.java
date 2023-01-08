package com.joyful.java.cqrs.account.command.api.commands;


import com.joyful.java.cqrs.core.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WithdrawFundCommand extends BaseCommand {
    private double amount;
}
