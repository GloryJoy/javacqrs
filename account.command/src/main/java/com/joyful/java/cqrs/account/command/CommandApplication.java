package com.joyful.java.cqrs.account.command;

import com.joyful.java.cqrs.account.command.api.commands.*;
import com.joyful.java.cqrs.core.infrastructure.CommandDispatcher;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommandApplication {
	@Autowired
	private CommandDispatcher commandDispatcher;

	@Autowired
	private CommandHandler commandHandler;

	public static void main(String[] args) {
		SpringApplication.run(CommandApplication.class, args);
	}

	@PostConstruct
	public void registerHandlers(){
		commandDispatcher.registerHandler(OpenAccountCommand.class, commandHandler::handler);
		commandDispatcher.registerHandler(DepositFundCommand.class, command -> commandHandler.handler(command));
		commandDispatcher.registerHandler(WithdrawFundCommand.class, commandHandler::handler);
		commandDispatcher.registerHandler(CloseAccountCommand.class, commandHandler::handler);
	}

}
