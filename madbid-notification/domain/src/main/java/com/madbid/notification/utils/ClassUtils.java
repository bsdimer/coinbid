package com.madbid.notification.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nikolov.
 */
public final class ClassUtils {
    public static final String packageName = "com.madbid.core.model";

    /**
     * Get only field names
     * @param type
     * @param excludes
     * @return
     */
    public static List<String> getFieldNames(Class<?> type, String... excludes) {
        Field[] fields = type.getDeclaredFields();
        List<String> fieldsNames = new ArrayList<>();
        for (Field field : fields) {
            fieldsNames.add(field.getName());
        }
        fieldsNames.removeAll(Arrays.asList(excludes));
        return fieldsNames;
    }

    /**
     * Get Field names concatenated with class name for example Foo.id, Foo.name
     * @param type
     * @param excludes
     * @return
     */
    public static List<String> getClassFieldNames(Class<?> type, String... excludes) {
        List<String> classFieldNames = new ArrayList<>();
        List<String> fieldNames = getFieldNames(type, excludes);
        for (String fieldName : fieldNames) {
            classFieldNames.add(type.getSimpleName() + "_" + fieldName);
        }
        return classFieldNames;
    }

    public static Class<?> getClassByName(String className) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(packageName + "." + className);
        return clazz;
    }
}
