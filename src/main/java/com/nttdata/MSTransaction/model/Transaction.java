package com.nttdata.MSTransaction.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("transaction")
public class Transaction {
    @Id
    private String id;
    private String idAccount;
    private Float amount;
    private String date;
    private TransactionType transactionType;
}
