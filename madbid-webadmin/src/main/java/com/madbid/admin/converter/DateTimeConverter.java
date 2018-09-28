package com.madbid.admin.converter;

import org.joda.time.LocalDateTime;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by nikolov.
 */
@FacesConverter(value = "dateTimeConverter")
public class DateTimeConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH).parse(s);
            LocalDateTime dateTime = LocalDateTime.fromDateFields(date);
            return dateTime;
        } catch (ParseException e) {
            try {
                Date date = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(s);
                LocalDateTime dateTime = LocalDateTime.fromDateFields(date);
                return dateTime;
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o instanceof LocalDateTime) {
            StringBuilder sb = new StringBuilder();
            sb.append(((LocalDateTime) o).getDayOfMonth()).append("/");
            sb.append(((LocalDateTime) o).getMonthOfYear()).append("/");
            sb.append(((LocalDateTime) o).getYear()).append(" ");
            sb.append(String.format("%02d", ((LocalDateTime) o).getHourOfDay())).append(":");
            sb.append(String.format("%02d", ((LocalDateTime) o).getMinuteOfHour())).append(":");
            sb.append(String.format("%02d", ((LocalDateTime) o).getSecondOfMinute()));
            return sb.toString();
        }
        return null;
    }
}
