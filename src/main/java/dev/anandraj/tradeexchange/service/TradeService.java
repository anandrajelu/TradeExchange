package dev.anandraj.tradeexchange.service;

import dev.anandraj.tradeexchange.entity.Order;
import dev.anandraj.tradeexchange.entity.Trade;
import dev.anandraj.tradeexchange.repository.TradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class TradeService {

    @Autowired
    SequenceService sequenceService;

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    public Long createTrade(Order matchedOrder, Order order) {
        Long tradeId = sequenceService.getNextSequence("Trade");
        Trade trade = new Trade();
        String buyer = matchedOrder.getParty();
        String seller = order.getParty();
        if (Order.OrderType.SELL.equals(matchedOrder.getOrderType())) {
            seller = matchedOrder.getParty();
            buyer = order.getParty();
        }
        trade.setSeller(seller);
        trade.setBuyer(buyer);
        trade.setTradeDate(new Date());
        trade.setPrice(order.getPrice());
        trade.setStock(order.getStock());
        trade.setId(tradeId);
        tradeRepository.save(trade);
        return tradeId;
    }

    public List<Trade> getTradeDetails(String buyer, String seller, String symbol, Date date) {
        Query query = TradeRepository.getTradeQuery(buyer, seller, symbol, date);
        log.info("Trade Details Query - "+query);
        List<Trade> trades = mongoTemplate.find(query, Trade.class);
        log.info("Trades Size: "+trades.size());
        return trades;
    }
}
