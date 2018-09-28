package com.madbid.core.repository;

import com.madbid.core.model.Payment;
import com.madbid.core.model.Shipping;

/**
 * Created by Dimer.
 */
public interface ShippingRepository extends MadbidRepository<Shipping> {
    Shipping findDistinctByPayment(Payment payment);
}
