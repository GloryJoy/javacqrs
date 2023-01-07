package com.joyful.java.cqrs.account.command.api.commands;

import com.joyful.java.cqrs.core.commands.BaseCommand;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class CloseAccountCommand extends BaseCommand {
    public CloseAccountCommand(String id){
        super(id);
    }
}
