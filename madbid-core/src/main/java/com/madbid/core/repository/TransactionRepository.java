package com.madbid.core.repository;

import com.madbid.core.model.Transaction;

/**
 * Created by Dimer.
 */
public interface TransactionRepository extends MadbidRepository<Transaction> {
    Transaction findByTransactionId(String transactionId);
}
