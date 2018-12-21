package com.example.restapi.catalog.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class Utils{

   public Object copyNonNullProperties(Object source, Object destination) {
        BeanUtils.copyProperties(source, destination,
                getNullPropertyNames(source));
        return destination;
      }

    private String getNullPropertyNames (Object source) {
            final BeanWrapper src = new BeanWrapperImpl(source);
            java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

            Set emptyNames = new HashSet();
            for(java.beans.PropertyDescriptor pd : pds) {
                //check if value of this property is null then add it to the collection
                Object srcValue = src.getPropertyValue(pd.getName());
                if (srcValue == null) emptyNames.add(pd.getName());
            }
            String[] result = new String[emptyNames.size()];
            //return (emptyNames.toArray(result);
            return  String.join(",",emptyNames);
        }

    }
