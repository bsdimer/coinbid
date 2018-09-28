package com.madbid.lazyLoaders;

import com.madbid.core.model.Payment;
import com.madbid.core.service.ServiceContract;

/**
 * Created by dimer on 8/17/14.
 */
public class PaymentsLazyLoader extends LazyLoaderAdapter<Payment> {

    public PaymentsLazyLoader(ServiceContract<Payment> repository) {
        super(repository);
    }
}
