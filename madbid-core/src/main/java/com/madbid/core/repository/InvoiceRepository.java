package com.madbid.core.repository;

import com.madbid.core.model.Invoice;
import com.madbid.core.model.User;
import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Dimer.
 */
public interface InvoiceRepository extends MadbidRepository<Invoice> {

    @Query(value = "select count(i) from Invoice i")
    Long getInvoicesCount();

    @Query(value = "select i from Invoice i where i.paidAt is null and i.expireAt > CURRENT_TIMESTAMP")
    List<Invoice> getExpiredInvoices();

    @Query(value = "select count(i) from Invoice i where i.user = ?1 and i.created > ?2 and i.created < ?3")
    int getInvoicesCountPerUserBetween(User user, LocalDateTime start, LocalDateTime end);
}
