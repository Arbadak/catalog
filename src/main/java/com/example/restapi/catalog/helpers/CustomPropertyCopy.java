package com.example.restapi.catalog.helpers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import java.beans.FeatureDescriptor;
import java.util.stream.Stream;

@Component
public class CustomPropertyCopy {

    /**
     * Функция копирования property без нулевых значений
     *
     * @param source      - View
     * @param destination - Entity
     * @return
     */

    public Object copyNonNullProperties(Object source, Object destination) {
        String[] ignore = getNullPropertyNames(source);
        BeanUtils.copyProperties(source, destination, ignore);
        return destination;
    }

    protected String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }
}