package com.joyful.java.cqrs.account.query.infrastructure;

import com.joyful.java.cqrs.account.common.events.AccountClosedEvent;
import com.joyful.java.cqrs.account.common.events.AccountFundDepositedEvent;
import com.joyful.java.cqrs.account.common.events.AccountOpenedEvent;
import com.joyful.java.cqrs.account.common.events.AccountFundWithdrawnEvent;
import com.joyful.java.cqrs.account.query.domain.AccountRepository;
import com.joyful.java.cqrs.account.query.domain.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountEventHandler implements EventHandler{

    @Autowired
    AccountRepository accountRepository;

    @Override
    public void on(AccountOpenedEvent event) {
        var bankAccount = BankAccount
                .builder()
                .id(event.getId())
                .accountHolder(event.getAccountHolder())
                .accountType(event.getAccountType())
                .creationDate(event.getCreatedDate())
                .balance(event.getOpeningBalance())
                .build();


        accountRepository.save(bankAccount);

    }

    @Override
    public void on(AccountFundWithdrawnEvent event) {
        var bankAccount = accountRepository.findById(event.getId());
        bankAccount.ifPresent(b -> {
            var currentBalance = b.getBalance();
            var latestBalance = currentBalance - event.getAmount();
            b.setBalance(latestBalance);
            accountRepository.save(b);
        });

    }

    @Override
    public void on(AccountClosedEvent event) {
        accountRepository.deleteById(event.getId());

    }

    @Override
    public void on(AccountFundDepositedEvent event) {
        var bankAccount = accountRepository.findById(event.getId());

        bankAccount.ifPresent(b -> {
            var currentBalance = b.getBalance();
            var latestBalance = currentBalance + event.getAmount();
            b.setBalance(latestBalance);
            accountRepository.save(b);
        });

    }
}
