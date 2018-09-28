package com.madbid.admin.view.converters;


import com.madbid.core.model.Identifiable;
import com.madbid.core.repository.MadbidRepository;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * Created by peter on 7/26/14.
 */
public abstract class EntityConverter<T extends Identifiable> implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s.length() > 0) {
            Long id = Long.parseLong(s);
            return getRepository().findOne(id);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o instanceof Identifiable) {
            return ((Identifiable) o).getId().toString();
        }
        return null;
    }

    protected abstract MadbidRepository<T> getRepository();
}
