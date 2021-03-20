package dev.anandraj.tradeexchange.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void testNonMatchedOrders() throws Exception {
        assertNotNull(mockMvc);
        mockMvc.perform(get("/orders"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testNonMatchedOrdersByStock() throws Exception {
        assertNotNull(mockMvc);
        mockMvc.perform(get("/orders")
                .queryParam("symbol", "AMZ")
                .queryParam("price", "100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status.result").value("SUCCESS"));
    }

}