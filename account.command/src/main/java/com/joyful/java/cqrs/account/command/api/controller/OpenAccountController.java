package com.joyful.java.cqrs.account.command.api.controller;

import com.joyful.java.cqrs.account.command.api.commands.OpenAccountCommand;
import com.joyful.java.cqrs.account.command.api.dto.OpenAccountResponse;
import com.joyful.java.cqrs.account.common.dto.BaseResponse;
import com.joyful.java.cqrs.core.infrastructure.CommandDispatcher;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("api/v1/openaccount")
public class OpenAccountController {
    Logger logger = Logger.getLogger(OpenAccountController.class.getName());

    @Autowired
    CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity<BaseResponse> openAccount(@RequestBody OpenAccountCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        try {
            commandDispatcher.send(command);
            return new ResponseEntity(new OpenAccountResponse("The bank account creation request has been completed succesfully with ID ", command.getId()), HttpStatus.CREATED);

        } catch (IllegalStateException illegalStateException) {
            logger.log(Level.WARNING, MessageFormat.format("The request returned an illegal state with the following message : {0}", illegalStateException.getMessage()));
            return new ResponseEntity<>(new BaseResponse(illegalStateException.toString()), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            var friendlyMessage = MessageFormat.format("Error while processing request to open a new bank account with id {0}", id);
            logger.log(Level.SEVERE, friendlyMessage, e.getMessage());
            return new ResponseEntity<>(new OpenAccountResponse(friendlyMessage), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
