package com.madbid.admin.utils;

import com.madbid.core.model.*;
import com.madbid.core.service.ItemInventoryService;
import com.madbid.core.service.ItemService;
import com.madbid.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

/**
 * Created by nikolov.
 */
@Component
public class AutoComplete implements Serializable {

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemInventoryService itemInventoryService;

    private List<Item> items = new ArrayList<>();
    private List<Currency> currencies = new ArrayList<>();

    @PostConstruct
    private void init() {
        currencies.add(Currency.getInstance("BGN"));
        currencies.add(Currency.getInstance("EUR"));
        currencies.add(Currency.getInstance("USD"));
    }

    public synchronized List<User> autoCompleteUser(String query) {
        query = query.trim();
        if (query.length() > 0) {
            return userService.findByFullName(query, new PageRequest(0, 10));
        }
        return new ArrayList<User>();
    }

    public synchronized List<Item> autoCompleteItem(String query) {
        query = query.trim();
        if (query.length() > 0) {
            return itemService.findByNameLike(query, new PageRequest(0, 10));
        }
        return new ArrayList<Item>();
    }

    public synchronized List<ItemInventory> autoCompleteItemInventoryByItemName(String query) {
        List<ItemInventory> inventories = new ArrayList<>();
        query = query.trim();
        if (query.length() > 0) {
            return itemInventoryService.findByItemNameLikeWithItems(query, new PageRequest(0, 10));
        }
        return new ArrayList<ItemInventory>();
    }

    public List<Currency> getAllCurrencies() {
        return currencies;
    }

    public InventoryState[] getAllItemInventoryStates() {
        return InventoryState.values();
    }

    public AuctionState[] getAllAuctionStates() {
        return AuctionState.values();
    }

    public ItemInventoryService getItemInventoryService() {
        return itemInventoryService;
    }

    public void setItemInventoryService(ItemInventoryService itemInventoryService) {
        this.itemInventoryService = itemInventoryService;
    }
}
