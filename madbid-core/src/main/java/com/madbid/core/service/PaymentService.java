package com.madbid.core.service;

import com.madbid.core.model.Payment;
import com.madbid.core.repository.MadbidRepository;
import com.madbid.core.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by dimer on 8/17/14.
 */
@Service
@Transactional
public class PaymentService extends BaseService<Payment> implements ServiceContract<Payment> {

    @Inject
    private PaymentRepository paymentRepository;

    @Override
    public MadbidRepository getRepository() {
        return paymentRepository;
    }

    public Payment findByData(String data) {
        return paymentRepository.findByData(data);
    }

    public Payment findByDataDeep(String data) {
        return paymentRepository.findByDataDeep(data);
    }

    public Payment findOneDeep(Long id) {
        return paymentRepository.findOneDeep(id);
    }

}
