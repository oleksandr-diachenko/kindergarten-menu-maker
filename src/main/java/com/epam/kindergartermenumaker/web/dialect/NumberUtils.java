package com.epam.kindergartermenumaker.web.dialect;

import java.text.DecimalFormat;

/**
 * @author : Oleksandr Diachenko
 * @since : 10/13/2020
 **/
public class NumberUtils {

    public String removeTrailingZeroes(Number target) {
        DecimalFormat df = new DecimalFormat("###.##");
        return df.format(target);
    }
}
