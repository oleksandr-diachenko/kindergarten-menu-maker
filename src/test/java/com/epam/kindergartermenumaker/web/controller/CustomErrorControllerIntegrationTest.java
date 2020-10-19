package com.epam.kindergartermenumaker.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author : Oleksandr Diachenko
 * @since : 10/16/2020
 **/
@WebMvcTest(CustomErrorController.class)
class CustomErrorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldStatusOkOnError() throws Exception {
        mockMvc.perform(get("/error"))
                .andExpect(status().isOk());
    }
}