package com.madbid.core.repository;

import com.madbid.core.model.Payment;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Dimer.
 */
public interface PaymentRepository extends MadbidRepository<Payment> {

    Payment findByData(String data);

    @Query("SELECT DISTINCT p FROM Payment  p " +
            "LEFT JOIN FETCH p.transactions t " +
            "where p.data like ?1")
    Payment findByDataDeep(String data);

    @Query("SELECT DISTINCT p FROM Payment p " +
            "LEFT JOIN FETCH p.transactions t " +
            "LEFT JOIN FETCH p.payer u " +
            "LEFT JOIN FETCH u.addresses a " +
            "where p.id = ?1")
    Payment findOneDeep(Long id);
}
