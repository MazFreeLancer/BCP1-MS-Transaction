package com.nttdata.MSTransaction.service;

import com.nttdata.MSTransaction.model.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {
    Mono<Transaction> createTransaction(Transaction t);
    Mono<Transaction> updateTransaction(Transaction t);
    Mono<Transaction> findByTransactionId(Integer id);
    Flux<Transaction> findAllByAccountId(Integer id);
    Mono<Void> deleteTransaction(Integer id);
}
