package com.semobook.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.common.base.CaseFormat;

public class CustomNamingStrategy extends PropertyNamingStrategy.PropertyNamingStrategyBase {

    @Override
    public String translate(String propertyName) {
        String result = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, propertyName);
        return result;
    }
}