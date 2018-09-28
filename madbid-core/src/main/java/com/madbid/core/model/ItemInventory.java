package com.madbid.core.model;

import com.madbid.notification.model.Placeholdable;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

/**
 * Created by nikolov.
 */
@Entity
@Table(name = "item_inventory",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"item_id", "serial_number"})}
)
public class ItemInventory extends Auditable implements Identifiable, Serializable, Placeholdable {
    private static final long serialVersionUID = -5810872261642498628L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "state", length = 31)
    @Enumerated(EnumType.STRING)
    private InventoryState state;

    @Column(name = "cost_price")
    private BigDecimal costPrice;

    @Column(name = "costPrice_currency")
    private Currency costPriceCurrency = Currency.getInstance("BGN");

    @Column(name = "serial_number", length = 31)
    private String serialNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @OneToMany(mappedBy = "itemInventory", fetch = FetchType.LAZY)
    private List<Shipping> shippings = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InventoryState getState() {
        return state;
    }

    public void setState(InventoryState state) {
        this.state = state;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String productNumber) {
        this.serialNumber = productNumber;
    }

    public List<Shipping> getShippings() {
        return shippings;
    }

    public void setShippings(List<Shipping> shippings) {
        this.shippings = shippings;
    }

    public Currency getCostPriceCurrency() {
        return costPriceCurrency;
    }

    public void setCostPriceCurrency(Currency costPriceCurrency) {
        this.costPriceCurrency = costPriceCurrency;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    @Override
    public String getValueByFieldName(String fieldName) throws IllegalAccessException, NoSuchFieldException {
        Field field = this.getClass().getDeclaredField(fieldName);
        return field.get(this.getClass().cast(this)).toString();
    }
}
