package com.common.mydb;

import java.util.Date;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;

public class RowMapperFactory<T> {
    private Class<T> requiredType;

    public RowMapperFactory(Class<T> requiredType) {
        this.requiredType = requiredType;
    }

    public RowMapper<T> getRowMapper() {
        if (requiredType.equals(String.class) || Number.class.isAssignableFrom(requiredType)
                || requiredType.equals(Date.class)) {
            return new SingleColumnRowMapper<T>(requiredType);
        } else {
            return new BeanPropertyRowMapper<T>(requiredType);
        }
    }
}
