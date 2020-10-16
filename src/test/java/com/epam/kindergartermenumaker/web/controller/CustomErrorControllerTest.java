package com.epam.kindergartermenumaker.web.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;

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

    @Test
    void shouldReturn404PageWhenStatus404() {
        when(request.getAttribute(ERROR_STATUS_CODE)).thenReturn(404);

        String page = controller.handleError(request);

        assertThat(page).isEqualTo("error-404");
    }

    @Test
    void shouldReturn500PageWhenStatus500() {
        when(request.getAttribute(ERROR_STATUS_CODE)).thenReturn(500);

        String page = controller.handleError(request);

        assertThat(page).isEqualTo("error-500");
    }

    @Test
    void shouldReturnErrorPageWhenStatus403() {
        when(request.getAttribute(ERROR_STATUS_CODE)).thenReturn(403);

        String page = controller.handleError(request);

        assertThat(page).isEqualTo("error");
    }

    @Test
    void shouldReturnErrorPageWhenStatusIsNull() {
        when(request.getAttribute(ERROR_STATUS_CODE)).thenReturn(null);

        String page = controller.handleError(request);

        assertThat(page).isEqualTo("error");
    }

    @Test
    void shouldReturnNullAsErrorPath() {
        String page = controller.getErrorPath();

        assertThat(page).isNull();
    }
}