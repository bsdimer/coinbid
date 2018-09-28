package com.madbid.web.payment;

import com.madbid.core.model.*;
import com.madbid.core.validation.ValidatorUtil;

import java.util.UUID;

/**
 * Created by dimer on 11/1/14.
 */

public class ShippingUtil {

    public static synchronized Shipping generateShipping(User user,
                                                         ItemInventory inventory,
                                                         Address address) {
        Shipping shipping = new Shipping();
        shipping.setUser(user);
        shipping.setTrackingId(UUID.randomUUID().toString());
        shipping.setItemInventory(inventory);
        shipping.setShippingState(ShippingState.INITIATING);
        shipping.setShippingCompany("Speedy LTD");
        if (ValidatorUtil.validateAddress(address)) {
            shipping.setAddress(address);
        }
        shipping.setPriceCurrency(inventory.getItem().getShippingPriceCurrency());
        shipping.setPrice(inventory.getItem().getShippingPrice());
        return shipping;
    }

}
