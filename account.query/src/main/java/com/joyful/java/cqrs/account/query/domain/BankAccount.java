package com.joyful.java.cqrs.account.query.domain;

import com.joyful.java.cqrs.account.common.dto.AccountType;
import com.joyful.java.cqrs.core.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccount extends BaseEntity {

    @Id
    private String id;
    private String accountHolder;
    private Date creationDate;
    private AccountType accountType;
    private double balance;
}
