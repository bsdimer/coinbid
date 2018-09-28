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
@Table(name = "items")
public class Item extends Auditable implements Identifiable, Serializable, Placeholdable {
    private static final long serialVersionUID = 2495957989971015109L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 63, nullable = false, unique = true)
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "longDescription")
    private String longDescription;

    @Column(name = "retail_price")
    private BigDecimal retailPrice;

    @Column(name = "retailPrice_currency")
    private Currency retailPriceCurrency = Currency.getInstance("BGN");

    @Column(name = "market_price")
    private BigDecimal marketPrice;

    @Column(name = "marketPrice_currency")
    private Currency marketPriceCurrency = Currency.getInstance("BGN");

    @Column(name = "shipping_price")
    private BigDecimal shippingPrice = BigDecimal.valueOf(10f);

    @Column
    private float profitMargin = 1.2f;

    @Column(name = "shippingPrice_currency")
    private Currency shippingPriceCurrency = Currency.getInstance("BGN");

    @OneToMany(mappedBy = "item", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Picture> pictures = new ArrayList<Picture>();

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<ItemInventory> itemInventories = new ArrayList<ItemInventory>();

    public List<ItemInventory> getAvailableItemInventories() {
        List<ItemInventory> availableItemInventories = new ArrayList<>();
        for (ItemInventory itemInventory : itemInventories) {
            if (InventoryState.AVAILABLE.equals(itemInventory.getState())) {
                availableItemInventories.add(itemInventory);
            }
        }
        return availableItemInventories;
    }

    public Picture getDefaultPicture() {
        //STODO implement setting of default picture
        return pictures.get(0);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public List<ItemInventory> getItemInventories() {
        return itemInventories;
    }

    public void setItemInventories(List<ItemInventory> itemInventories) {
        this.itemInventories = itemInventories;
    }

    public BigDecimal getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(BigDecimal shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public Currency getRetailPriceCurrency() {
        return retailPriceCurrency;
    }

    public void setRetailPriceCurrency(Currency retailPriceCurrency) {
        this.retailPriceCurrency = retailPriceCurrency;
    }

    public Currency getShippingPriceCurrency() {
        return shippingPriceCurrency;
    }

    public void setShippingPriceCurrency(Currency shippingPriceCurrency) {
        this.shippingPriceCurrency = shippingPriceCurrency;
    }

    public float getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(float profitMargin) {
        this.profitMargin = profitMargin;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Currency getMarketPriceCurrency() {
        return marketPriceCurrency;
    }

    public void setMarketPriceCurrency(Currency marketPriceCurrency) {
        this.marketPriceCurrency = marketPriceCurrency;
    }

    @Override
    public String getValueByFieldName(String fieldName) throws IllegalAccessException, NoSuchFieldException {
        Field field = this.getClass().getDeclaredField(fieldName);
        return field.get(this.getClass().cast(this)).toString();
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }
}
