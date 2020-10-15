package com.epam.kindergartermenumaker.bussiness.service.search;

/**
 * @author : Oleksandr Diachenko
 * @since : 10/15/2020
 **/
public interface Searcher<T> {

    boolean contains(T object, String filter);
}
