package com.madbid.admin.converter;

import com.madbid.core.model.Item;
import com.madbid.core.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * Created by nikolov.
 */
@Component
public class ItemConverter implements Converter {
    @Autowired
    private ItemService itemService;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return itemService.findOne(Long.parseLong(s));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o instanceof Item) {
            return ((Item) o).getId().toString();
        }
        return null;
    }

    public ItemService getItemService() {
        return itemService;
    }

    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }
}
