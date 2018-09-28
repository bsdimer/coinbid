package com.madbid.core.model;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

/**
 * Created by nikolov.
 */
@Entity
@Table(name = "shippings")
public class Shipping extends Auditable implements Identifiable, Serializable {
    private static final long serialVersionUID = -299335303022000201L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tracking_id")
    private String trackingId;

    @Column
    private BigDecimal price;

    @Column
    private Currency priceCurrency;

    @Column(name = "shipping_company", nullable = false)
    private String shippingCompany;

    @Column(name = "shipping_state", length = 31, nullable = false)
    @Enumerated(EnumType.STRING)
    private ShippingState shippingState;

    @Column(name = "returned_reason", length = 255)
    private String returnedReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_inventory", nullable = false)
    private ItemInventory itemInventory;

    @Column
    private BigDecimal insurancePrice;

    @Column
    private Currency insuranceCurrency;

    @OneToOne
    private Address address;

    @OneToOne(mappedBy = "shipping", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Payment payment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getShippingCompany() {
        return shippingCompany;
    }

    public void setShippingCompany(String shippingCompany) {
        this.shippingCompany = shippingCompany;
    }

    public ShippingState getShippingState() {
        return shippingState;
    }

    public void setShippingState(ShippingState shippingState) {
        this.shippingState = shippingState;
    }

    public String getReturnedReason() {
        return returnedReason;
    }

    public void setReturnedReason(String returnedReason) {
        this.returnedReason = returnedReason;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ItemInventory getItemInventory() {
        return itemInventory;
    }

    public void setItemInventory(ItemInventory itemInventory) {
        this.itemInventory = itemInventory;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Currency getPriceCurrency() {
        return priceCurrency;
    }

    public void setPriceCurrency(Currency priceCurrency) {
        this.priceCurrency = priceCurrency;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public BigDecimal getInsurancePrice() {
        return insurancePrice;
    }

    public void setInsurancePrice(BigDecimal insurancePrice) {
        this.insurancePrice = insurancePrice;
    }

    public Currency getInsuranceCurrency() {
        return insuranceCurrency;
    }

    public void setInsuranceCurrency(Currency insuranceCurrency) {
        this.insuranceCurrency = insuranceCurrency;
    }

    public Boolean hasInsurance() {
        return (this.insurancePrice != null && this.insurancePrice.floatValue() > 0);
    }
}
