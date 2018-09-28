package com.madbid.admin.converter;

import com.madbid.core.model.ItemInventory;
import com.madbid.core.service.ItemInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * Created by nikolov.
 */
@Component
public class ItemInventoryConverter implements Converter {
    @Autowired
    private ItemInventoryService itemInventoryService;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return itemInventoryService.findOne(Long.parseLong(s));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o instanceof ItemInventory) {
            return ((ItemInventory) o).getId().toString();
        }
        return null;
    }

    public ItemInventoryService getItemInventoryService() {
        return itemInventoryService;
    }

    public void setItemInventoryService(ItemInventoryService itemInventoryService) {
        this.itemInventoryService = itemInventoryService;
    }
}
