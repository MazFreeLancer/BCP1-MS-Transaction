package com.nttdata.MSTransaction.controller;

import com.nttdata.MSTransaction.model.Transaction;
import com.nttdata.MSTransaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/transaction")
@RestController
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Transaction> createTransaction(@RequestBody Transaction t){
        return transactionService.createTransaction(t);
    }

    @GetMapping(value = "/get/account/{idAccount}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Flux<Transaction> findByAccountId(@PathVariable("idAccount") String id){
        return transactionService.findAllByAccountId(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Mono<Transaction>> findById(@PathVariable("id") String id){
        Mono<Transaction> transactionMono = transactionService.findByTransactionId(id);
        return new ResponseEntity<Mono<Transaction>>(transactionMono, transactionMono != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/getAll", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus
    public Flux<Transaction> findAll(){
        return transactionService.findAll();
    }


    @PutMapping("/update")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Mono<Transaction>> updateTransaction(@RequestBody Transaction t){
        Mono<Transaction> transactionMono = transactionService.updateTransaction(t);
        return new ResponseEntity<Mono<Transaction>>(transactionMono,
                transactionMono!=null? HttpStatus.OK:HttpStatus.NOT_FOUND) ;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> deleteById(@PathVariable("id") String id){
        return transactionService.deleteTransaction(id);
    }

}
