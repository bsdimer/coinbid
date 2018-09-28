package com.madbid.core.service;

import com.madbid.core.model.Transaction;
import com.madbid.core.repository.MadbidRepository;
import com.madbid.core.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by dimer on 8/17/14.
 */
@Service
@Transactional
public class TransactionService extends BaseService<Transaction> {

    @Inject
    private TransactionRepository transactionRepository;

    @Override
    public MadbidRepository getRepository() {
        return transactionRepository;
    }


    public Transaction findByTransactionId(String transactionId) {
        return transactionRepository.findByTransactionId(transactionId);
    }

}
