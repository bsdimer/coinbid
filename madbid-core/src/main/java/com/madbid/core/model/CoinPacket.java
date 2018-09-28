package com.madbid.core.model;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

/**
 * Created by nikolov.
 */
@Entity
@Table(name = "coinPackets", uniqueConstraints =
@UniqueConstraint(columnNames = {"name", "quantity", "value", "currency"}))
public class CoinPacket extends Auditable implements Identifiable, Serializable {

    private static final long serialVersionUID = -2204793950604055251L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Integer quantity;

    @Column
    private BigDecimal value;

    @Column
    private Currency currency;

    @Override
    public boolean equals(Object value) {
        if (value instanceof CoinPacket && ((CoinPacket) value).getId() != null) {
            return ((CoinPacket) value).getId().equals(this.getId());
        }
        return super.equals(value);
    }


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}