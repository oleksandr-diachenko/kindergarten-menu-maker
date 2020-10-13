package com.epam.kindergartermenumaker.web.dialect;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author : Oleksandr Diachenko
 * @since : 10/13/2020
 **/
class NumberUtilsTest {

    private final NumberUtils utils = new NumberUtils();

    @Test
    void shouldReturnIntegerWhenIntegerPassed() {
        String result = utils.removeTrailingZeroes(1);

        assertThat(result).isEqualTo("1");
    }

    @Test
    void shouldReturnIntegerWhenDecimalWithOneZeroPassed() {
        String result = utils.removeTrailingZeroes(1.0);

        assertThat(result).isEqualTo("1");
    }

    @Test
    void shouldReturnIntegerWhenDecimalWithTwoZeroPassed() {
        String result = utils.removeTrailingZeroes(1.00);

        assertThat(result).isEqualTo("1");
    }

    @Test
    void shouldReturnIntegerWhenDecimalWithThreeZeroPassed() {
        String result = utils.removeTrailingZeroes(1.000);

        assertThat(result).isEqualTo("1");
    }

    @Test
    void shouldReturnDecimalWhenDecimalWithoutTrailingZeroes() {
        String result = utils.removeTrailingZeroes(1.1);

        assertThat(result).isEqualTo("1.1");
    }

    @Test
    void shouldReturnDecimalWhenDecimalWithTrailingZeroes() {
        String result = utils.removeTrailingZeroes(1.10);

        assertThat(result).isEqualTo("1.1");
    }

    @Test
    void shouldReturnDecimalWhenDecimalWithTwoDigitsAfterDot() {
        String result = utils.removeTrailingZeroes(1.11);

        assertThat(result).isEqualTo("1.11");
    }

    @Test
    void shouldReturnDecimalWithTwoDigitsWhenDecimalWithThreeDigitsAfterDot() {
        String result = utils.removeTrailingZeroes(1.111);

        assertThat(result).isEqualTo("1.11");
    }

    @Test
    void shouldReturnRoundedDecimalWithTwoDigitsWhenDecimalWithThreeDigitsAfterDot() {
        String result = utils.removeTrailingZeroes(1.119);

        assertThat(result).isEqualTo("1.12");
    }
}