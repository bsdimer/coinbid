package com.madbid.web.payment.dto;

import com.madbid.core.model.*;
import com.madbid.web.payment.PaymentProvider;
import org.joda.time.LocalDateTime;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

/**
 * Created by dimer on 10/28/14.
 */
public class PaymentDTO {

    private SaleType saleType;
    private User user;
    private BigDecimal price;
    private Currency currency;
    private String itemName;
    private String itemDescription;
    private String paymentMethod = PaymentProvider.payOnDelivery;
    private Shipping shipping;
    private Address address;

    public SaleType getSaleType() {
        return saleType;
    }

    public void setSaleType(SaleType saleType) {
        this.saleType = saleType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String description) {
        this.itemDescription = description;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    public BigDecimal getItemsTotal() {
        return getPrice().setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getShippingTotal() {
        if (shipping != null) {
            return getShipping().getPrice().setScale(2, RoundingMode.HALF_UP);
        }
        return BigDecimal.valueOf(0);
    }

    public BigDecimal getOrderTotal() {
        return getItemsTotal().add(getShippingTotal());
    }

    public Payment toPayment() {
        Payment payment = new Payment();
        /*payment.setCreateTime(LocalDateTime.now());*/
        payment.setPayer(user);
        payment.setSaleType(getSaleType());
        payment.setCurrency(getCurrency());
        payment.setAmount(getPrice());
        payment.setShipping(shipping);
        if (shipping != null) {
            itemDescription = getShipping().getItemInventory().getItem().getDescription();
        }
        Transaction transaction = new Transaction();
        transaction.setTransactionState(TransactionState.INITIAL);
        transaction.setExpireAt(LocalDateTime.now().plusDays(1));
        /*transaction.setUser(getUser());*/
        transaction.setPaymentType(PaymentType.pec);
        transaction.setDescription(getItemName());
        transaction.setAmount(getPrice());
        transaction.setCurrency(getCurrency());
        transaction.setPayment(payment);
        payment.addTransaction(transaction);
        return payment;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    /*public List<Address> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }*/
}
