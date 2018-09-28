package com.madbid.core.service;

import com.madbid.core.model.Invoice;
import com.madbid.core.model.User;
import com.madbid.core.repository.InvoiceRepository;
import com.madbid.core.repository.MadbidRepository;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by dimer on 8/17/14.
 */
@Service
@Transactional
public class InvoiceService extends BaseService<Invoice> {

    @Inject
    private InvoiceRepository invoiceRepository;

    @Override
    public MadbidRepository getRepository() {
        return invoiceRepository;
    }

    public Long getInvoicesCount() {
        return invoiceRepository.getInvoicesCount();
    }

    public List<Invoice> getExpiredInvoices() {
        return invoiceRepository.getExpiredInvoices();
    }

    public int getInvoicesCountPerUserBetween(User user, LocalDateTime start, LocalDateTime end) {
        return invoiceRepository.getInvoicesCountPerUserBetween(user, start, end);
    }
}
