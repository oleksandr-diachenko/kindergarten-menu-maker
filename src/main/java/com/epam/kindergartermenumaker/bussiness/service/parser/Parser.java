package com.epam.kindergartermenumaker.bussiness.service.parser;

import java.io.InputStream;

/**
 * @author : Oleksandr Diachenko
 * @since : 10/2/2020
 **/
public interface Parser<R> {

    R parse(InputStream inputStream);
}
