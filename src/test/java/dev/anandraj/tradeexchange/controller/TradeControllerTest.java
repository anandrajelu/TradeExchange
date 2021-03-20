package dev.anandraj.tradeexchange.controller;

import dev.anandraj.tradeexchange.entity.Trade;
import dev.anandraj.tradeexchange.repository.TradeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TradeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TradeRepository tradeRepository;

    @Test
    void testTradeDetailsNoFilter() throws Exception {
        assertNotNull(mockMvc);
        ResultActions action = mockMvc.perform(get("/trades"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status.result").value("FAILURE"));
    }

    @Test
    void testTradeDetailsWithBuyerFilter() throws Exception {
        mockMvc.perform(get("/trades")
                .queryParam("buyer", "PartyA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status.result").value("SUCCESS"));
    }

    @Test
    void testTradeDetailsWithBuyerSellerFilter() throws Exception {
        generateTradeDetails();
        mockMvc.perform(get("/trades")
                .queryParam("buyer", "PartyA")
                .queryParam("seller", "PartyC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trades").isArray())
                .andExpect(jsonPath("$.count").value(1));
    }

    private void generateTradeDetails() {
        Trade trade = new Trade();
        trade.setBuyer("PartyA");
        trade.setSeller("PartyC");
        trade.setStock("AMZ");
        trade.setId(101);
        trade.setPrice(100);
        tradeRepository.save(trade);
    }
}