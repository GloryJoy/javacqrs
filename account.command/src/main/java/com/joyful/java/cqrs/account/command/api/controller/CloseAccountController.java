package com.joyful.java.cqrs.account.command.api.controller;

import com.joyful.java.cqrs.account.command.api.commands.CloseAccountCommand;
import com.joyful.java.cqrs.account.common.dto.BaseResponse;
import com.joyful.java.cqrs.core.exception.AggregateNotFoundException;
import com.joyful.java.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("api/v1/closeaccount")
public class CloseAccountController {

    private Logger logger = Logger.getLogger(CloseAccountController.class.getName());

    @Autowired
    CommandDispatcher commandDispatcher;

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> closeAccount(@PathVariable(name = "id") String id){
        try {
            commandDispatcher.send(new CloseAccountCommand(id));
            return new ResponseEntity<>(new BaseResponse(MessageFormat.format("The request for closing account with id {0} has been successfully submitted.", id)), HttpStatus.OK);
        } catch (IllegalStateException | AggregateNotFoundException e){
            logger.log(Level.WARNING, MessageFormat.format("Account closing request with id {0} is failed with the following error message. \n {1} ", id, e.getMessage()));
            return new ResponseEntity<>(new BaseResponse(MessageFormat.format("The account close request with account id {0} has failed", id)), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            logger.log(Level.SEVERE, MessageFormat.format("Account close request has failed with the following stack trace \n {0} ", e.fillInStackTrace()));
            return new ResponseEntity<>(new BaseResponse("Account close request has failed."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
