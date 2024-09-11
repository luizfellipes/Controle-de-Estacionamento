package com.api.Controledeestacionamento.configs;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.Arrays;

public abstract class CopyPropertiesConfig {

    protected CopyPropertiesConfig() {
    }

    public static void copyProperties(Object origem, Object alvo) {
        if (origem != null && alvo != null) {
            BeanUtils.copyProperties(origem, alvo, getNullPropertyNames(origem));
        }
    }

    private static String[] getNullPropertyNames(Object source) {
        BeanWrapper src = new BeanWrapperImpl(source);
        return Arrays.stream(src.getPropertyDescriptors())
                .map(PropertyDescriptor::getName)
                .filter(name -> src.getPropertyValue(name) == null)
                .toArray(String[]::new);
    }

}