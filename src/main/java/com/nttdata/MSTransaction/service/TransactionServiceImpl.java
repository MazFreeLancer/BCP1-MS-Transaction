package com.nttdata.MSTransaction.service;

import com.nttdata.MSTransaction.model.Transaction;
import com.nttdata.MSTransaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public Mono<Transaction> createTransaction(Transaction t) {
        return transactionRepository.save(t);
    }

    @Override
    public Mono<Transaction> updateTransaction(Transaction t) {
        return transactionRepository.save(t);
    }

    @Override
    public Mono<Transaction> findByTransactionId(String id) {
        return transactionRepository.findById(id);
    }

    @Override
    public Flux<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Flux<Transaction> findAllByAccountId(String id) {
        return transactionRepository.findAll(Sort.by(String.valueOf(id)));
    }

    @Override
    public Mono<Void> deleteTransaction(String id) {
        return transactionRepository.deleteById(id);
    }
}
