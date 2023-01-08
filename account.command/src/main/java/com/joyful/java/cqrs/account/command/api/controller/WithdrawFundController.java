package com.joyful.java.cqrs.account.command.api.controller;

import com.joyful.java.cqrs.account.command.api.commands.WithdrawFundCommand;
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
@RequestMapping("api/v1/withdraw")
public class WithdrawFundController {

    @Autowired
    private CommandDispatcher commandDispatcher;

    private Logger logger = Logger.getLogger(WithdrawFundController.class.getName());

    @PutMapping(value = "/{id}")
    public ResponseEntity<BaseResponse> withdrawFund(@PathVariable String id, @RequestBody WithdrawFundCommand command) {
        try {

            command.setId(id);
            commandDispatcher.send(command);
            return new ResponseEntity<>(new BaseResponse("The withdraw fund amound has been deducted successfully"), HttpStatus.OK);

        } catch (IllegalStateException | AggregateNotFoundException e) {
            logger.log(Level.WARNING, MessageFormat.format("Error during account fund withdrawal as following {0}", e));
            return new ResponseEntity<>(new BaseResponse("Fund withdrawal failed"), HttpStatus.BAD_REQUEST);


        } catch (Exception e) {
            String friendlyMessage = MessageFormat.format("Internal server error has caused the fund withdrawal with the following id {0} to fail", id);
            logger.log(Level.SEVERE, MessageFormat.format("Fund withdrawal failed with internal error with the following message {0}", e));

            return new ResponseEntity<>(new BaseResponse(friendlyMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


}
