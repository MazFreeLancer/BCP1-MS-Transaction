package com.nttdata.MSTransaction.service;

import com.nttdata.MSTransaction.MsTransactionApplication;
import com.nttdata.MSTransaction.model.Account;
import com.nttdata.MSTransaction.model.Transaction;
import com.nttdata.MSTransaction.repository.TransactionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionServiceImpl implements TransactionService {
    private static final Logger logger = LogManager.getLogger(MsTransactionApplication.class);

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountServiceImpl accountService;

    @Override
    public Mono<Transaction> createTransaction(Transaction t) {
        logger.info("Ingresa al create transaction");
        Mono<Account> accountMono = accountService.getAccountById(t.getIdAccount());

        logger.info("Trabajamos con el account");
        return accountMono.flatMap(account -> {
            Float balanceAux = account.getBalance();
            Float balanceFinal = Float.valueOf(0);
            logger.info("Asignamos balance: " + balanceAux);
            if(t.getTransactionType().toString().equals("DEPOSIT")){
                logger.info("Trabajamos con deposito");
                balanceFinal = balanceAux + t.getAmount();
                logger.info("balanceFinal: " + balanceFinal);
                account.setBalance(balanceFinal);
                logger.info("accountTransaction: " + account.toString());
                accountService.updateAccount(account);
                return transactionRepository.save(t);
            }else if(t.getTransactionType().toString().equals("CASHOUT")){
                logger.info("Trabajamos con retiro");
                if(balanceAux < t.getAmount()){
                    return Mono.error(new
                            Exception("Insufficient fund"));
                }else{
                    account.setBalance(balanceAux - t.getAmount());
                    accountService.updateAccount(account);
                    return transactionRepository.save(t);
                }
            }
            return Mono.error(new
                    Exception("Something happened"));
        });

        //return transactionRepository.save(t);
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
