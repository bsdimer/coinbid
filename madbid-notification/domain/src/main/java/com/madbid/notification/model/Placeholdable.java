package com.madbid.notification.model;

/**
 * Created by nikolov.
 */
public interface Placeholdable {
    String getValueByFieldName(String fieldName) throws IllegalAccessException, NoSuchFieldException;
}
