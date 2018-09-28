package com.madbid.admin.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.util.Currency;

/**
 * Created by dimer on 8/20/14.
 */
@FacesConverter("currencyConverter")
public class CurrencyConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return Currency.getInstance(s);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return ((Currency) o).getCurrencyCode();
    }
}
