package com.epam.kindergartermenumaker.web.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Stream;

import static javax.servlet.RequestDispatcher.ERROR_STATUS_CODE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author : Oleksandr Diachenko
 * @since : 10/16/2020
 **/
@ExtendWith(MockitoExtension.class)
class CustomErrorControllerTest {

    @InjectMocks
    private CustomErrorController controller;

    @Mock
    private HttpServletRequest request;

    private static Stream<Arguments> provideCodesToPages() {
        return Stream.of(
                Arguments.of(404, "error-404"),
                Arguments.of(500, "error-500"),
                Arguments.of(403, "error"),
                Arguments.of(null, "error")
        );
    }

    @ParameterizedTest
    @MethodSource("provideCodesToPages")
    void shouldReturnSpecificPageForSpecificErrorCode(Integer code, String expectedPage) {
        when(request.getAttribute(ERROR_STATUS_CODE)).thenReturn(code);

        String page = controller.handleError(request);

        assertThat(page).isEqualTo(expectedPage);
    }

    @Test
    void shouldReturnNullAsErrorPath() {
        String page = controller.getErrorPath();

        assertThat(page).isNull();
    }
}