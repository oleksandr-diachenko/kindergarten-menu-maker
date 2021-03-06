package com.epam.kindergartermenumaker.web.converter;

/**
 * @author : Oleksandr Diachenko
 * @since : 9/18/2020
 **/
public interface Converter<T, R> {

    R convert(T type);
}
