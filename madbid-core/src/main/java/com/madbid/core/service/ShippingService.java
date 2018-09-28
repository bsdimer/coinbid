package com.madbid.core.service;

import com.madbid.core.model.Payment;
import com.madbid.core.model.Shipping;
import com.madbid.core.repository.MadbidRepository;
import com.madbid.core.repository.ShippingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by dimer on 8/17/14.
 */
@Service
@Transactional
public class ShippingService extends BaseService<Shipping> {

    @Inject
    private ShippingRepository shippingRepository;

    @Override
    public MadbidRepository getRepository() {
        return shippingRepository;
    }

    public Shipping findDistinctByPayment(Payment payment) {
        return shippingRepository.findDistinctByPayment(payment);
    }
}
