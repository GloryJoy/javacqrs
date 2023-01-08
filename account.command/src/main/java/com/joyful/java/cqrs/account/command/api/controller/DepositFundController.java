package com.joyful.java.cqrs.account.command.api.controller;

import com.joyful.java.cqrs.account.command.api.commands.DepositFundCommand;
import com.joyful.java.cqrs.account.common.dto.BaseResponse;
import com.joyful.java.cqrs.core.exception.AggregateNotFoundException;
import com.joyful.java.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("api/v1/deposit")
public class DepositFundController {

    private final Logger logger = Logger.getLogger(DepositFundController.class.getName());

    @Autowired
    CommandDispatcher commandDispatcher;

    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> depositFund(@PathVariable("id") String id, @RequestBody DepositFundCommand command){
        try {
            command.setId(id);
            commandDispatcher.send(command);
            return new  ResponseEntity(new BaseResponse("Deposit Fund request completed successfully"), HttpStatus.OK);

        }catch (IllegalStateException | AggregateNotFoundException e) {
            logger.log(Level.WARNING, MessageFormat.format("Error during accound deposit fund as following {0}", e));
            return new ResponseEntity<>(new BaseResponse("Deposit fund failed"), HttpStatus.BAD_REQUEST);


        } catch (Exception e){
            String friendlyMessage = MessageFormat.format("Internal server error has caused the deposit fund with the following id {0} to fail", id);
            logger.log(Level.SEVERE, MessageFormat.format("Deposit Fund failed with internal error with the following message {0}", e));

            return new ResponseEntity<>(new BaseResponse(friendlyMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
